package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.dto.BillDto
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BillServiceImpl() : BillService {


    private var bills: MutableMap<Long, Bill>? = null

    @Autowired
    private lateinit var modelMapper: ModelMapper

    @Autowired
    private lateinit var userService: UserServiceImpl

    init {
        bills = mutableMapOf()
    }

    override fun generateBill(billDto: BillDto): Bill {

        var bill = modelMapper.map(billDto, Bill::class.java)
        userService.addDebtorBill(billDto.createdBy!!, bill)
        splitBill(bill)
        bills?.put(bill.billId!!, bill)
        return bill
    }


    override fun getBill(billId: Long): Bill? {
        return bills?.get(billId)
    }

    private fun splitBill(bill: Bill) {
        bill.involvedUser?.forEach { (userEmail, _) ->
            run {
                userService.addCreditorBill(userEmail, bill)
            }
        }
    }


}