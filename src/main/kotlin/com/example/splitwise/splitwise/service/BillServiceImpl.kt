package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.dto.BillDto
import org.modelmapper.ModelMapper

class BillServiceImpl : BillService {


    var bills: MutableMap<Long, Bill>? = null
    var modelMapper: ModelMapper? = null

    init {
        modelMapper = ModelMapper()
        bills = mutableMapOf()
    }

    override fun generateBill(billDto: BillDto): Bill? {

        var bill = modelMapper?.map(billDto, Bill::class.java)
        bills?.put(bill?.billId!!, bill!!)
        return bill
    }


    override fun getBill(billId:Long): Bill? {
        return bills?.get(billId)
    }


}