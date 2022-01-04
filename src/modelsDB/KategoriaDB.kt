package com.example.modelsDB
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Column
object KategoriaDB: Table() {
    var Nazwa : Column<String> = varchar("Nazwa",50)
    var IdKategoria : Column<String> = varchar("IdKategoria",50).uniqueIndex()
    override val primaryKey = PrimaryKey(KategoriaDB.IdKategoria,name="IdKategoria_PK")
}