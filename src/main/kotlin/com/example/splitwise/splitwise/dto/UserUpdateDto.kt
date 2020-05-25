package com.example.splitwise.splitwise.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserUpdateDto(

        @JsonProperty("user_id", required = false)
        var userId: Long? = 0,

        @JsonProperty("Email")
        var emailId: String?,

        @JsonProperty("Name")
        var name: String?,

        @JsonProperty("contact")
        var contact: String?

)