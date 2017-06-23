package com.gaohuan.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohuan on 2017/5/6.
 */
public class UseMongoDB {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        try {
            //连接
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            MongoDatabase mongoDatabase = mongoClient.getDatabase("runoob");
            MongoCollection<Document> collection = mongoDatabase.getCollection("col");
            Document document = new Document();
            document.append("title", "MongoDB")
                    .append("description", "database")
                    .append("likes", 100)
                    .append("by", "Fly");
            List<Document> documents = new ArrayList<>();
            documents.add(document);
            System.out.println("--------------文档插入-------------");
            collection.insertMany(documents);
            printCollection(collection);
            System.out.println("--------------文档更新-------------");
            collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
            printCollection(collection);
            System.out.println("--------------文档删除-------------");
            collection.deleteOne(Filters.eq("likes", 200));
            collection.deleteMany(Filters.eq("likes", 200));
            printCollection(collection);
            //关闭
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void printCollection(MongoCollection<Document> collection) {
        MongoCursor<Document> mongoCursor = collection.find().iterator();
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
        }
        System.out.println("文档查询成功");
    }


}
