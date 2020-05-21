package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.UserDto
import com.example.splitwise.splitwise.module.Response
import com.example.splitwise.splitwise.module.User
import com.example.splitwise.splitwise.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @PostMapping
    fun createUser(@RequestBody userDto: UserDto): ResponseEntity<Response> {
        val response = userService.create(userDto)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @PutMapping("/{userEmail}")
    fun updateUSerDetails(@RequestBody userDto: UserDto, @PathVariable(value = "userEmail") userEmail: String): ResponseEntity<Response> {
        val response = userService.updateDetails(userEmail, userDto)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @GetMapping("/{userEmail}")
    fun getUser(@PathVariable(value = "userEmail") userEmail: String): ResponseEntity<User> {
        val user = userService.getUser(userEmail)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user)

    }


}