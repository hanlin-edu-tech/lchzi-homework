package org.acme

import com.mongodb.client.model.Filters
import io.quarkus.test.junit.QuarkusTest
import io.smallrye.common.constraint.Assert
import org.bson.conversions.Bson
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    @Inject
    lateinit var userservice: UserSevice

    val account = "aaron456"
    val password = "aaron222"

    @AfterAll
    fun resetDatabase(){
        val filiter = Filters.and(Filters.eq("Account", account), Filters.eq("Password", password))
        userservice.getCollection.deleteOne(filiter)
    }

    @Test
    fun existsUserTest(){
        val result = userservice.existsUser(account, password)
        Assert.assertFalse(result)
    }

    @Test
    fun insertUserTest() {
        userservice.insertUser(account, password)
        val result = userservice.existsUser(account, password)
        Assert.assertTrue(result)
    }
}




