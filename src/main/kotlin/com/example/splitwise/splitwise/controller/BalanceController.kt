package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.response.ResponseDto
import com.example.splitwise.splitwise.service.BalanceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/balance")
class BalanceController(val balanceService: BalanceService) {

    /**
     * api for getting the total balance of a user
     * @param userId
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/total/{userId}")
    fun getTotalBalance(@PathVariable("userId") userId: Long): ResponseEntity<ResponseDto> {
        val totalBalance = balanceService.getTotalBalance(userId = userId)
        val response = ResponseDto("Total balance", totalBalance, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * api for getting the individual balance of a user with respect to other user
     * @param userId user id
     * @param respectId other user
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/individual/{userId}/{respectId}")
    fun getIndividualBalance(@RequestParam("userId") userId: Long, @RequestParam("respectId") respectId: Long): ResponseEntity<ResponseDto> {

        val individualBalance = balanceService.getIndividualBalance(userId = userId, respectUserId = respectId)
        val response = ResponseDto("Individual Balance", individualBalance, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

}