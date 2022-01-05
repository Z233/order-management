package me.z233.ordermanagement.user

import org.springframework.http.ResponseEntity
import org.springframework.lang.NonNull
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/user")
class UserController(val userRepository: UserRepository) {

  @GetMapping()
  fun getAllUser(model: Model): String {
    val userList = userRepository.findAll()
    model.addAttribute("userList", userList)
    return "/user/index"
  }

  @GetMapping("/search")
  fun searchByName(@RequestParam  nameKey: String?, @RequestParam phoneKey: String?, @RequestParam sex: String?, model: Model): String {
    val name = nameKey ?: ""
    val phone = phoneKey ?: ""
    val sexKey = sex ?: ""
    val userList = userRepository.search(name, phone, sexKey)
    model.addAttribute("userList", userList)
    model.addAttribute("isSearch", true)
    return "/user/index"
  }

  @GetMapping("/add")
  fun createUserView() = "/user/add"

  @PostMapping("/add")
  fun createUser(@NonNull newUser: User): String {
    val newId = userRepository.insert(newUser)
    newUser.id = newId
    return "user/success"
  }

  @GetMapping("/edit/{id}")
  fun editUser(model: Model, @PathVariable @NonNull id: Int): String {
    val user = userRepository.findById(id)
    user.let {
      model.addAttribute("user", user)
      return "/user/edit"
    }
  }

  @PostMapping("/edit/{id}")
  fun updateUser(@NonNull user: User, @PathVariable @NonNull id: Int): String {
    user.id = id
    val res = userRepository.update(user)
    if (res == 1) {
      return "/user/success"
    }
    return "/user/fail"
  }

  @PostMapping("/delete/{id}")
  fun deleteUser(@PathVariable @NonNull id: Int): String {
    val res = userRepository.deleteById(id)
    if (res == 1) {
      return "/user/success"
    } else {
      return "/user/fail"
    }
  }

}