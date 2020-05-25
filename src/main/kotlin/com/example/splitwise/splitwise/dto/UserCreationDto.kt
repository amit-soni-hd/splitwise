package com.example.splitwise.splitwise.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCreationDto(

        @JsonProperty("user_id")
        var userId: Long?,

        @JsonProperty("user_email",required = true)
        var emailId: String,

        @JsonProperty("user_name",required = true)
        var name: String,

        @JsonProperty("user_contact",required = true)
        var contact: String
)

