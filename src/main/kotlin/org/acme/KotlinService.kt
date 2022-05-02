package org.acme

import com.mongodb.BasicDBObject
import com.mongodb.client.*
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class KotlinService(private val mongoClient: MongoClient) {
    var getDatabase: MongoDatabase = mongoClient.getDatabase("Warmup")
    var getCollection: MongoCollection<Document> = getDatabase.getCollection("User")
    var getCollectionPro: MongoCollection<Document> = getDatabase.getCollection("UserProfile")

    fun insertUser(acc: String, pw: String) {
        var findIterable: FindIterable<Document> = getCollection.find().sort(BasicDBObject("_id", -1)).limit(1)
        var mongoCursor: MongoCursor<Document> = findIterable.iterator()
        var idNum: Int = mongoCursor.next().get("_id") as Int
        idNum += 1
        var newuserData: Document = Document()
            .append("_id", idNum)
            .append("Account", acc)
            .append("Password", pw)
        getCollection.insertOne(newuserData)
    }

    fun existsUser(Acc: String, Pw: String): Boolean{
        val filiter = and(eq("Account", Acc), eq("Password", Pw))
        return getCollection.find(filiter).firstOrNull() != null
    }

    fun findAll(): Int {
        var findIterable: FindIterable<Document> = getCollection.find().sort(BasicDBObject("_id", -1)).limit(1)
        var mongoCursor: MongoCursor<Document> = findIterable.iterator()
        var idNum: Int = mongoCursor.next().get("_id") as Int
        idNum += 1

        var newuserData: Document = Document()
            .append("_id", idNum)
            .append("Account", "Acc")
            .append("Password", "Pw")
        getCollection.insertOne(newuserData)
        return idNum
    }

//    fun SUser(_id: Int): ArrayList<Document> {
//        fun findIterable(query: BasicDBObject): FindIterable<Document> = getCollection.find(query)
//        var mongoCursor: MongoCursor<Document> = findIterable(BasicDBObject("_id", _id)).iterator()
//        var newresult = arrayListOf<Document>()
//        while (mongoCursor.hasNext()) {
//            newresult.add(mongoCursor.next())
//        }
//        return newresult
//    }

    fun SUserPro(): ArrayList<Document> {
        fun findIterable(query : BasicDBObject): FindIterable<Document> = getCollectionPro.find(query)
        var mongoCursor : MongoCursor<Document> = findIterable(BasicDBObject("User_Credit", 2)).iterator()
        var newresult = arrayListOf<Document>()
        while (mongoCursor.hasNext()){
            newresult.add(mongoCursor.next())
        }
        return newresult
    }



    fun SearchProfileData(_id: Int): ArrayList<Document> {
        fun findIterable(query: BasicDBObject): FindIterable<Document> = getCollection.find(query)
        var mongoCursor: MongoCursor<Document> = findIterable(BasicDBObject("_id", _id)).iterator()
        var newresult = arrayListOf<Document>()
        while (mongoCursor.hasNext()) {
            newresult.add(mongoCursor.next())
        }
        return newresult
    }

    fun UpdateProfileData(userId: Int,credit: Int) {
        val updateFliiter: Document = Document()
            .append("UserId", userId)
        val updateValue: Document = Document()
            .append("User_Credit", credit)
        val updateObject :Document = Document()
        updateObject.append("\$set", updateValue)
        getCollectionPro.updateOne(updateFliiter, updateObject)
    }
}




