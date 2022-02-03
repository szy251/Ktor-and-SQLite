package com.example.routes

import com.example.controllers.OrderController
import com.example.models.Order
import com.example.models.Produkt
import com.example.models.RespondBody
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
fun Application.registerOrderRoutes(){
    routing{
        OrderRouting()
    }
}
fun Route.OrderRouting() {
    val orderController = OrderController()
    get("/order/{email}"){
        val email = call.parameters["email"] ?: return@get call.respondText("Bad Request", status = HttpStatusCode.BadRequest)
        call.respond(orderController.getAllbyEmail(email))
    }
    post("/order"){
        val order = call.receive<Order>()
        if(orderController.getbyOrderId(order.OrderId).isNotEmpty())
            call.respond(RespondBody(false,"Już istnieje"))
        orderController.adOrder(order)
        call.respond(RespondBody(true,"Poprawnie dodany"))
    }
    get("/{id}"){
        val id = call.parameters["id"] ?: return@get call.respondText(
            "Niepoprawne dane",
            status = HttpStatusCode.BadRequest
        )
        call.respond(orderController.getbyOrderId(id))
    }
    put{
        val order = call.receive<Order>()
        orderController.update(order)
        call.respond(RespondBody(true,"Poprawnie zaktualizowany"))
    }
    delete("/order/{id}"){
        val id = call.parameters["id"] ?: return@delete call.respond(RespondBody(false,"Złe dane"))
        orderController.delete(id)
        call.respond(RespondBody(true,"Poprawnie usunięty"))
    }
}