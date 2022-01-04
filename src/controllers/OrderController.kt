package com.example.controllers

import com.example.models.Order
import com.example.modelsDB.OrderDB
import com.example.modelsDB.ProduktDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class OrderController {
    fun adOrder(order: Order) {
        transaction {
            OrderDB.insert{
                it[OrderId] = order.OrderId
                it[Email] = order.Email
                it[Status] = order.Status
                it[Opis] = order.Opis
            }
        }
    }
    fun getAllbyEmail(Email:String):ArrayList<Order>{
        val orders : ArrayList<Order> = ArrayList()
        transaction {
            OrderDB.select(OrderDB.Email eq Email).map{
                orders.add(Order(it[OrderDB.OrderId],it[OrderDB.Email],it[OrderDB.Status],it[OrderDB.Opis]))
            }
        }
        return orders
    }
    fun getbyOrderId(OrderId:String):ArrayList<Order> {
        val orders: ArrayList<Order> = ArrayList()
        transaction {
            OrderDB.select(OrderDB.OrderId eq OrderId).map {
                orders.add(Order(it[OrderDB.OrderId],it[OrderDB.Email],it[OrderDB.Status],it[OrderDB.Opis]))
            }
        }
        return orders
    }
    fun update(order: Order){
        transaction {
            ProduktDB.update({ OrderDB.OrderId eq order.OrderId}) {
                it[OrderDB.Status] = order.Status
                it[OrderDB.Opis] = order.Opis
            }
        }
    }
    fun delete(OrderId: String){
        transaction {
            OrderDB.deleteWhere{ OrderDB.OrderId eq OrderId }
        }
    }
}