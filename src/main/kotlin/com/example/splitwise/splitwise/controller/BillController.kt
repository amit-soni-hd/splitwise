package com.example.splitwise.splitwise.controller

//import com.example.splitwise.splitwise.dto.BillDto
//import com.example.splitwise.splitwise.dto.ResponseDto
//import com.example.splitwise.splitwise.service.BillServiceImpl
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@RequestMapping("/bill")
//class BillController {

//    @Autowired
//    lateinit var billService: BillServiceImpl
//
//    @PostMapping("/")
//    fun generateBill(@RequestBody billDto: BillDto): ResponseEntity<ResponseDto> {
//        val generateBill = billService.generateBill(billDto)
//        var response = ResponseDto("Successfully generated", generateBill, HttpStatus.CREATED)
//        return ResponseEntity.status(HttpStatus.CREATED).body(response)
//    }
//
//    @GetMapping("/{billId}")
//    fun getBill(@PathVariable(value = "billId") billId: Long): ResponseEntity<ResponseDto> {
//        val bill = billService.getBill(billId)
//        if (bill == null) {
//            var response = ResponseDto("Bill not present (might be bill id is wrong)", BillDto::class, HttpStatus.NOT_FOUND)
//            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
//        }
//        var response = ResponseDto("Successfully Found", bill, HttpStatus.FOUND)
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response)
//    }
//}