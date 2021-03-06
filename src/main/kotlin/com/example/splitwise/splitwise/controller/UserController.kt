package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.response.ResponseDto
import com.example.splitwise.splitwise.dto.request.UserCreationDto
import com.example.splitwise.splitwise.dto.request.UserUpdateDto
import com.example.splitwise.splitwise.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    /**
     * post api for create the user
     * @param userCreationDto details for creating the user
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @PostMapping("/")
    fun createUser(@RequestBody userCreationDto: UserCreationDto): ResponseEntity<ResponseDto> {
        val createUser = userService.create(userCreationDto)
        var response = ResponseDto("Successfully created user", createUser, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * put api for update the details of user
     * @param userUpdateDto details for updating the user
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @PutMapping("/{userId}")
    fun updateUserDetails(@RequestBody userUpdateDto: UserUpdateDto, @PathVariable(value = "userId") userId: Long): ResponseEntity<ResponseDto> {
        val updatedUser = userService.updateDetails(userId, userUpdateDto)
        var response = ResponseDto("Successfully updated", updatedUser, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * get api for getting the user by user id
     * @param userId
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/id/{userId}")
    fun getUserById(@PathVariable(value = "userId") userId: Long): ResponseEntity<ResponseDto> {
        val user = userService.getUserById(userId)
        var response = ResponseDto("Successfully fetched user", user.toString(), HttpStatus.FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * get api for getting the user by email id
     * @param emailId
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/email/{emailId}")
    fun getUserByEmail(@PathVariable(value = "emailId") emailId: String): ResponseEntity<ResponseDto> {
        val user = userService.getUserByEmail(emailId)
        var response = ResponseDto("Successfully fetched user", user.toString(), HttpStatus.FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * get api for getting the user by contact
     * @param contact
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/contact/{contact}")
    fun getUserByContact(@PathVariable(value = "contact") contact: String): ResponseEntity<ResponseDto> {
        val user = userService.getUserByContact(contact)
        var response = ResponseDto("Successfully fetched user", user.toString(), HttpStatus.FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

}