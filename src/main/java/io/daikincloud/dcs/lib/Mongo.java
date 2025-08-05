package io.daikincloud.dcs.lib;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Mongo {
    private static MongoClient client = null;

    public static MongoClient getClient() {
        if (client == null) {
            AppProperties props = new AppProperties();

            String connectionString = props.getProperty("mongo.connection");

            System.out.println("Connecting Mongo at " + connectionString);

            client = MongoClients.create(MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString)).build());
        }
        return client;
    }

    public static MongoDatabase getDatabase() {
        MongoClient client = Mongo.getClient();
        if (client != null) {
            return client.getDatabase("connect");
        }
        return null;
    }

    public static MongoCollection<Document> getCollection(String name) {
        MongoDatabase db = Mongo.getDatabase();
        if (db != null) {
            return db.getCollection(name);
        }

        return null;
    }
}