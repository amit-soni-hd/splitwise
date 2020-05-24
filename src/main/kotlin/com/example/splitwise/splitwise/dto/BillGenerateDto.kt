package com.example.splitwise.splitwise.dto

import com.example.splitwise.splitwise.module.Group
import com.example.splitwise.splitwise.module.User
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

class BillGenerateDto(

        @JsonProperty("owner_id")
        val ownerId:Long,

        @JsonProperty("bill_name")
        var billName: String,

        @JsonProperty("bill_description")
        var description: String,

        @JsonProperty("bill_amount")
        var amount: Double,

        @JsonProperty("date_time")
        var date: LocalDateTime,

        @JsonProperty("involved_user")
        val involvedUser: MutableList<User> = mutableListOf(),

        @JsonProperty("group")
        var group: Group? = null
)