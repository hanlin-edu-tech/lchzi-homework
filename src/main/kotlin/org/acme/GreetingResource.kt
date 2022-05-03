package org.acme

import com.mongodb.MongoException
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import org.bson.Document
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@ApplicationScoped
@Path("UserData")
class userData(private  val kotlinService: KotlinService) {

    @Inject
    lateinit var InsertUser: Template
    @GET()
    @Produces(MediaType.TEXT_PLAIN)
    fun loginPage(): TemplateInstance{
        return InsertUser.instance()
    }

    @Path("UserInsert")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    fun userInsert(body: Map<String, String>){

        var acc: String = body["newAcc"]!!
        var pw: String = body["newPw"]!!
        try {
            kotlinService.insertUser(acc, pw)
        } catch (e: MongoException) {
            e.printStackTrace()
        }
    }

    @Path("Check")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    fun searchID(body: Map<String, String>){
        var acc: String = body["newAcc"]!!
        var pw: String = body["newPw"]!!
        try {
            if (kotlinService.existsUser(acc, pw)){
                println("have this account")
            }else{
                println("dont have this account")
            }
        } catch (e: MongoException) {
            e.printStackTrace()
        }
    }
}

@Path("/UserProfile")
class ProfileData(private  val kotlinService: KotlinService) {
    @Path("ProfileData")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun profileDataSearch(): ArrayList<Document> {
        return kotlinService.searchProfileData(1)
    }

    @Path("UpdatePro")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    fun updataProfile(){
        try {
            kotlinService.updateProfileData(1, 10000)
        }catch (e: MongoException){
            e.printStackTrace()
        }
    }
}




//    @Path("/Search/{_id}")
//    @Produces(MediaType.TEXT_PLAIN)
//    @PUT
//    fun searchID(@PathParam("_id") _id: Int): ArrayList<Document> {
//        return kotlinService.SUser(_id)
//    }

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
//
//@Path("/hello2")
//class HelloTest {
//    @GET()
//    @Produces(MediaType.TEXT_PLAIN)
//    fun printWorld() = "My first quarkus application."
//}
//
//@Path("/hello")
//class HelloResource {
//    @Inject
//    lateinit var mongoData: Template
//    @GET()
//    @Produces(MediaType.TEXT_PLAIN)
//    fun printWorld(): TemplateInstance{
//        return mongoData.data("name", "I AM here")
//    }
//}

