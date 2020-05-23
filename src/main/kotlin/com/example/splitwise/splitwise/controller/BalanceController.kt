package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.ResponseDto
import com.example.splitwise.splitwise.service.BalanceService
import com.example.splitwise.splitwise.service.BalanceServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/balance")
class BalanceController(val balanceService: BalanceService) {

    @GetMapping("/total/{userId}")
    fun getTotalBalance(@PathVariable("userId") userId: Long): ResponseEntity<ResponseDto> {
        val totalBalance = balanceService.getTotalBalance(userId = userId)
        var response = ResponseDto("Total balance", totalBalance, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @GetMapping("/individual/{userId}/{respectId}")
    fun getIndividualBalance(@RequestParam("userId") userId: Long, @RequestParam("respectId") respectId: Long): ResponseEntity<ResponseDto> {

        val individualBalance = balanceService.getIndividualBalance(userId = userId, respectUserId = respectId)
        var response = ResponseDto("Individual Balance", individualBalance, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

}