package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.request.BillGenerateDto
import com.example.splitwise.splitwise.dto.request.BillUpdateDto
import com.example.splitwise.splitwise.dto.request.IncludeUserOnBillDto
import com.example.splitwise.splitwise.dto.response.ResponseDto
import com.example.splitwise.splitwise.service.BillService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/bill")
class BillController(val billService: BillService) {


    /**
     * post api for generate the bill
     * @param billGenerateDto is the object which contain the details of bill
     * @return ResponseEntity with response if bill was successfully created
     */
    @PostMapping("/")
    fun generateBill(@RequestBody billGenerateDto: BillGenerateDto): ResponseEntity<ResponseDto> {
        billGenerateDto.date = LocalDateTime.now()
        val generateBill = billService.generateBill(billGenerateDto)
        val response = ResponseDto("Successfully generated", generateBill, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    /**
     * get api for get the bill
     * @param billId bill id for getting the bill
     * @return ResponseEntity with response if bill was successfully fetched
     */
    @GetMapping("/{billId}")
    fun getBill(@PathVariable(value = "billId") billId: Long): ResponseEntity<ResponseDto> {
        val bill = billService.getBill(billId)
        val response = ResponseDto("Successfully fetched  bill ", bill, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)

    }

    /**
     * put api for update  the bill
     * @param billUpdateDto is the object which contain the details of bill updating
     * @return ResponseEntity with response if bill was successfully update
     */
    @PutMapping("/")
    fun updateBill(billUpdateDto: BillUpdateDto): ResponseEntity<ResponseDto> {
        val bill = billService.updateBill(billUpdateDto = billUpdateDto)
        val response = ResponseDto("Successfully update  bill ", bill, HttpStatus.NOT_FOUND)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * delete api for delete  the bill
     * @param billId id of bill for delete the bill
     * @return ResponseEntity with response if bill was successfully deleted
     */
    @DeleteMapping("/{billId}")
    fun deleteBill(@PathVariable("billId") billId: Long): ResponseEntity<ResponseDto> {
        val deleteBill = billService.deleteBill(billId = billId)
        val response = ResponseDto("Successfully update  bill ", deleteBill, HttpStatus.OK)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }

    /**
     * put api for add the users with old bill
     * @param includeUserOnBillDto details for adding the user
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @PutMapping("/includeUser")
    fun addUsersOnBill(@RequestBody includeUserOnBillDto: IncludeUserOnBillDto): ResponseEntity<ResponseDto> {
        val bill = billService.includeNewUsers(includeUserOnBillDto = includeUserOnBillDto)
        val response = ResponseDto("Successfully add on  bill ", bill, HttpStatus.ACCEPTED)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
    }
}