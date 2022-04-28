package org.acme

import com.mongodb.MongoException
import org.bson.Document
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/hello")
class HelloResource {
    @GET()
    @Produces(MediaType.TEXT_PLAIN)
    fun printWorld() = "hello"
}
@Path("warm/up")
class GradlePractice{
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun printWorld() = "My first quarkus application."
}



@Path("/Search/{_id}")
class userid(private val kotlinService: KotlinService) {

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    fun searchID(@PathParam("_id") _id: Int): ArrayList<Document> {
        return kotlinService.SUser(_id)
    }
    @Path("Profile")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun searchPro(@PathParam("_id") _id: Int): ArrayList<Document> {
        return kotlinService.SUserPro(_id)
    }
}

@Path("/mongotest")
class MongoTest(private val kotlinService: KotlinService) {

//    var employee: Document = Document()
//        .append("Account", "Java Developer")
//        .append("Password", "ccc")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun mongo(){
        try {

//            kotlinService.insertData(employee)
            println(kotlinService.findAll())
        }catch (e: MongoException){
            e.printStackTrace()
        }
    }
}

