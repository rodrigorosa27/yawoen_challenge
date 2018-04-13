package com.neoway.persistence.database;

import com.mongodb.MongoClient;
import com.neoway.Configuration;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ejb.Singleton;

@Singleton
public class MorphiaService {

    private Morphia morphia;

    private Datastore datastore;

    private Configuration configuration = Configuration.getInstance();

    public MorphiaService() {
        MongoClient mongoClient = new MongoClient(configuration.getProperty("DATABASE_HOST") +":" +configuration.getProperty("DATABASE_PORT"));
        this.morphia = new Morphia();
        this.datastore = morphia.createDatastore(mongoClient, configuration.getProperty("DATABASE_NAME"));
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(final Datastore datastore) {
        this.datastore = datastore;
    }
}
