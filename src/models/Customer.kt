package com.example.models
import kotlinx.serialization.Serializable
@Serializable
data class Customer(val Imie: String, val Nazwisko: String, val Email : String, val Nazwa: String, val Haslo : String)