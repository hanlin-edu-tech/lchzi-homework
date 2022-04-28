package org.acme

import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCursor
import com.mongodb.client.MongoDatabase
import org.bson.Document
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class KotlinService(private val mongoClient: MongoClient) {
    var getDatabase: MongoDatabase = mongoClient.getDatabase("Warmup")
    var getCollection: MongoCollection<Document> = getDatabase.getCollection("User")
    var getCollectionPro: MongoCollection<Document> = getDatabase.getCollection("UserProfile")

    fun findAll(){
        var findIterable: FindIterable<Document> = getCollection.find()
        var mongoCursor : MongoCursor<Document> = findIterable.iterator()
        while (mongoCursor.hasNext()){
            println(mongoCursor.next())
        }
    }

    fun SUser(_id: Int): ArrayList<Document> {
        fun findIterable(query : BasicDBObject): FindIterable<Document> = getCollection.find(query)
        var mongoCursor : MongoCursor<Document> = findIterable(BasicDBObject("_id",_id)).iterator()
        var newresult = arrayListOf<Document>()
        while (mongoCursor.hasNext()){
            newresult.add(mongoCursor.next())
        }
        return newresult
    }

    fun SUserPro(_id: Int): ArrayList<Document> {
        fun findIterable(query : BasicDBObject): FindIterable<Document> = getCollectionPro.find(query)
        var mongoCursor : MongoCursor<Document> = findIterable(BasicDBObject("UserId",_id)).iterator()
        var newresult = arrayListOf<Document>()
        while (mongoCursor.hasNext()){
            newresult.add(mongoCursor.next())
        }
        return newresult
    }
//    var getCollection: MongoCollection<Document> = mongoClient.getDatabase("Warmup").getCollection("User")
//    fun findIterable(query : BasicDBObject): FindIterable<Document> = getCollection.find(query)
//    var mongoCursor : MongoCursor<Document> = findIterable(BasicDBObject("_id",1)).iterator()
//    fun findAll(): ArrayList<Document> {
//        var newresult = arrayListOf<Document>()
//        while (mongoCursor.hasNext()){
//            newresult.add(mongoCursor.next())
//        }
//        return newresult
//    }
//    fun insertData(document: Document): InsertOneResult = getCollection.insertOne(document)
}



