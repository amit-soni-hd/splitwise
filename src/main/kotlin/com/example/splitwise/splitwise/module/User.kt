package code.module

class User() {

    var email: String? = null
    var fName: String? = null
    var lName: String? = null
    var contact: String? = null
    var userGroup: MutableList<Group>? = null
    var debtorsBill: MutableList<Bill>? = null
    var creditorsBill: MutableList<Bill>? = null

    init {
        userGroup = mutableListOf()
        debtorsBill = mutableListOf()
        creditorsBill = mutableListOf()
    }

    constructor(fName: String, lName: String, email: String, contact: String) : this() {
        this.fName = fName
        this.lName = lName
        this.contact = contact
        this.email = email
    }
}