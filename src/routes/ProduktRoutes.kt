package com.example.routes

import com.example.controllers.ProduktController
import com.example.models.Customer
import com.example.models.Produkt
import com.example.models.RespondBody
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
fun Application.regiestrProduktRoutes(){
    routing {
        ProduktRouting()
    }
}
fun Route.ProduktRouting(){
    route("/produkt"){
        val produktController =ProduktController()
        get{
          call.respond(produktController.getAll())
        }
        get("{Kod}"){
            val Kod = call.parameters["Kod"] ?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
            call.respond(produktController.getbyKod(Kod))
        }
        post{
            val produkt = call.receive<Produkt>()
            if(produktController.getbyKod(produkt.Nazwa).isNotEmpty())
                call.respond(RespondBody(false,"Już istnieje"))
            produktController.adProdukt(produkt)
            call.respond(RespondBody(true,"Poprawnie dodany"))
        }
        put{
            val produkt = call.receive<Produkt>()
            produktController.update(produkt)
            call.respond(RespondBody(true,"Poprawnie zaktualizowany"))
        }
        delete("{Kod}"){
            val Kod = call.parameters["Kod"] ?: return@delete call.respond(RespondBody(false,"Złe dane"))
            produktController.delete(Kod)
            call.respond(RespondBody(true,"Poprawnie usunięty"))
        }
    }
}