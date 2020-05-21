package com.example.splitwise.splitwise.module

import org.springframework.http.HttpStatus

class Response {

    var message:String
    var objects: Any
    var status:HttpStatus

    constructor(message: String, objects: Any, status: HttpStatus) {
        this.message = message
        this.objects = objects
        this.status = status
    }

}