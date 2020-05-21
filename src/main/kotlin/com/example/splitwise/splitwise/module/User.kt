package com.example.splitwise.splitwise.module

class User() {

    lateinit var email: String
    lateinit var fName: String
    lateinit var lName: String
    lateinit var contact: String
    var debtorsBill: MutableList<Bill> = mutableListOf()
    var creditorsBill: MutableList<Bill> = mutableListOf()
    var userGroup: MutableList<Group> = mutableListOf()

    constructor(fName: String, lName: String, email: String, contact: String) : this() {
        this.fName = fName
        this.lName = lName
        this.contact = contact
        this.email = email
    }
}