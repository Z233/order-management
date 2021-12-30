package me.z233.ordermanagement.order

import me.z233.ordermanagement.good.GoodRepository
import me.z233.ordermanagement.good.GoodWithQuantity
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
  val orderRepository: OrderRepository,
  val goodRepository: GoodRepository,
  val orderGoodRepository: OrderGoodRepository
) {

  @PostMapping()
  @Transactional
  fun createOrder(@RequestBody newOrder: Order): ResponseEntity<Any> {
    val newId = orderRepository.insert(newOrder)
    newOrder.id = newId
    newOrder.goods.forEach {
      val newOrderGood = OrderGood(newOrder.id!!, it.id!!, it.quantity)
      orderGoodRepository.insert(newOrderGood)
    }
    return ResponseEntity.ok(newOrder)
  }

  @DeleteMapping()
  @Transactional
  fun deleteOrder(@RequestBody order: Order): ResponseEntity<Any> {
    if (order.id == null) {
      return ResponseEntity.badRequest().body("Order id is null")
    }
    // 删除订单和商品的对应关系
    order.goods.forEach {
      orderGoodRepository.delete(order.id!!, it.id!!)
    }
    // 删除订单
    orderRepository.deleteById(order.id!!)
    return ResponseEntity.ok().build()
  }

  @PutMapping()
  @Transactional
  fun updateOrder(@RequestBody order: Order): ResponseEntity<Any> {
    if (order.id == null) {
      return ResponseEntity.badRequest().body("Order id is null")
    }
    // 更新订单和商品的对应关系
    order.goods.forEach {
      orderGoodRepository.update(OrderGood(order.id!!, it.id!!, it.quantity))
    }
    // 更新订单
    orderRepository.update(order)
    return ResponseEntity.ok().build()
  }

  @GetMapping()
  fun getAllOrders(): ResponseEntity<Any> {
    val orders = orderRepository.findAll()
    orders.forEach { order ->
      order.goods = orderGoodRepository.findByOrderId(order.id!!).map {
        val good = goodRepository.findById(it.goodId)
        GoodWithQuantity(good.id!!, good.name, good.price, good.description, it.quantity)
      }
    }
    return ResponseEntity.ok(orders)
  }

}