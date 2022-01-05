package me.z233.ordermanagement.order

import com.beust.klaxon.Klaxon
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import me.z233.ordermanagement.good.GoodRepository
import me.z233.ordermanagement.good.GoodWithQuantity
import me.z233.ordermanagement.user.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.lang.NonNull
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/order")
class OrderController(
  val userRepository: UserRepository,
  val orderRepository: OrderRepository,
  val goodRepository: GoodRepository,
  val orderGoodRepository: OrderGoodRepository
) {
  @GetMapping("/add")
  fun createOrderView(model: Model): String {
    val userList = userRepository.findAll()
    val goodList = goodRepository.findAll()
    model.addAttribute("userList", userList)
    model.addAttribute("goodList", goodList)
    return "order/add"
  }

  @PostMapping("/add")
  @Transactional
  fun createOrder(@NonNull orderParams: OrderParams): String {
    val newOrder = Order(
      id = null,
      userId = orderParams.userId,
      createdAt = orderParams.createdAt,
      remark = orderParams.remark,
      goods = Klaxon().parseArray<GoodWithQuantity>(orderParams.goods)!!
    )
    orderRepository.insert(newOrder)
    newOrder.goods.forEach {
      val newOrderGood = OrderGood(newOrder.id!!, it.id!!, it.quantity)
      orderGoodRepository.insert(newOrderGood)
    }
    return "order/success"
  }

  @PostMapping("/delete/{id}")
  @Transactional
  fun deleteOrder(@PathVariable @NonNull id: Int): String {
    val order = orderRepository.findById(id)
    val orderGoods = orderGoodRepository.findByOrderId(id)
    // 删除订单和商品的对应关系
    orderGoods?.forEach {
      orderGoodRepository.delete(it.orderId, it.goodId)
    }
    // 删除订单
    orderRepository.deleteById(id)
    return "/order/success"
  }

  @GetMapping("/edit/{id}")
  fun editOrder(model: Model, @PathVariable @NonNull id: Int): String {
    val userList = userRepository.findAll()
    val goodList = goodRepository.findAll()
    val order = orderRepository.findById(id)
    if (order != null) {
      order.goods = orderGoodRepository.findByOrderId(id)?.map {
        val good = goodRepository.findById(it.goodId)
        GoodWithQuantity(good.id!!, good.name, good.price, good.description, it.quantity)
      } ?: emptyList()
    }
    model.addAttribute("userList", userList)
    model.addAttribute("goodList", goodList)
    model.addAttribute("order", order)
    return "order/edit"
  }

  @PostMapping("/edit/{id}")
  @Transactional
  fun updateOrder(@NonNull orderParams: OrderParams): String {
    val order = orderParams.run {
      Order(id, userId, createdAt, remark, Klaxon().parseArray<GoodWithQuantity>(goods)!!)
    }
    if (order.id == null) {
      return "order/fail"
    }
    // 获取所有的订单商品，检查是否还存在，不存在则删除
    val orderGoods = orderGoodRepository.findByOrderId(order.id!!)
    // 更新订单和商品的对应关系
    order.goods.forEach {
      if (orderGoods != null && orderGoods.any { og -> og.goodId == it.id }) {
        orderGoodRepository.update(OrderGood(order.id!!, it.id!!, it.quantity))
      } else {
        val newOrderGood = OrderGood(order.id!!, it.id!!, it.quantity)
        orderGoodRepository.insert(newOrderGood)
      }
    }
    // 删除多余
    orderGoods?.forEach {
      if (!order.goods.any { og -> og.id == it.goodId }) {
        orderGoodRepository.delete(it.orderId, it.goodId)
      }
    }
    // 更新订单
    orderRepository.update(order)
    return "order/success"
  }

  @GetMapping()
  fun getAllOrders(model: Model): String {
    val orderList = orderRepository.findAll()
    orderList.forEach { order ->
      order.goods = orderGoodRepository.findByOrderId(order.id!!)?.map {
        val good = goodRepository.findById(it.goodId)
        GoodWithQuantity(good.id!!, good.name, good.price, good.description, it.quantity)
      } ?: emptyList()
    }
    model.addAttribute("orderList", orderList)
    return "order/index"
  }

}