package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.dto.BillDto
import org.modelmapper.ModelMapper

class BillServiceImpl() : BillService {


    var bills: MutableMap<Long, Bill>? = null
    var modelMapper: ModelMapper? = null
    var userService: UserServiceImpl? = null

    init {
        modelMapper = ModelMapper()
        bills = mutableMapOf()
        userService = UserServiceImpl()
    }

    override fun generateBill(userEmail:String, billDto: BillDto): Bill? {

        var bill = modelMapper?.map(billDto, Bill::class.java)
        userService?.addDebtorBill(userEmail,bill!!)
        splitBill(bill!!)
        bills?.put(bill?.billId!!, bill!!)
        return bill
    }


    override fun getBill(billId: Long): Bill? {
        return bills?.get(billId)
    }

    private fun splitBill(bill: Bill) {
        bill.involvedUser?.forEach { (userEmail, status) ->
            run {
                userService?.addCreditorBill(userEmail, bill)
            }
        }
    }


}