package me.z233.ordermanagement.good

import kotlinx.serialization.Serializable

data class Good(var id: Int?, val name: String, val price: Double, val description: String)
data class GoodWithQuantity(val id: Int, val name: String, val price: Double, val description: String, val quantity: Int)