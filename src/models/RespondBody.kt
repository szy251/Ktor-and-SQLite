package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class RespondBody(val boolean: Boolean, val message:String)
