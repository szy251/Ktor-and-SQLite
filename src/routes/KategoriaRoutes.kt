package com.example.routes

import com.example.controllers.KategoriaController
import com.example.models.Kategoria
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
fun Application.registerKetegoriaRoutes()
{
    routing{
        KategoriaRouting()
    }
}
fun Route.KategoriaRouting(){
    route("/kategoria"){
        val kategoriaController = KategoriaController()
        get{
           call.respond(kategoriaController.getAll())
        }
        post{
            val kategoria = call.receive<Kategoria>()
            kategoriaController.addKategoria(kategoria)
            call.respondText("Kategoria dodana poprawnie", status = HttpStatusCode.Created)
        }
        delete("{IdKategoria}"){
            val IdKategoria = call.parameters["IdKategoria"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            kategoriaController.delete(IdKategoria)
            call.respond(HttpStatusCode.Accepted)
        }
    }
}



