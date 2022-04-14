package model.persistance;

import model.Player;
import org.junit.jupiter.api.Test;

public class MongoDbPersistenceManagerTest {

    @Test
    public void MongoDbPersistenceManagerAddPlayerTest() throws PersistenceException {
        MongoDbPersistenceManager persistenceManager = new MongoDbPersistenceManager();
        persistenceManager.savePlayer(new Player("Fisiek","fisiek@com.pl","1234"));
    }

}
