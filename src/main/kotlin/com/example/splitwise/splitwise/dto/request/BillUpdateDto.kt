package com.example.splitwise.splitwise.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class BillUpdateDto(

        @JsonProperty("bill_id")
        var billId:Long,

        @JsonProperty("bill_name")
        var billName: String?,

        @JsonProperty("bill_description")
        var description: String?,

        @JsonProperty("bill_amount")
        var amount: Double?,

        @JsonProperty("date_time")
        var date: LocalDateTime?,

        @JsonProperty("group_id")
        var groupId :Long?
)