package code.module

import lombok.Data

@Data
class User() {

    var email: String? = null
    var fName: String? = null
    var lName: String? = null
    var phone: String? = null

    constructor(fName: String, lName: String, email: String, phone: String) : this() {
        this.fName = fName
        this.lName = lName
        this.phone = phone
        this.email = email
    }
}