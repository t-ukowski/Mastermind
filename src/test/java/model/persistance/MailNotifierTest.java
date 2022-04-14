package model.persistance;
import io.reactivex.rxjava3.core.Observable;
import model.Game;
import model.Player;
import model.statistic.MailNotifier;
import model.statistic.data_objects.RankingItem;
import model.statistic.data_objects.StatisticsItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.enums.GameDifficulty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MailNotifierTest {

    private MailNotifier mailNotifier;

    @BeforeEach
    void setUp(){
        this.mailNotifier = new MailNotifier();
    }

    @Test
    public void simpleMessageTest(){
        mailNotifier.sendNotification("First simple test message!", new Player("nick", "faciszewski@student.agh.edu.pl", "passwd"));
    }

    @Test
    public void sendGameListTest(){
        Player player = new Player("Player1", "faciszewski@student.agh.edu.pl", "passwd");
        List<Game> games = new ArrayList<>();
        games.add(new Game(player, GameDifficulty.EASY));
        games.add(new Game(player, GameDifficulty.HARD));
        mailNotifier.sendNotification(String.format("Your games:\n%-5s %-10s %-6s %-10s","id","player","points","level"),player,games);
    }

    @Test
    public void sendStatisticsListToPlayersTest(){
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1", "faciszewski@student.agh.edu.pl", "passwd");
        players.add(player1);
        Player player2 = new Player("Player2", "faciszewski00@gmail.com", "passwd");
        players.add(player2);

        List<StatisticsItem> games = new ArrayList<>();
        games.add(new StatisticsItem(1, LocalDate.EPOCH, GameDifficulty.EASY,3,100));
        games.add(new StatisticsItem(2, LocalDate.EPOCH, GameDifficulty.EASY,3,100));
        games.add(new StatisticsItem(3, LocalDate.EPOCH, GameDifficulty.EASY,3,100));
        games.add(new StatisticsItem(4, LocalDate.EPOCH, GameDifficulty.EASY,3,100));
        games.add(new StatisticsItem(5, LocalDate.EPOCH, GameDifficulty.EASY,3,100));
        games.add(new StatisticsItem(6, LocalDate.EPOCH, GameDifficulty.EASY,3,100));
        games.add(new StatisticsItem(7, LocalDate.EPOCH, GameDifficulty.EASY,3,100));

        Observable<Player> playerObservable = Observable.fromIterable(players);
        mailNotifier.sendNotification(String.format("Statistics:\n     %-35s %-10s %-25s %-25s\n","date","difficulty","moves","score"),
                playerObservable,games);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void sendRankingListToPlayersTest(){
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player1", "faciszewski@student.agh.edu.pl", "passwd");
        players.add(player1);
        Player player2 = new Player("Player2", "faciszewski00@gmail.com", "passwd");
        players.add(player2);

        List<RankingItem> games = new ArrayList<>();
        games.add(new RankingItem(1, "fisiek123", 3,3,100));
        games.add(new RankingItem(1, "fisiek123", 3,3,100));
        games.add(new RankingItem(1, "fisiek123", 3,3,100));
        games.add(new RankingItem(1, "fisiek123", 3,3,100));
        games.add(new RankingItem(1, "fisiek123", 3,3,100));
        games.add(new RankingItem(1, "fisiek123", 3,3,100));


        Observable<Player> playerObservable = Observable.fromIterable(players);
        mailNotifier.sendNotification(String.format("Ranking:\n     %-70s %-10s %-10s %-10s\n","nick","played","won","best"),
                playerObservable,games);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
