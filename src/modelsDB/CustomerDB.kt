package com.example.modelsDB
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column
object CustomerDB :Table() {
    var Imie : Column<String> = varchar("Imie",50)
    var Nazwisko : Column<String> = varchar("Nazwisko",50)
    var Email : Column<String> = varchar("Email",50).uniqueIndex()
    var Nazwa : Column<String> = varchar("Nazwa",50)
    var Haslo : Column<String> = varchar("Haslo",50)
    override val primaryKey = PrimaryKey(CustomerDB.Email,name = "Email_PK")
}