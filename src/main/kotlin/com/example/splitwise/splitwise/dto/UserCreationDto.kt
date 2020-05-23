package com.example.splitwise.splitwise.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCreationDto(
        @JsonProperty("email")
        var emailId: String,

        @JsonProperty("name")
        var name: String,

        @JsonProperty("contact")
        var contact: String
)

