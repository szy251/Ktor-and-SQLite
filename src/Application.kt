package com.example

import com.example.modelsDB.*
import com.example.routes.*
import io.ktor.application.*
import com.example.controllers.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.server.netty.*
import io.ktor.server.engine.embeddedServer
import org.jetbrains.exposed.sql.*
import io.ktor.gson.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    Database.connect("jdbc:sqlite:./src/data/data.db", "org.sqlite.JDBC")
    transaction{

       // SchemaUtils.drop(CustomerDB, KategoriaDB, OrderDB, KoszykDB, ProduktDB)
        SchemaUtils.create(CustomerDB, KategoriaDB, OrderDB, KoszykDB, ProduktDB)

    }
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CallLogging){
            level = Level.DEBUG
        }

        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }

        routing {
            trace { application.log.trace(it.buildText()) }
        }
        configureRouting()
        registerCustomerRoutes()
        regiestrProduktRoutes()
        registerOrderRoutes()
        registerKoszykRoutes()
        registerKetegoriaRoutes()
    }.start(wait = true)
}
fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
