package model.persistance;

import model.Game;
import model.IGameObject;
import model.Player;
import model.statistic.data_objects.RankingItem;

import java.util.List;

public interface IPersistenceManager {
    void addGame(Game game) throws PersistenceException;

    void savePlayer(Player player) throws PersistenceException;

    void removePlayer(Player player) throws PersistenceException;

    List<IGameObject> selectByParam(IGameObject gameObject, String key, Object value);

    List<IGameObject> getTopByParam(IGameObject gameObject, String key, Object value, String sortBy, int n);

    List<IGameObject> getLatestPlayerGames(Player player, int n);

    List<RankingItem> getBestPlayers();
}

