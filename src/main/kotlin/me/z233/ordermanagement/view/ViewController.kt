package me.z233.ordermanagement.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {
  @GetMapping(value = ["/", "/index"])
  fun index(): String {
    return "index"
  }
}