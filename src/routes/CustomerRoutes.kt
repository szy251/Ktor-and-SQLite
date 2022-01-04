package com.example.routes
import com.example.controllers.CustomerController
import com.example.models.Customer
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerCustomerRoutes() {
    routing {
        CustomerRouting()
    }
}
fun Route.CustomerRouting(){
    route("/customer"){
        val customerController = CustomerController()
        get{
           call.respond(customerController.getAllCustomers())
        }
      get("/get/{Email}"){
            val Email = call.parameters["Email"] ?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
            call.respond(customerController.getbyEmail(Email))
        }
        post{
            val customer = call.receive<Customer>()
            customerController.adCustomer(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{Email}"){
            val Email = call.parameters["Email"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            customerController.delete(Email)
            call.respond(HttpStatusCode.Accepted)
        }
    }
}
