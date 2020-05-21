package com.example.splitwise.splitwise.dto

class UserDto() {


    lateinit var fName: String
    lateinit var lName: String
    lateinit var email: String
    lateinit var contact: String

    constructor(fName: String, lName: String, email: String, phone: String): this() {
        this.fName = fName
        this.lName = lName
        this.email = email
        this.contact = phone
    }

    constructor(fName: String, lName: String, phone: String): this() {
        this.fName = fName
        this.lName = lName
        this.contact = phone
    }



}