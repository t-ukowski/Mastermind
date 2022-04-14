package model.persistance;

//import model.*;

import model.Game;
import model.IGameObject;
import model.Player;
import model.hasher.HasherMD5;
import model.hasher.IHasher;
import model.statistic.data_objects.RankingItem;
import model.statistic.data_objects.StatisticsItem;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePlayerDAO {

    private final IHasher iHasher = new HasherMD5();
    IPersistenceManager manager;


    @Inject
    public GamePlayerDAO(MongoDbPersistenceManager manager) {
        this.manager = manager;
    }

    public void addPlayer(Player player) throws PersistenceException {
        manager.savePlayer(player);
    }

    public void addGame(Game game) {
        try {
            manager.addGame(game);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    public boolean isPasswordCorrect(String player_email, String password) {
        List<IGameObject> players = manager.selectByParam(new Player(null, null, null), "password", iHasher.hash(password));

        for (IGameObject player : players) {
            String name = ((Player) player).getEmail();
            if (Objects.equals(name, player_email)) {
                System.out.println("Welcome " + player_email + "! Login successful!");
                return true;
            }
        }
        System.out.println("Login unsuccessful. Try again");
        return false;
    }

    public Player getPlayer(String email) {
        List<IGameObject> objects = manager.selectByParam(new Player(null, null, null), "email", email);
        Player player;

        if (objects.isEmpty()) {
            System.out.println("No such player found!");
            return null;
        }

        if (objects == null) {
            System.out.println("Null returned");
            return null;
        }

        if (objects.size() > 1) {
            System.out.println("More than one player found!");
            return null;
        } else {
            System.out.println("Player found in database!");
        }

        player = (Player) objects.get(0);

        return player;
    }


    /**
     * @deprecated
     */
    @Deprecated
    public boolean emailExists(String email) {
        return false;
    }

    public List<Game> getLatestGames(Player player) {

        List<IGameObject> objects = manager.getLatestPlayerGames(player, 10);
        List<Game> games = null;

        for (IGameObject obj : objects) {
            games.add((Game) obj);
        }

        return games;
    }

    public int getBestScore(Player player) {
        List<IGameObject> objects = manager.getTopByParam(new Game(null, null), "timestamp", null, "timestamp", 1);
        List<Game> games = null;

        for (IGameObject obj : objects) {
            games.add((Game) obj);
        }

        return games.get(0).getPoints();
    }

    public List<Game> getBestGames() {
        List<IGameObject> objects = manager.getTopByParam(new Game(null, null), "points", null, "points", 10);
        List<Game> games = null;

        for (IGameObject obj : objects) {
            games.add((Game) obj);
        }

        return games;
    }

    public List<Game> getAllGames(Player player) {
        List<IGameObject> objects = manager.getTopByParam(new Game(null, null), "timestamp", null, "timestamp", 9999);
        List<Game> games = null;

        for (IGameObject obj : objects) {
            games.add((Game) obj);
        }

        return games;
    }

    public List<Player> getBestPlayers() {
        List<IGameObject> objects = manager.getTopByParam(new Player(null, null, null), "points", null, "points", 10);
        List<Player> players = null;

        for (IGameObject obj : objects) {
            players.add((Player) obj);
        }

        return players;
    }

    /**
     * Fetches ranking items from database
     *
     * @return List of the items
     */
    public List<RankingItem> getRanking() {
        return manager.getBestPlayers();
    }


    /**
     * Fetches statistics items from database
     *
     * @return List of the items
     */
    public List<StatisticsItem> getStatistics(Player player) {
        List<IGameObject> games = manager.getLatestPlayerGames(player, 10);
        List<StatisticsItem> stats = new ArrayList<>();
        int index = 1;
        Game game;

        for (IGameObject o : games) {
            game = (Game) o;
            stats.add(new StatisticsItem(index, game.getTimestamp(), game.getDifficulty(), 0, game.getPoints()));
            index++;
        }
        return stats;
    }

}
