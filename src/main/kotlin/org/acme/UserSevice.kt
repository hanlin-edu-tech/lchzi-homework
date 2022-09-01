package org.acme

import com.mongodb.BasicDBObject
import com.mongodb.client.*
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserSevice(private val mongoClient: MongoClient) {
    var getDatabase: MongoDatabase = mongoClient.getDatabase("Warmup")
    var getCollection: MongoCollection<Document> = getDatabase.getCollection("User")

    fun insertUser(acc: String, pw: String) {
        var findIterable: FindIterable<Document> = getCollection.find().sort(BasicDBObject("_id", -1)).limit(1)
        var mongoCursor: MongoCursor<Document> = findIterable.iterator()
        var idNum: Int = mongoCursor.next().get("_id") as Int
        idNum += 1
        var newuserData: Document = Document()
            .append("_id", idNum)
            .append("Account", acc)
            .append("Password", pw)

        var userData: Document = Document("_id", "idNum")
        getCollection.insertOne(newuserData)
    }

    fun existsUser(Acc: String, Pw: String): Boolean {
        val filiter = and(eq("Account", Acc), eq("Password", Pw))
        return getCollection.find(filiter).firstOrNull() != null
    }

    fun getUserId(Acc: String, Pw: String): Int{
        val filiter = and(eq("Account", Acc), eq("Password", Pw))
        var finditerable: FindIterable<Document> = getCollection.find(filiter)
        var mongocursor: MongoCursor<Document> = finditerable.iterator()

        return mongocursor.next().get("_id") as Int
    }
}


