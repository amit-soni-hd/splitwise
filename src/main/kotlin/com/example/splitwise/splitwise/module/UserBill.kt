package com.example.splitwise.splitwise.module

import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.enum.PaymentStatus
import javax.persistence.*

@Entity
@Table(name = "user_bill")
class UserBill(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Long = 0,

        @Column(name = "user_Id")
        var userId: Long,

        @Column(name = "bill_Id")
        var billId: Long,

        @Column(name = "owner_id")
        var ownerId:Long,

        @Column(name = "bill_status")
        @Enumerated(value = EnumType.STRING)
        var billStatus: BillStatus = BillStatus.PRESENT,

        @Column(name = "group_id", nullable = true)
        var groupId: Long? = null,

        @Column(name = "user_share", nullable = false)
        var userShare:Double,

        @Column(name = "amount_paid")
        var dueAmount:Double = 0.0,

        @Column(name = "payment_status")
        @Enumerated(value = EnumType.STRING)
        var paymentStatus: PaymentStatus = PaymentStatus.PENDING
)