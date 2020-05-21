package com.example.splitwise.splitwise.dto

import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.module.User
import java.util.*

class BillDto() {

    var billId: Long? = null
    var description: String? = null
    var amount: Int? = null
    var date: Date? = null
    var involvedUser: MutableMap<String, BillStatus>? = null

    constructor(billId: Long, description: String?, amount: Int?, date: Date?, involvedUser: MutableMap<String, BillStatus>?) : this() {
        this.billId = billId
        this.description = description
        this.amount = amount
        this.date = date
        this.involvedUser = involvedUser
    }
}