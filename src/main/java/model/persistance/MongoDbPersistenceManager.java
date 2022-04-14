package model.persistance;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Game;
import model.IGameObject;
import model.Player;
import model.hasher.HasherMD5;
import model.hasher.IHasher;
import model.statistic.data_objects.RankingItem;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.enums.GameDifficulty;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.descending;

public class MongoDbPersistenceManager implements IPersistenceManager {
    private static final ConnectionString CONNECTION_STRING = new ConnectionString("mongodb+srv://Fisiek:mastermind@operations.esmi1.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
    private final MongoCollection<Document> games;
    private final MongoCollection<Document> players;
    private IHasher iHasher = new HasherMD5();


    public MongoDbPersistenceManager() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(CONNECTION_STRING)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("MindMaster");
        this.games = database.getCollection("Game");
        this.players = database.getCollection("Player");
    }

    public void savePlayer(Player player) throws PersistenceException {
        if (players.find(eq("email", player.getEmail())).first() != null)
            throw new PersistenceException(String.format("Player with %s email already exists.", player.getEmail()));

        if (players.find(eq("name", player.getName())).first() != null)
            throw new PersistenceException(String.format("Player with %s name already exists.", player.getEmail()));

        Document newPlayer = new Document("name", player.getName())
                .append("email", player.getEmail())
                .append("password", iHasher.hash(player.getPassword()));

        try {
            player.setId(players.insertOne(newPlayer).getInsertedId().asObjectId().getValue().toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("Saving to database failed due to internal error.");
        }
    }

    public void removePlayer(Player player) throws PersistenceException {
        if (players.find(and(eq("email", player.getEmail()), eq("name", player.getName()))).first() == null)
            throw new PersistenceException(String.format("Player with %s email and %s name do not exists.", player.getEmail(), player.getName()));

        try {
            deleteAllPlayerGames(player);
            players.deleteOne(and(eq("email", player.getEmail()), eq("name", player.getName())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new PersistenceException("Deleting from database failed due to internal error.");
        }
    }

    private void deleteAllPlayerGames(Player player) {
        Document playerIdDoc = players.find(and(eq("email", player.getEmail()), eq("name", player.getName()))).projection(include("_id")).first();
        assert playerIdDoc != null;
        games.deleteMany(eq("player_id", playerIdDoc.get("_id")));
    }

    public void addGame(Game game) throws PersistenceException {
        Document playerIdDoc = players.find(and(eq("email", game.getPlayer().getEmail()),
                eq("name", game.getPlayer().getName()))).
                projection(include("_id")).
                first();
//        if(playerIdDoc==null)
//            throw new PersistenceException(String.format("Player with %s email and %s name do not exists.",
//                                                        game.getPlayer().getEmail(),
//                                                        game.getPlayer().getName()));
        String playerId;
        if (playerIdDoc == null) {
            savePlayer(game.getPlayer());
            playerId = game.getPlayer().getId();
        } else {
            playerId = playerIdDoc.get("_id").toString();
        }


        Document newGame = new Document("player_id", new ObjectId(playerId))
//                .append("time",game.getTime())
                .append("timestamp", game.getTimestamp())
                .append("difficulty", game.getDifficulty().ordinal())
                .append("points", game.getPoints());

        try {
            game.setId(games.insertOne(newGame).getInsertedId().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new PersistenceException("Saving game to database failed due to internal error.");
        }

    }

    // -----------  New Functions start here:

    public List<IGameObject> getLatestPlayerGames(Player player, int n) {
        String key = "timestamp";

        List<IGameObject> gameObjects = new ArrayList<>();

        List<Document> gamesDocList = new ArrayList<>();
        games.find(eq("player_id", new ObjectId(player.getId())))
                .sort(descending(key))
                .limit(n)
                .projection(fields())
                .into(gamesDocList);
        gameObjects = getGamesFromDocs(gamesDocList);

        return gameObjects;
    }

    public List<RankingItem> getBestPlayers() {
        List<RankingObject> ranking = new ArrayList<>();
        List<RankingItem> rankingItems = new ArrayList<>();

        // lecę po wszystkich grach, sprawdzam czy dany gracz jest już na liście,
        // jak nie to dodaje go z wynikiem aktualnie rozpatrywanej gry
        // jak tak to porównuje jego obecny highscore z wynikiem aktualnie rozpatrywanej gry, jeśli trzeba to podmieniam.
        // złożoność niefajna jak barszcz - trzeba przeglądnąć wszystkie gry i jeszcze całą listę graczy w poszukiwaniu gracza
        // może jakimś dictionary to robić albo aggregatem

        boolean already_here = false;

        for (Document doc : games.find()) {
            already_here = false;
            for (RankingObject already : ranking) {
                if (already.getPlayer_id().equals(doc.get("player_id").toString())) { //Tutaj możliwe że bedzie ObjectId a nie String
                    already_here = true;
                    already.setGamesPlayed(already.getGamesPlayed() + 1);
                    if (already.getHighestScore() < (int) doc.get("points")) {
                        already.setHighestScore((int) doc.get("points"));
                    } else {
                        continue;
                    }
                }
            }
            if (!already_here) {
//                ranking.add((new RankingObject((new ObjectId(doc.get("player_id").toString())).toString(), 1, (int) doc.get("points")))); //Tutaj też możliwe że będzie ObjectId
                ranking.add((new RankingObject(doc.get("player_id").toString(), 1, (int) doc.get("points"))));
//                System.out.println(doc.get("player_id").toString());
            }
        }

        for (RankingObject obj : ranking) {
//            obj.setName( ((Player)players.find(eq("player_id", obj.getPlayer_id()))).getName() );
//            System.out.println(obj.getPlayer_id());
            List<IGameObject> tmp = selectByParam(new Player(null, null, null), "_id", new ObjectId(obj.getPlayer_id()));
            obj.setName(((Player) tmp.get(0)).getName());
//            obj.setName(mockDB("_id", obj.getPlayer_id()));
            rankingItems.add(new RankingItem(-1, obj.getName(), obj.getGamesPlayed(), obj.getGamesWon(), obj.getHighestScore()));

            // tutaj jeszcze wsadzimy te position i bedzie elegansko
        }

        Collections.sort(rankingItems);
        int position = 1;
        for (RankingItem r : rankingItems) {
            r.setPosition(position);
            position++;
        }

        return rankingItems;
    }


    // New Functions end here

    public List<IGameObject> getTopByParam(IGameObject gameObject, String key, Object value, String sortBy, int n) {
        List<IGameObject> gameObjects = new ArrayList<>();
        if (gameObject instanceof Player) {
            List<Document> playersDocList = new ArrayList<>();
            players.find(eq(key, value))
                    .sort(descending(sortBy))
                    .limit(n)
                    .projection(fields(exclude("password")))
                    .into(playersDocList);
            gameObjects = getPlayersFromDocs(playersDocList);
        } else if (gameObject instanceof Game) {
            List<Document> gamesDocList = new ArrayList<>();
            games.find(eq(key, value))
                    .sort(descending(key))
                    .limit(n)
                    .projection(fields())
                    .into(gamesDocList);
            gameObjects = getGamesFromDocs(gamesDocList);
        }
        return gameObjects;
    }

    @Deprecated
    private String mockDB(String key, Object value) {
//        System.out.println("**> " + key + " > " + value + " > " + players.find(eq(key,value)).first().get("name"));
        return players.find(eq(key, value)).first().get("name").toString();
    }

    public List<IGameObject> selectByParam(IGameObject gameObject, String key, Object value) {
        List<IGameObject> gameObjects = new ArrayList<>();
        if (gameObject instanceof Player) {
            List<Document> playersDocList = new ArrayList<>();
//            System.out.println(">>> " + key + " > " + value + " > " + players.find(eq(key,value)).first().get("name"));
            players.find(eq(key, value))
                    .projection(fields(exclude("password")))
                    .into(playersDocList);
//            System.out.println("] " + key + "\t" + value + "\t" + playersDocList.size());
            gameObjects = getPlayersFromDocs(playersDocList);
        } else if (gameObject instanceof Game) {
            List<Document> gamesDocList = new ArrayList<>();
            games.find(eq(key, value))
                    .projection(fields()).
                    into(gamesDocList);
            gameObjects = getGamesFromDocs(gamesDocList);
        }
        return gameObjects;
    }

    private List<IGameObject> getPlayersFromDocs(List<Document> playersDocList) {
        List<IGameObject> players = new ArrayList<>();
        playersDocList.forEach(playerDoc -> players.add(new Player(playerDoc.get("name").toString(), playerDoc.get("email").toString(), "", playerDoc.get("_id").toString())));
        return players;
    }

    private List<IGameObject> getGamesFromDocs(List<Document> gamesDocList) {
        List<IGameObject> games = new ArrayList<>();
        gamesDocList.forEach(gameDoc -> {
            Document gamePlayer;
            gamePlayer = players.find(eq("_id", gameDoc.get("player_id"))).projection(exclude("password")).first();
            assert gamePlayer != null;
//            games.add(new Game(Integer.getInteger(gameDoc.get("points").toString()),
            games.add(new Game((int) gameDoc.get("points"),
                    new Player(gamePlayer.get("name").toString(),
                            gamePlayer.get("email").toString(),
                            "",
                            gamePlayer.get("_id").toString()),
                    GameDifficulty.byIndex((int) gameDoc.get("difficulty")),
//                    Time.valueOf(gameDoc.get("time").toString()),
                    ((Date) gameDoc.get("timestamp")).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));

        });
        return games;
    }
}
