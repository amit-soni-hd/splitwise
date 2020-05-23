package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.module.Bill
import org.springframework.stereotype.Service

@Service
class BalanceServiceImpl(val userService: UserService) : BalanceService {



    override fun getTotalBalance(userId: Long): Map<String, Long> {




        return null!!
    }

    override fun getIndividualBalance(userEmail: String, respectUserEmail: String): Map<String, Long> {

        return  null!!
    }

}