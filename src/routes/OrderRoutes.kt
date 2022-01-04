package com.example.routes

import com.example.controllers.OrderController
import com.example.models.Order
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
    post("/order/post"){
        val order = call.receive<Order>()
        orderController.adOrder(order)
        call.respondText("Zamowienie dodane", status = HttpStatusCode.Created)
    }
    delete("/order/{id}"){
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
        orderController.delete(id)
        call.respond(HttpStatusCode.Accepted)
    }
}