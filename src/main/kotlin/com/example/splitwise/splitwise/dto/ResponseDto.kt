package com.example.splitwise.splitwise.dto

import org.springframework.http.HttpStatus

data class ResponseDto(var message: String?, var objects: Any?, var httpStatus: HttpStatus?)