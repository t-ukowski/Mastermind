package model.statistic;

import io.reactivex.rxjava3.core.Observable;
import model.Player;

import java.util.List;

public interface INotifier {
    void sendNotification(String message, Player player);

    <T> void sendNotification(String message, Player player, List<T> list);

    void sendNotification(String message, Observable<Player> players);

    <T> void sendNotification(String message, Observable<Player> players, List<T> list);
}
