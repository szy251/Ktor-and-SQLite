package com.example.routes

import com.example.controllers.KoszykController
import com.example.models.Koszyk
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
fun Application.registerKoszykRoutes(){
    routing{
        KoszykRouting()
    }
}
fun Route.KoszykRouting(){
    route("/koszyk"){
        val koszykController = KoszykController()
        get("/{Email}"){
            val Email = call.parameters["Email"]?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
            call.respond(koszykController.getAllbyEmail(Email))
        }
        get("/{Email}/{Kod}"){
            val Email = call.parameters["Email"]?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
            val Kod = call.parameters["Kod"]?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
            call.respond(koszykController.getProduktForEmail(Email, Kod))
        }
        post{
            val produkt = call.receive<Koszyk>()
            koszykController.adKoszyk(produkt)
            call.respondText("Produkt dodany poprawnie", status = HttpStatusCode.Created)
        }
        delete("/delete/{Email}"){
            val Email = call.parameters["Email"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            koszykController.delete(Email)
        }
    }
}