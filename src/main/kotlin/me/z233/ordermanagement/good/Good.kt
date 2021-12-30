package me.z233.ordermanagement.good

open class Good(var id: Int?, val name: String, val price: Double, val description: String)
class GoodWithQuantity(id: Int, name: String, price: Double, description: String, val quantity: Int) :
  Good(id, name, price, description)