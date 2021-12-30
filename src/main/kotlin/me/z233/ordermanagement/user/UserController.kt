package me.z233.ordermanagement.user

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/user")
class UserController(val userRepository: UserRepository) {

  @GetMapping()
  fun getAllUser(): List<User> {
    return userRepository.findAll()
  }

  @PostMapping()
  fun createUser(
    @RequestBody newUser: User
  ): User {
    val newId = userRepository.insert(newUser)
    newUser.id = newId
    return newUser
  }

  @PutMapping()
  fun updateUser(@RequestBody user: User): ResponseEntity<Any> {
    if (user.id == null) {
      return ResponseEntity.badRequest().body("User id cannot be null")
    }
    val res = userRepository.update(user)
    if (res == 1) {
      return ResponseEntity.ok().build()
    } else {
      return ResponseEntity.internalServerError().build()
    }
  }

  @DeleteMapping()
  fun deleteUser(@RequestBody user: User): ResponseEntity<Any> {
    if (user.id == null) {
      return ResponseEntity.badRequest().body("User id cannot be null")
    }
    val res = userRepository.deleteById(user.id!!)
    if (res == 1) {
      return ResponseEntity.ok().build()
    } else {
      return ResponseEntity.internalServerError().build()
    }
  }

}