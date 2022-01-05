package me.z233.ordermanagement.good

import me.z233.ordermanagement.order.OrderGoodRepository
import me.z233.ordermanagement.user.User
import org.springframework.http.ResponseEntity
import org.springframework.lang.NonNull
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/good")
class GoodController(
  val goodRepository: GoodRepository,
  val orderGoodRepository: OrderGoodRepository
  ) {
  @GetMapping("/add") fun createGoodView() = "good/add"

  @PostMapping("/add")
  fun createGood(@NonNull newGood: Good): String {
    goodRepository.insert(newGood)
    return "good/success"
  }

  @PostMapping("/delete/{id}")
  fun deleteGood(@PathVariable @NonNull id: Int, model: Model): String {
    val orderGoodList = orderGoodRepository.findByGoodId(id)
    if (orderGoodList == null || orderGoodList.isEmpty()) {
      val res = goodRepository.deleteById(id)
      if (res == 1) {
        return "/good/success"
      } else {
        return "/good/fail"
      }
    } else {
      model.addAttribute("message", "该商品已被订单引用，无法删除")
      return "/good/fail"
    }
  }

  @GetMapping("/edit/{id}")
  fun editGood(model: Model, @PathVariable @NonNull id: Int): String {
    val good = goodRepository.findById(id)
    good.let {
      model.addAttribute("good", good)
      return "/good/edit"
    }
  }

  @PostMapping("/edit/{id}")
  fun updateGood(@NonNull good: Good, @PathVariable @NonNull id: Int): String {
    good.id = id
    val res = goodRepository.update(good)
    if (res == 1) {
      return "/good/success"
    }
    return "/good/fail"
  }

  @GetMapping()
  fun getAllGoods(model: Model): String {
    model.addAttribute("goodList", goodRepository.findAll())
    return "/good/index"
  }

}