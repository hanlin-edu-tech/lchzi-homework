package org.acme

import com.mongodb.MongoException
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import org.bson.Document
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


@ApplicationScoped
@Path("/hello")
class HelloResource {
    @Inject
    lateinit var mongoData: Template
    @GET()
    @Produces(MediaType.TEXT_PLAIN)
    fun printWorld(): TemplateInstance{
        return mongoData.data("name", "I AM here")
    }
}

@Path("/userData")
class userDataPage{
    @Inject
    lateinit var InsertUser: Template
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun userInsert():TemplateInstance{
        return InsertUser.instance()
    }
}

@Path("/UserData")
class userData(private  val kotlinService: KotlinService) {
    @Path("UserInsert")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    fun UserInsert() {
        try {
            kotlinService.InsertUser("Account", "Password")
        } catch (e: MongoException) {
            e.printStackTrace()
        }
    }
}

@Path("/Search/{_id}")
class userid(private val kotlinService: KotlinService) {

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    fun searchID(@PathParam("_id") _id: Int): ArrayList<Document> {
        return kotlinService.SUser(_id)
    }
}



//@Path("/UserData")
//class userData(private  val kotlinService: KotlinService){
//    @Path("UserInsert")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    fun UserInsert() {
//        try {
//            kotlinService.InsertUser("Account", "Password")
//        }catch (e: MongoException){
//            e.printStackTrace()
//        }
//    }
//    @Path("UserSearch/")
//    @Produces(MediaType.TEXT_PLAIN)
//    @GET
//    fun searchID(@PathParam("_id") _id: Int): ArrayList<Document> {
//        return kotlinService.SUser(_id)
//    }
//}

@Path("/UserProfile")
class ProfileData(private  val kotlinService: KotlinService) {
    @Path("ProfileData")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun ProfileDataSearch(): ArrayList<Document> {
        return kotlinService.SearchProfileData(1)
    }

    @Path("UpdatePro")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    fun UpdataProfile(){
        try {
            kotlinService.UpdateProfileData(1, 10000)
        }catch (e: MongoException){
            e.printStackTrace()
        }
    }
}

//@Path("warm/up")
//class GradlePractice{
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    fun printWorld() = "My first quarkus application."
//}


//@Path("mongotest/")
//class MongoTest(private val kotlinService: KotlinService) {
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.TEXT_PLAIN)
////    @GET
////    @Produces(MediaType.TEXT_PLAIN)
//    fun test(): Int {
//        return kotlinService.findAll()
//    }
//
//    @Path("Profile")
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    fun SUserPro(): ArrayList<Document> {
//        return kotlinService.SUserPro()
//    }
//}

@Path("/hello2")
class HelloTest {
    @GET()
    @Produces(MediaType.TEXT_PLAIN)
    fun printWorld() = "My first quarkus application."
}

