package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.BillGenerateDto
import com.example.splitwise.splitwise.dto.BillUpdateDto
import com.example.splitwise.splitwise.dto.ResponseDto
import com.example.splitwise.splitwise.service.BillService
import com.example.splitwise.splitwise.service.BillServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/bill")
class BillController(val billService: BillService) {


    @PostMapping("/")
    fun generateBill(@RequestBody billGenerateDto: BillGenerateDto): ResponseEntity<ResponseDto> {
        billGenerateDto.date = LocalDateTime.now()
        val generateBill = billService.generateBill(billGenerateDto)
        val response = ResponseDto("Successfully generated", generateBill, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{billId}")
    fun getBill(@PathVariable(value = "billId") billId: Long): ResponseEntity<ResponseDto> {
        val bill = billService.getBill(billId)
        val response = ResponseDto("Successfully fetched  bill ", bill, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)

    }

    @PutMapping("/")
    fun updateBill(billUpdateDto: BillUpdateDto): ResponseEntity<ResponseDto> {
        val bill = billService.updateBill(billUpdateDto = billUpdateDto)
        val response = ResponseDto("Successfully update  bill ", bill, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
    
    @DeleteMapping("/{billId}")
    fun deleteBill(@PathVariable("billId") billId: Long): ResponseEntity<ResponseDto> {
        val deleteBill = billService.deleteBill(billId = billId)
        val response = ResponseDto("Successfully update  bill ", deleteBill, HttpStatus.OK)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
}