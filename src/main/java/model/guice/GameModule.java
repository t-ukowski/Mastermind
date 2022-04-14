package model.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import model.persistance.IPersistenceManager;
import model.persistance.MongoDbPersistenceManager;

public class GameModule extends AbstractModule {
    @Provides
    IPersistenceManager providePersistenceManager(MongoDbPersistenceManager impl) {
        return impl;
    }
}
