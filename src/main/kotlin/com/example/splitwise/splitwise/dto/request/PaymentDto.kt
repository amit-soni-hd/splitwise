package com.example.splitwise.splitwise.dto.request

import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.enum.PaymentType
import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentDto(

        @JsonProperty("bill_id", required = true)
        var billId: Long,

        @JsonProperty("payer_id", required = true)
        var payerId: Long,

        @JsonProperty("receiver_id", required = true)
        var receiverId: Long,

        @JsonProperty("amount", required = true)
        var amount: Double,

        @JsonProperty("payment_type", required = true)
        var paymentType: PaymentType,

        @JsonProperty("payment_status")
        var paymentStatus: PaymentStatus = PaymentStatus.PENDING
) 