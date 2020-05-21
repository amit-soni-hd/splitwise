package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.BillDto
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.Response
import com.example.splitwise.splitwise.service.BillService
import com.example.splitwise.splitwise.service.BillServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bill")
class BillController {

    @Autowired
    lateinit var billService: BillServiceImpl

    @PostMapping("/")
    fun generateBill(@RequestBody billDto: BillDto): ResponseEntity<Response> {
        val generateBill = billService.generateBill(billDto)
        var response = Response("Successfully generated", generateBill, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{billId}")
    fun getBill(@PathVariable(value = "billId") billId:Long): ResponseEntity<Response> {
        val bill = billService.getBill(billId)
        if (bill == null) {
            var response = Response("Bill not present (might be bill id is wrong)", BillDto::class, HttpStatus.NOT_FOUND)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
        }
        var response = Response("Successfully Found", bill, HttpStatus.FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
}