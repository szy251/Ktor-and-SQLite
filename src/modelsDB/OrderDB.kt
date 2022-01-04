package com.example.modelsDB
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column
object OrderDB:Table() {
    var OrderId: Column<String> = varchar("OrderId",50).uniqueIndex()
    var Email : Column<String> = varchar("Email",50).references(CustomerDB.Email)
    var Status : Column<String> = varchar("Status",50)
    var Opis: Column<String> = text("Opis")
}