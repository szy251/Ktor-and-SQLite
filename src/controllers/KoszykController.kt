package com.example.controllers

import com.example.models.Koszyk
import com.example.modelsDB.KoszykDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class KoszykController {
    fun adKoszyk(koszyk: Koszyk) {
        transaction {
            KoszykDB.insert{
               it[Ilosc] = koszyk.Ilosc
                it[Kod] = koszyk.Kod
                it[Email] = koszyk.Email
            }
        }
    }
    fun getAllbyEmail(Email:String):ArrayList<Koszyk>{
        val koszyk : ArrayList<Koszyk> = ArrayList()
        transaction {
            KoszykDB.select(KoszykDB.Email eq Email).map {
                koszyk.add(Koszyk(it[KoszykDB.Ilosc],it[KoszykDB.Kod],it[KoszykDB.Email]))
            }
        }
        return koszyk
    }
    fun getProduktForEmail(Email:String,Kod: String):ArrayList<Koszyk>{
        val koszyk : ArrayList<Koszyk> = ArrayList()
        transaction {
            KoszykDB.select(KoszykDB.Email eq Email and (KoszykDB.Kod eq Kod)).map {
                koszyk.add(Koszyk(it[KoszykDB.Ilosc],it[KoszykDB.Kod],it[KoszykDB.Email]))
            }
        }
        return koszyk
    }
    fun update(koszyk: Koszyk){
        transaction {
            KoszykDB.update({ KoszykDB.Kod eq koszyk.Kod and ( KoszykDB.Email eq koszyk.Email )}) {
               it[Ilosc] = koszyk.Ilosc
            }
        }
    }
    fun delete(Email: String){
        transaction {
            KoszykDB.deleteWhere{ KoszykDB.Email eq Email }
        }
    }
    fun delete(Email: String, Kod:String){
        transaction {
            KoszykDB.deleteWhere{ KoszykDB.Email eq Email and(KoszykDB.Kod eq Kod) }
        }
    }
}