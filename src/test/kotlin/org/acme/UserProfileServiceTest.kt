package org.acme

import com.mongodb.BasicDBObject
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCursor
import com.mongodb.client.model.Filters
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.common.constraint.Assert
import org.bson.Document
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserProfileServiceTest {
    @Inject
    lateinit var userprofileservice: UserProfileService

    val credit: Int = 12345
    val newcredit:Int = 45678
    var idnum : Int = 0

    @BeforeAll
    fun setUserProfileData(){
        var mongocursor : MongoCursor<Document> = userprofileservice.getCollectionPro.find().sort(BasicDBObject("UserId", -1)).limit(1).iterator()
        idnum = (mongocursor.next().get("UserId") as Int) + 1
        var newtestdata: Document = Document()
            .append("UserId", idnum)
            .append("User_Credit", credit)
        userprofileservice.getCollectionPro.insertOne(newtestdata)
    }

    @AfterAll
    fun resetUserProfileData(){
        val filiter = Filters.and(Filters.eq("UserId", idnum), Filters.eq("User_Credit", newcredit))
        userprofileservice.getCollectionPro.deleteOne(filiter)
    }

    @Test
    fun searchProfileDataTest(){
        val result = userprofileservice.searchProfileData(idnum)
        Assert.assertNotNull(result)
    }

    @Test
    fun updateProfileDataTest(){
        userprofileservice.updateProfileData(idnum, newcredit)
        var creditdata = userprofileservice.searchProfileData(idnum)
        var result: Int = creditdata[0].get("User_Credit") as Int
        print(result)
        Assert.assertTrue( result == newcredit)
    }
}