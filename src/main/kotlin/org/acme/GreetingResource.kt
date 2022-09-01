package org.acme

import com.mongodb.MongoException
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("UserData")
class UserData(private  val userservice: UserSevice) {
    @Inject
    lateinit var InsertUser: Template
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun loginPage(@QueryParam("Status") name: String?): TemplateInstance{
        return InsertUser.data("alert_msg", name)
    }

    @Path("UserInsert")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun userInsert(body: Map<String, String>){
        var acc: String = body["newAcc"]!!
        var pw: String = body["newPw"]!!
        try {
            userservice.insertUser(acc, pw)
        } catch (e: MongoException) {
            e.printStackTrace()
        }
    }

    @Path("Check")
    @GET
    fun searchID(@QueryParam("Account") account: String, @QueryParam("Password") password: String):Response{
        var acc: String = account
        var pw: String = password
        return try {
            if (userservice.existsUser(acc, pw)){
               Response.ok("Susscess").build()
            }else{
                println("bad")
                Response.ok("Account didnt find").build()
            }
        } catch (e: MongoException) {
            e.printStackTrace()
            Response.serverError().build()
        }
    }
}

@Path("/UserProfile")
class ProfileData(private  val userprofileservice: UserProfileService, private val userservice: UserSevice) {


    @Location("UserProfile")
    lateinit var userprofile: Template
    @GET
    fun profileDataSearch(@QueryParam("Account") account: String, @QueryParam("Password") password: String): TemplateInstance {
        var userid = userservice.getUserId(account, password)
        var userprofiledata = userprofileservice.searchProfileData(userid)

        return userprofile.data("Account","d", "Credit", "232")
    }
}

