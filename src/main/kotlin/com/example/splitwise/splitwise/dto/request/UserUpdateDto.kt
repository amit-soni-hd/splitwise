package com.example.splitwise.splitwise.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UserUpdateDto(

        @JsonProperty("user_id", required = false)
        var userId: Long? = 0,

        @JsonProperty("email")
        var emailId: String?,

        @JsonProperty("name")
        var name: String?,

        @JsonProperty("contact")
        var contact: String?

)