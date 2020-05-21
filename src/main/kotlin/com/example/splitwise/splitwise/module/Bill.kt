package code.module

import code.enums.BillStatus
import lombok.Data
import java.util.*

@Data
class Bill() {

    var billId: Long? = null
    var description: String? = null
    var amount: Int? = null
    var date: Date? = null
    var status: BillStatus? = null
    var generatedUser: User? = null

    constructor(description: String?, amount: Int?, date: Date?, status: BillStatus?, generatedUser: User?) : this() {
        this.description = description
        this.amount = amount
        this.date = date
        this.status = status
        this.generatedUser = generatedUser
    }



}