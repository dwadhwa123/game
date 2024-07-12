package com.example.game;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.changestream.ChangeStreamDocument;


public class MongoDB {
    //String uri = "mongodb+srv://dwadhwa:RkzwC5uipJApsk2c@cluster0.nzyceaj.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    String uri = "mongodb+srv://dwadhwa:dzRtn9jb0AW1gjKZ@cluster1.2oiy60e.mongodb.net/?retryWrites=true&w=majority&appName=Cluster1";
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public MongoDB(){
        try{
            client = MongoClients.create(uri);
            database = client.getDatabase("user_db");
            collection = database.getCollection("user_collection");
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    // public MongoDB(MongoDatabase database, MongoCollection<Document> collection) {
    //     this.database = database;
    //     this.collection = collection;
    // }

    public void addEntry(String username, String password){
        Document d = new Document("_id", username).append("password", password).append("basic price", 0).append("quality price", 0).append("advertising spent", 0);
        collection.insertOne(d);
    }

    public void saveDecisions(String username, int decision1, int decision2, int decision3){
        Document query = new Document().append("_id", username);

        Bson updates = Updates.combine(
                    Updates.set("basic price", decision1),
                    Updates.set("quality price", decision2),
                    Updates.set("advertising spent", decision3));

        UpdateOptions options = new UpdateOptions().upsert(false);

        collection.updateOne(query, updates, options);
    }

    public Integer[] recieveEnemyInputs(String username){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(!doc.get("_id").equals(username)){
                int enemy1 = (Integer) doc.get("basic price");
                int enemy2 = (Integer) doc.get("quality price");
                int enemy3 = (Integer) doc.get("advertising spent");
                Integer[] ret = new Integer[3];
                ret[0] = enemy1;
                ret[1] = enemy2;
                ret[2] = enemy3;
                return ret;
            }
        }
        return null;
    }

    public Integer[] recieveUserInputs(String username){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(doc.get("_id").equals(username)){
                int enemy1 = (Integer) doc.get("basic price");
                int enemy2 = (Integer) doc.get("quality price");
                int enemy3 = (Integer) doc.get("advertising spent");
                Integer[] ret = new Integer[3];
                ret[0] = enemy1;
                ret[1] = enemy2;
                ret[2] = enemy3;
                return ret;
            }
        }
        return null;
    }

    public boolean correctUsernameCheck(String username, String password){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(doc.get("_id").equals(username)){
                if(doc.get("password").equals(password)){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    public long getSize(){
        return this.collection.countDocuments();
    }

}
