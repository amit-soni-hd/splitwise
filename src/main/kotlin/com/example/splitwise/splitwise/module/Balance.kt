package code.module

import lombok.Data
import javax.persistence.Entity

@Data
class Balance() {

    var userEmail: String? = null
    var debtors: MutableMap<Long, MutableList<Bill>>? = null
    var creditors:MutableMap<Long, MutableList<Bill>>? = null

    constructor(userEmail: String?, debtors: MutableMap<Long, MutableList<Bill>>?, creditors: MutableMap<Long, MutableList<Bill>>?):this() {
        this.userEmail = userEmail
        this.debtors = debtors
        this.creditors = creditors
    }
}