package com.example.splitwise.splitwise.module

import com.example.splitwise.splitwise.enum.BillStatus
import lombok.Data
import java.util.*

@Data
class Bill() {

    var billId: Long? = null
    var description: String? = null
    var amount: Int? = null
    var date: Date? = null
    var involvedUser: MutableMap<User, BillStatus>? = null

    constructor(billId: Long, description: String?, amount: Int?, date: Date?, involvedUser: MutableMap<User, BillStatus>?) : this() {
        this.description = description
        this.amount = amount
        this.date = date
        this.involvedUser = involvedUser
        this.billId = billId
    }
}