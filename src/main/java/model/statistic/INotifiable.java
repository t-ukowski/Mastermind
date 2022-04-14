package model.statistic;

import io.reactivex.rxjava3.core.Observable;
import model.Player;

import java.util.List;

public interface INotifiable {
    void subscribe(INotifier notifier);

    void unsubscribe(INotifier notifier);

    void update(String message, Player player);

    <T>
    void update(String message, Player player, List<T> list);

    void update(String message, Observable<Player> players);

    <T>
    void update(String message, Observable<Player> players, List<T> list);
}
