package com.example.routes

import com.example.controllers.KoszykController
import com.example.models.Koszyk
import com.example.models.Produkt
import com.example.models.RespondBody
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
            if(koszykController.getProduktForEmail(produkt.Email,produkt.Kod).isNotEmpty())
                call.respond(RespondBody(false,"Już istnieje"))
            koszykController.adKoszyk(produkt)
            call.respond(RespondBody(true,"Poprawnie dodany"))
        }
        put{
            val koszyk = call.receive<Koszyk>()
            koszykController.update(koszyk)
            call.respond(RespondBody(true,"Poprawnie zaktualizowany"))
        }
        delete("/{Email}"){
            val Email = call.parameters["Email"] ?: return@delete call.respond(RespondBody(false,"Złe dane"))
            koszykController.delete(Email)
            call.respond(RespondBody(true,"Poprawnie usunięty"))
        }
        delete("/{Email}/{Kod}"){
            val Email = call.parameters["Email"] ?: return@delete call.respond(RespondBody(false,"Złe dane"))
            val Kod = call.parameters["Kod"] ?: return@delete call.respond(RespondBody(false,"Złe dane"))
            koszykController.delete(Email,Kod)
            call.respond(RespondBody(true,"Poprawnie usunięty"))
        }
    }
}