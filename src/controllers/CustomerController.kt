package com.example.controllers

import com.example.models.Customer
import com.example.modelsDB.CustomerDB
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CustomerController {
    fun adCustomer(customer : Customer) {
        transaction {
            CustomerDB.insert{
                it[Imie] = customer.Imie
                it[Nazwisko] = customer.Nazwisko
                it[Email] = customer.Email
                it[Nazwa] = customer.Nazwa
                it[Haslo] = customer.Haslo
            }
        }
    }
    fun getAllCustomers():ArrayList<Customer>{
         val customers : ArrayList<Customer> = ArrayList()
        transaction {
            CustomerDB.selectAll().map{
                customers.add(Customer(Imie=it[CustomerDB.Imie],
                    Nazwisko = it[CustomerDB.Nazwisko],
                    Email = it[CustomerDB.Email],
                    Nazwa = it[CustomerDB.Nazwa],
                    Haslo = it[CustomerDB.Haslo]))
            }
        }
        return customers
    }
    fun getbyEmailandHaslo(Email:String, Haslo:String):ArrayList<Customer> {
        val customer: ArrayList<Customer> = ArrayList()
        transaction {
            CustomerDB.select(CustomerDB.Email eq Email and (CustomerDB.Haslo eq Haslo)).map {
                customer.add(Customer(
                    it[CustomerDB.Imie],
                    it[CustomerDB.Nazwisko],
                    it[CustomerDB.Email],
                    it[CustomerDB.Nazwa],
                    it[CustomerDB.Haslo]
                )
                )
            }
        }
        return customer
    }
    fun getbyEmail(Email:String):ArrayList<Customer> {
        val customer: ArrayList<Customer> = ArrayList()
        transaction {
            CustomerDB.select(CustomerDB.Email eq Email).map {
                customer.add(Customer(
                    it[CustomerDB.Imie],
                    it[CustomerDB.Nazwisko],
                    it[CustomerDB.Email],
                    it[CustomerDB.Nazwa],
                    it[CustomerDB.Haslo]
                )
                )
            }
        }
        return customer
    }
    fun update(customer: Customer){
        transaction {
            CustomerDB.update({ CustomerDB.Email eq customer.Email }) {
                it[Imie] = customer.Imie
                it[Nazwisko] = customer.Nazwisko
                it[Nazwa] = customer.Nazwa
                it[Haslo] = customer.Haslo
            }
        }
    }
    fun delete(Email: String){
        transaction {
            CustomerDB.deleteWhere{CustomerDB.Email eq Email }
        }
    }
}