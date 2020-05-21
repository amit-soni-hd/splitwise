package code.module

import code.enums.BillStatus
import lombok.Data
import java.util.*

@Data
class Bill {

    var billId: Long? = null
    var description: String? = null
    var amount: Int? = null
    var date: Date? = null
    var status: BillStatus? = null
    var involvedUser: MutableMap<User,BillStatus>?=null

    constructor(description: String?, amount: Int?, date: Date?, status: BillStatus?, involvedUser: MutableMap<User,BillStatus>?) {
        this.description = description
        this.amount = amount
        this.date = date
        this.status = status
        this.involvedUser = involvedUser
    }
}