package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.ResponseDto
import com.example.splitwise.splitwise.dto.UserCreationDto
import com.example.splitwise.splitwise.dto.UserUpdateDto
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
    fun createUser(@RequestBody userCreationDto: UserCreationDto): ResponseEntity<ResponseDto> {
        val createUser = userService.create(userCreationDto)
        var response = ResponseDto("Successfully updated", createUser, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @PutMapping("/{userId}")
    fun updateUSerDetails(@RequestBody userUpdateDto: UserUpdateDto, @PathVariable(value = "userId") userId: Long): ResponseEntity<ResponseDto> {
        val updatedUser = userService.updateDetails(userId, userUpdateDto)
        var response = ResponseDto("Successfully updated", updatedUser, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable(value = "userId") userId: Long): ResponseEntity<ResponseDto> {
        val user = userService.getUser(userId)
        var response = ResponseDto("Successfully updated", user, HttpStatus.FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }


}