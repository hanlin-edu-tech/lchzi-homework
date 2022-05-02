package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.smallrye.common.constraint.Assert
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class KotlinServiceTest {

    @Inject
    lateinit var kotlinService: KotlinService

    @Test
    fun insertUserTest() {
        val account = "aaron"
        val password = "aaron123"
        val result1 = kotlinService.existsUser(account, password)
        Assert.assertFalse(result1)
        kotlinService.insertUser(account, password)
        val result = kotlinService.existsUser(account, password)
        Assert.assertTrue(result)
    }

}




