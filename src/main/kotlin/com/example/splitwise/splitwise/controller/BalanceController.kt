package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.module.Response
import com.example.splitwise.splitwise.service.BalanceServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/balance")
class BalanceController {

    @Autowired
    private lateinit var balanceService: BalanceServiceImpl

    @GetMapping("/total/{emailId}")
    fun getTotalBalance(@PathVariable("emailId") emailId:String): ResponseEntity<Response> {
        val totalBalance = balanceService.getTotalBalance(emailId)
        var response = Response("Total balance", totalBalance, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    @GetMapping("/individual/{emailId}/{respectId}")
    fun getIndividualBalance(@RequestParam("emailId") emailId: String, @RequestParam("respectId") respectId: String): ResponseEntity<Response> {

        val individualBalance = balanceService.getIndividualBalance(emailId, respectId)
        var response = Response("Individual Balance", individualBalance, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }


}