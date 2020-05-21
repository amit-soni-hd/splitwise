package com.example.splitwise.splitwise.dto

class UserDto() {


    var fName: String? = null
    var lName: String? = null
    var email: String? = null
    var phone: String? = null

    constructor(fName: String?, lName: String?, email: String?, phone: String?): this() {
        this.fName = fName
        this.lName = lName
        this.email = email
        this.phone = phone
    }

    constructor(fName: String?, lName: String?, phone: String?): this() {
        this.fName = fName
        this.lName = lName
        this.email = email
        this.phone = phone
    }



}