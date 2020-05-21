package com.example.splitwise.splitwise.dto

import code.enums.BillStatus
import code.module.User
import java.util.*

class BillDto() {

    var billId: Long? = null
    var description: String? = null
    var amount: Int? = null
    var date: Date? = null
    var involvedUser: MutableMap<User, BillStatus>? = null

    constructor(billId: Long, description: String?, amount: Int?, date: Date?, involvedUser: MutableMap<User, BillStatus>?) : this() {
        this.billId = billId
        this.description = description
        this.amount = amount
        this.date = date
        this.involvedUser = involvedUser
    }
}