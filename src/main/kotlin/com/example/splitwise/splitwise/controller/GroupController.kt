package com.example.splitwise.splitwise.controller

import com.example.splitwise.splitwise.dto.request.UserGroupDto
import com.example.splitwise.splitwise.dto.response.ResponseDto
import com.example.splitwise.splitwise.service.UserGroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/group")
class GroupController(private val userGroupService: UserGroupService) {

    @PostMapping("/create")
    fun createGroup(@RequestBody userGroupDto: UserGroupDto): ResponseEntity<ResponseDto> {
        val group = userGroupService.run { createGroup(userGroupDto = userGroupDto) }
        val response = ResponseDto("Total balance", group, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/add/bill/{bill_id}/group/{group_id}")
    fun addBill(@PathVariable(value = "bill_id") billId: Long, @PathVariable(value = "group_id") groupId: Long): ResponseEntity<ResponseDto> {
        val bill = userGroupService.run { addGroupBill(groupId = groupId, billId = billId) }
        val response = ResponseDto("Total balance", bill, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}