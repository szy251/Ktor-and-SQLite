package com.example.routes

import com.example.controllers.ProduktController
import com.example.models.Customer
import com.example.models.Produkt
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
        get("/get"){
          call.respond(produktController.getAll())
        }
        get("/get/{Kod}"){
            val Kod = call.parameters["Kod"] ?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
            call.respond(produktController.getbyKod(Kod))
        }
        post{
            val produkt = call.receive<Produkt>()
            produktController.adProdukt(produkt)
            call.respondText("Produkt dodany poprawnie", status = HttpStatusCode.Created)
        }
        delete("{Kod}"){
            val Kod = call.parameters["Kod"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            produktController.delete(Kod)
            call.respond(HttpStatusCode.Accepted)
        }
    }
}