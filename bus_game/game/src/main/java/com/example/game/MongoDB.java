package com.example.game;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import com.mongodb.client.model.changestream.FullDocument;
import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;


public class MongoDB {
    String uri = "mongodb+srv://dwadhwa:RkzwC5uipJApsk2c@cluster0.nzyceaj.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    //String uri = "mongodb+srv://dwadhwa:dzRtn9jb0AW1gjKZ@cluster1.2oiy60e.mongodb.net/?retryWrites=true&w=majority&appName=Cluster1";
    MongoClient client;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public MongoDB(String collectionChoice){
        try{
            client = MongoClients.create(uri);
            database = client.getDatabase("user_db");
            collection = database.getCollection(collectionChoice);
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
        App.gameNumber = (this.collection.countDocuments()+App.numPlayers)/App.numPlayers;
        Document d = new Document("_id", username).append("password", password).append("basic price", 0).append("quality price", 0).append("advertising spend", 0).append("game number", App.gameNumber)
        .append("cumulative revenue", 0.0).append("cumulative profit", 0.0);
        collection.insertOne(d);
    }

    public void addAdmin(String password){
        Document d = new Document("_id", "admin").append("password", password).append("war", 30).append("new entrant", 60).append("customer increase", 5);
        collection.insertOne(d);
    }

    public ArrayList<Integer> getAdminInputs(){
        FindIterable<Document> documentCursor = collection.find();
        ArrayList<Integer> ret = new ArrayList<>();
        for(Document doc: documentCursor){
            if(doc.get("_id").equals("admin")){
                ret.add((Integer) doc.get("war"));
                ret.add((Integer) doc.get("new entrant"));
                ret.add((Integer) doc.get("customer increase"));
                return ret;
            }
        }
        ret.add(30);
        ret.add(60);
        ret.add(5);
        return ret;
    }

    public void saveDecisions(String username, int decision1, int decision2, int decision3){
        Document query = new Document().append("_id", username);

        Bson updates = Updates.combine(
                    Updates.set("basic price", decision1),
                    Updates.set("quality price", decision2),
                    Updates.set("advertising spend", decision3));

        UpdateOptions options = new UpdateOptions().upsert(false);

        collection.updateOne(query, updates, options);
    }

    public void saveAdminDecisions(ArrayList<Integer> decisions){
        Document query = new Document().append("_id", "admin");

        Bson updates = Updates.combine(
                    Updates.set("war", decisions.get(0)),
                    Updates.set("new entrant", decisions.get(1)),
                    Updates.set("customer increase", decisions.get(2)));

        UpdateOptions options = new UpdateOptions().upsert(false);

        collection.updateOne(query, updates, options);
    }

    public void saveCumulative(String username, double cumulativeRevenue, double cumulativeProfit){
        Document query = new Document().append("_id", username);

        Bson updates = Updates.combine(
                    Updates.set("cumulative revenue", cumulativeRevenue),
                    Updates.set("cumulative profit", cumulativeProfit));
        
        UpdateOptions options = new UpdateOptions().upsert(false);

        collection.updateOne(query, updates, options);
    }

    public String getEnemyUsername(String username, Long gameNumber){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(!doc.get("_id").equals(username) && (Long) doc.get("game number") == gameNumber){
                return (String) doc.get("_id");
            }
        }
        return null;
    }

    public ArrayList<String> getEnemyUsernames(String username, Long gameNumber){
        FindIterable<Document> documentCursor = collection.find();
        ArrayList<String> usernames = new ArrayList<>();
        for(Document doc: documentCursor){
            if(!doc.get("_id").equals(username) && (Long) doc.get("game number") == gameNumber){
                usernames.add((String) doc.get("_id"));
            }
        }
        return usernames;
    }

    public Double[] getUserCumulative(String username){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(doc.get("_id").equals(username)){
                Double revenue = (Double) doc.get("cumulative revenue");
                Double profit = (Double) doc.get("cumulative profit");
                Double[] ret = new Double[2];
                ret[0] = revenue;
                ret[1] = profit;
                return ret;
            }
        }
        return null;
    }

    public Integer[] recieveEnemyInputs(String username, long gameNumber){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(!doc.get("_id").equals(username) && doc.get("game number").equals(gameNumber)){
                int enemy1 = (Integer) doc.get("basic price");
                int enemy2 = (Integer) doc.get("quality price");
                int enemy3 = (Integer) doc.get("advertising spend");
                Integer[] ret = new Integer[3];
                ret[0] = enemy1;
                ret[1] = enemy2;
                ret[2] = enemy3;
                return ret;
            }
        }
        return null;
    }

    public ArrayList<Integer[]> recieveMultipleEnemyInputs(String username, long gameNumber){
        FindIterable<Document> documentCursor = collection.find();
        ArrayList<Integer[]> enemyInputs = new ArrayList<>();
        for(Document doc: documentCursor){
            if(!doc.get("_id").equals(username) && doc.get("game number").equals(gameNumber)){
                int enemy1 = (Integer) doc.get("basic price");
                int enemy2 = (Integer) doc.get("quality price");
                int enemy3 = (Integer) doc.get("advertising spend");
                Integer[] ret = new Integer[3];
                ret[0] = enemy1;
                ret[1] = enemy2;
                ret[2] = enemy3;
                enemyInputs.add(ret);
            }
        }
        return enemyInputs;
    }

    public Integer[] recieveUserInputs(String username){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(doc.get("_id").equals(username)){
                int enemy1 = (Integer) doc.get("basic price");
                int enemy2 = (Integer) doc.get("quality price");
                int enemy3 = (Integer) doc.get("advertising spend");
                Integer[] ret = new Integer[3];
                ret[0] = enemy1;
                ret[1] = enemy2;
                ret[2] = enemy3;
                return ret;
            }
        }
        return null;
    }

    public Long getGameNumber(String username){
        FindIterable<Document> documentCursor = collection.find();
        for(Document doc: documentCursor){
            if(doc.get("_id").equals(username)){
                long gN = (Long) doc.get("game number");
                return gN;
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
    public void stopUntilChange() throws InterruptedException{
        CountDownLatch latch = new CountDownLatch(1);

        try (MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch().fullDocument(FullDocument.UPDATE_LOOKUP).iterator()) {
            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> changeStreamDocument = cursor.next();
                Document fullDocument = changeStreamDocument.getFullDocument();

                // Optionally, you can handle the change here
                // For demonstration, we just release the latch
                latch.countDown();
                return;
            }
        }

        // This will block until a change is detected and latch.countDown() is called
        latch.await();
    }
    

}
