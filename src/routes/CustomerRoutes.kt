package com.example.routes
import com.example.controllers.CustomerController
import com.example.models.Customer
import com.example.models.RespondBody
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
      get("{Email}/{Haslo}"){
            val Email = call.parameters["Email"] ?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
          val Haslo = call.parameters["Haslo"] ?: return@get call.respondText(
              "Niepoprawne dane",
              status = HttpStatusCode.BadRequest
          )
            call.respond(customerController.getbyEmailandHaslo(Email,Haslo))
        }
        post{
            val customer = call.receive<Customer>()
            if(customerController.getbyEmail(customer.Email).isNotEmpty())
                call.respond(RespondBody(false,"Już istnieje"))
            customerController.adCustomer(customer)
            call.respond(RespondBody(true,"Poprawnie dodany"))
        }
        put{
            val customer = call.receive<Customer>()
            customerController.update(customer)
            call.respond(RespondBody(true,"Poprawnie zaktualizowany"))
        }
        delete("{Email}"){
            val Email = call.parameters["Email"] ?: return@delete call.respond(RespondBody(false,"Złe dane"))
            customerController.delete(Email)
            call.respond(RespondBody(true,"Usunięto poprawnie"))
        }
    }
}
