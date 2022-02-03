package com.example.controllers


import com.example.models.Produkt
import com.example.modelsDB.KoszykDB.Kod
import com.example.modelsDB.ProduktDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProduktController {
    fun adProdukt(produkt: Produkt) {
        transaction {
            ProduktDB.insert{
                it[Nazwa]= produkt.Nazwa
                it[Cena] = produkt.Cena
                it[IdKategoria] = produkt.IdKategoria
                it[Dostepnosc] = produkt.Dostepnosc
            }
        }
    }
    fun getAll():ArrayList<Produkt>{
        val produkty : ArrayList<Produkt> = ArrayList()
        transaction {
            ProduktDB.selectAll().map{
                produkty.add(Produkt(it[ProduktDB.Nazwa],it[ProduktDB.Cena],it[ProduktDB.IdKategoria],it[ProduktDB.Dostepnosc]))
            }
        }
        return produkty
    }
    fun getbyKod(Nazwa:String):ArrayList<Produkt> {
        val produkty: ArrayList<Produkt> = ArrayList()
        transaction {
            ProduktDB.select(ProduktDB.Nazwa eq Nazwa).map {
                produkty.add(Produkt(it[ProduktDB.Nazwa],it[ProduktDB.Cena],it[ProduktDB.IdKategoria],it[ProduktDB.Dostepnosc]))
            }
        }
        return produkty
    }
    fun update(produkt: Produkt){
        transaction {
            ProduktDB.update({ ProduktDB.Nazwa eq produkt.Nazwa }) {
                it[Cena] = produkt.Cena
                it[IdKategoria] = produkt.IdKategoria
                it[Dostepnosc] = produkt.Dostepnosc
            }
        }
    }
    fun delete(Nazwa: String){
        transaction {
            ProduktDB.deleteWhere{ ProduktDB.Nazwa eq Nazwa }
        }
    }
}