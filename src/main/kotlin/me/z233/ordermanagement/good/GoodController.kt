package me.z233.ordermanagement.good

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/good")
class GoodController(val goodRepository: GoodRepository) {
  @PostMapping()
  fun createGood(@RequestBody newGood: Good): ResponseEntity<Any> {
    val newId = goodRepository.insert(newGood)
    newGood.id = newId
    return ResponseEntity.ok(newGood)
  }

  @DeleteMapping()
  fun deleteGood(@RequestBody good: Good): ResponseEntity<Any> {
    if (good.id == null) {
      return ResponseEntity.badRequest().body("Order id is null")
    }
    goodRepository.deleteById(good.id!!)
    return ResponseEntity.ok().build()
  }

  @PutMapping()
  fun updateGood(@RequestBody good: Good): ResponseEntity<Any> {
    if (good.id == null) {
      return ResponseEntity.badRequest().body("Order id is null")
    }
    goodRepository.update(good)
    return ResponseEntity.ok().build()
  }

  @GetMapping()
  fun getAllGoods(): ResponseEntity<Any> {
    return ResponseEntity.ok(goodRepository.findAll())
  }

}