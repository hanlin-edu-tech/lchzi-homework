package org.acme

import com.mongodb.BasicDBObject
import com.mongodb.client.*
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserProfileService(private val mongoClient: MongoClient) {


    var getDatabase: MongoDatabase = mongoClient.getDatabase("Warmup")
    var getCollectionPro: MongoCollection<Document> = getDatabase.getCollection("UserProfile")

    fun searchProfileData(id: Int): ArrayList<Document> {
        fun findIterable(query: BasicDBObject): FindIterable<Document> = getCollectionPro.find(query)
        var mongocursor: MongoCursor<Document> = findIterable(BasicDBObject("UserId", id)).iterator()
        var newresult = arrayListOf<Document>()
        while (mongocursor.hasNext()) {
            newresult.add(mongocursor.next())
        }
        return newresult
    }

    fun updateProfileData(user_id: Int,credit: Int) {

        val updatefliiter: Document = Document()
            .append("UserId", user_id)
        val updatevalue: Document = Document()
            .append("User_Credit", credit)
        val updateobject :Document = Document()
        updateobject.append("\$set", updatevalue)
        getCollectionPro.updateOne(updatefliiter, updateobject)
    }
}


