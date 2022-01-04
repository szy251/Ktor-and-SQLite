package com.example.modelsDB

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column

object KoszykDB : Table(){
    var Ilosc : Column<Int> = integer("Ilosc")
    var Kod :Column<String> = varchar("Kod",50).references(ProduktDB.Kod)
    var Email:Column<String> = varchar("Email",50).references(CustomerDB.Email)
}