package com.example.splitwise.splitwise.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCreationDto(
        @JsonProperty("Email")
        var emailId: String,

        @JsonProperty("Name")
        var name: String,

        @JsonProperty("contact")
        var contact: String
)

