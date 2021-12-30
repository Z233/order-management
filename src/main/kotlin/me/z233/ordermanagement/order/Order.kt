package me.z233.ordermanagement.order

import me.z233.ordermanagement.good.GoodWithQuantity

class Order(var id: Int?, val userId: Int, val createdAt: Long, val remark: String, var goods: List<GoodWithQuantity>)
