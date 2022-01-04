package com.example.controllers


import com.example.models.Produkt
import com.example.modelsDB.ProduktDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProduktController {
    fun adProdukt(produkt: Produkt) {
        transaction {
            ProduktDB.insert{
                it[Nazwa]= produkt.Nazwa
                it[Kod] = produkt.Kod
                it[Cena] = produkt.Cena
                it[IdKategoria] = produkt.IdKategoria
                it[DostIlosc] = produkt.DostIlosc
            }
        }
    }
    fun getAll():ArrayList<Produkt>{
        val produkty : ArrayList<Produkt> = ArrayList()
        transaction {
            ProduktDB.selectAll().map{
                produkty.add(Produkt(it[ProduktDB.Nazwa],it[ProduktDB.Kod],it[ProduktDB.Cena],it[ProduktDB.IdKategoria],it[ProduktDB.DostIlosc]))
            }
        }
        return produkty
    }
    fun getbyKod(Kod:String):ArrayList<Produkt> {
        val produkty: ArrayList<Produkt> = ArrayList()
        transaction {
            ProduktDB.select(ProduktDB.Kod eq Kod).map {
                produkty.add(Produkt(it[ProduktDB.Nazwa],it[ProduktDB.Kod],it[ProduktDB.Cena],it[ProduktDB.IdKategoria],it[ProduktDB.DostIlosc]))
            }
        }
        return produkty
    }
    fun update(produkt: Produkt){
        transaction {
            ProduktDB.update({ ProduktDB.Kod eq produkt.Kod }) {
                it[Nazwa]= produkt.Nazwa
                it[Kod] = produkt.Kod
                it[Cena] = produkt.Cena
                it[IdKategoria] = produkt.IdKategoria
                it[DostIlosc] = produkt.DostIlosc
            }
        }
    }
    fun delete(Kod: String){
        transaction {
            ProduktDB.deleteWhere{ ProduktDB.Kod eq Kod }
        }
    }
}