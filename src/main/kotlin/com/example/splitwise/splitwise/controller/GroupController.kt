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

    /**
     * post api for create the new group
     * @param userGroupDto details for creating the new user
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @PostMapping("/create")
    fun createGroup(@RequestBody userGroupDto: UserGroupDto): ResponseEntity<ResponseDto> {
        val group = userGroupService.run { createGroup(userGroupDto = userGroupDto) }
        val response = ResponseDto("Total balance", group, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    /**
     * post api for add the bill with a group
     * @param billId
     * @param groupId
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @PostMapping("/add/bill/{bill_id}/group/{group_id}")
    fun addBill(@PathVariable(value = "bill_id") billId: Long, @PathVariable(value = "group_id") groupId: Long): ResponseEntity<ResponseDto> {
        val bill = userGroupService.run { addGroupBill(groupId = groupId, billId = billId) }
        val response = ResponseDto("Total balance", bill, HttpStatus.CREATED)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    /**
     * get api for get debts of a user
     * @param groupId
     * @return ResponseEntity<ResponseDto> which contain the details of status
     */
    @GetMapping("/balance/{group_id}")
    fun getDebts(@PathVariable("group_id") groupId: Long): ResponseEntity<ResponseDto> {
        val debts = userGroupService.getDebts(groupId = groupId)
        val response = ResponseDto("Total balance of group", debts, HttpStatus.OK)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }
}