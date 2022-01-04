package com.example.modelsDB
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column
object ProduktDB:Table() {
    var Nazwa : Column<String> = varchar("Nazwa",50)
    var Kod : Column<String> = varchar("Kod", 50).uniqueIndex()
    var Cena : Column <Double> = double("Cena")
    var IdKategoria : Column<String> = varchar("IdKategoria",50).references(KategoriaDB.IdKategoria)
    var DostIlosc : Column<Int> = integer("DostIlosc")
    override val primaryKey = PrimaryKey(ProduktDB.Kod, name= "Kod_PK")
}