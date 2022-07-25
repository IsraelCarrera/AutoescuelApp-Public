package org.example.serverautoescuela.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.inject.Inject;
import org.bson.Document;
import org.example.serverautoescuela.config.Configuration;

public class DBMongo {
    private final Configuration config;
    private MongoClient mongo = null;

    @Inject
    public DBMongo(Configuration config) {
        this.config = config;
    }

    public MongoCollection<Document> getDocument() {
        mongo = MongoClients.create(config.getRuta_mongodb());
        MongoDatabase db = mongo.getDatabase(config.getMongo_database());
        return db.getCollection(config.getMongo_collection());
    }

    public void cerrarMongo() {
        mongo.close();
    }
}
