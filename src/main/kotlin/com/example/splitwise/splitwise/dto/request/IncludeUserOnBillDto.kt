package com.example.splitwise.splitwise.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class IncludeUserOnBillDto(
        @JsonProperty("users_id")
        var usersId:List<Long>,
        @JsonProperty("bill_id")
        var billId:Long
)