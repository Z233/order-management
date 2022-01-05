package me.z233.ordermanagement.order

import kotlinx.serialization.Serializable
import me.z233.ordermanagement.good.GoodWithQuantity

class Order(var id: Int?, val userId: Int, val createdAt: Long, val remark: String, var goods: List<GoodWithQuantity>) {
  constructor(id: Int?, userId: Int, createdAt: Long, remark: String, vararg goods: GoodWithQuantity) : this(id, userId, createdAt, remark, goods.toList())
  constructor(): this(null, 0, 0, "", emptyList())
}

class OrderParams(var id: Int?, val userId: Int, val createdAt: Long, val remark: String, var goods: String)

