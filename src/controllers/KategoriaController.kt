package com.example.controllers

import com.example.models.Kategoria
import com.example.modelsDB.KategoriaDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class KategoriaController {
    fun addKategoria(kategoria: Kategoria)
    {
        transaction {
            KategoriaDB.insert{
                it[Nazwa] = kategoria.Nazwa
                it[IdKategoria] = kategoria.IdKategoria
            }
        }
    }
    fun delete(IdKategoria:String){
        transaction {
            KategoriaDB.deleteWhere{KategoriaDB.IdKategoria eq IdKategoria}
        }
    }
    fun getAll():ArrayList<Kategoria>{
        val kategorie : ArrayList<Kategoria> = ArrayList()
        transaction {
            KategoriaDB.selectAll().map{
                kategorie.add(
                    Kategoria(
                    it[KategoriaDB.Nazwa],
                    it[KategoriaDB.IdKategoria]
                )
                )
            }
        }
        return kategorie
    }
    fun update(kategoria: Kategoria){
        val kategorie : ArrayList<Kategoria> = ArrayList()
        transaction {
            KategoriaDB.update({KategoriaDB.IdKategoria eq kategoria.IdKategoria}){
                        it[KategoriaDB.Nazwa] = kategoria.Nazwa
            }
        }
    }


}