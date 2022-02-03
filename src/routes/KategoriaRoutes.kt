package com.example.routes

import com.example.controllers.KategoriaController
import com.example.models.Kategoria
import com.example.models.RespondBody
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
        get("/{IdKategoria}"){
            val IdKategoria = call.parameters["IdKategoria"] ?: return@get call.respondText(
                "Niepoprawne dane",
                status = HttpStatusCode.BadRequest
            )
            call.respond(kategoriaController.getByIdKategoria(IdKategoria))
        }
        post{
            val kategoria = call.receive<Kategoria>()
            if(kategoriaController.getByIdKategoria(kategoria.IdKategoria).isNotEmpty())
                call.respond(RespondBody(false,"Już istnieje"))
            kategoriaController.addKategoria(kategoria)
            call.respond(RespondBody(true,"Poprawnie dodana"))
        }
        put{
            val kategoria = call.receive<Kategoria>()
            kategoriaController.update(kategoria)
            call.respond(RespondBody(true,"Poprawnie zaktualizowana"))
        }
        delete("/{IdKategoria}"){
            val IdKategoria = call.parameters["IdKategoria"] ?: return@delete call.respond(RespondBody(false,"Złe dane"))
            kategoriaController.delete(IdKategoria)
            call.respond(RespondBody(true,"Poprawnie usunięta"))
        }
    }
}



