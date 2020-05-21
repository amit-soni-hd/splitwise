package com.example.splitwise.splitwise.dto

import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.module.User
import java.util.*

class BillDto() {

    var billId: Long? = null
    var createdBy: String? = null
    var description: String? = null
    var amount: Int? = null
    var date: Date? = null
    lateinit var involvedUser: MutableMap<String, BillStatus>

    constructor(billId: Long, createdBy: String?,  description: String?, amount: Int?, date: Date?, involvedUser: MutableMap<String, BillStatus>) : this() {
        this.billId = billId
        this.createdBy = createdBy
        this.description = description
        this.amount = amount
        this.date = date
        this.involvedUser = involvedUser
    }
}