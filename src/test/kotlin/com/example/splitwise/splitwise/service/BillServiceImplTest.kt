package com.example.splitwise.splitwise.service
//
//import com.example.splitwise.splitwise.dto.request.BillGenerateDto
//import com.example.splitwise.splitwise.dto.request.BillUpdateDto
//import com.example.splitwise.splitwise.module.Bill
//import com.example.splitwise.splitwise.repository.BillRepository
//import com.example.splitwise.splitwise.repository.UserBillRepository
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertAll
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.MockitoAnnotations
//import org.modelmapper.ModelMapper
//import java.time.LocalDateTime
//import java.util.*
//
//@DisplayName("test bill service")
//internal class BillServiceImplTest {
//
//    @InjectMocks
//    private lateinit var billService: BillServiceImpl
//
//    @Mock
//    private lateinit var modelMapper: ModelMapper
//
//    @Mock
//    private lateinit var billRepository: BillRepository
//
//    @Mock
//    private lateinit var userService: UserServiceImpl
//
//    @Mock
//    private lateinit var userBillRepository: UserBillRepository
//
//    companion object {
//        var billUpdateDto = BillUpdateDto(billId = 1, billName = "party", description = "tour to goa", amount = 7000.0, date = LocalDateTime.now())
//        var billGenerateDto = BillGenerateDto(billId = 1, ownerId = 1, billName = "party", description = "tour to goa", amount = 5000.0, involvedUserIds = mutableListOf(1, 2))
//        private var bill1 = Bill(billId = 1, ownerId = 1, billName = "party", description = "tour of goa", amount = 5000.0, date = LocalDateTime.now())
//    }
//
//
//    @BeforeEach
//    fun setUp() = MockitoAnnotations.initMocks(this)
//
//    @Test
//    @DisplayName("generate the new bill")
//    fun generateBill() {
//        `when`(modelMapper.map(billGenerateDto, Bill::class.java)).thenReturn(bill1)
//        `when`(billRepository.save(any(Bill::class.java))).thenReturn(bill1)
//        val generateBill = billService.generateBill(billGenerateDto = billGenerateDto)
//        assertAll("test generated bill details",
//                { assertEquals(bill1.billName, generateBill.billName) },
//                { assertEquals(bill1.noOfUser, generateBill.noOfUser) }
//        )
//    }
//
//    @Test
//    @DisplayName("get the bill by id")
//    fun getBill() {
//        `when`(billRepository.existsById(anyLong())).thenReturn(true)
//        `when`(billRepository.findById(anyLong())).thenReturn(Optional.of(bill1))
//        val bill = billService.getBill(1)
//        assertAll("check bill details",
//                { assertEquals(bill1.amount, bill.amount) },
//                { assertEquals(bill1.billId, bill.billId) }
//        )
//    }
//
//    @Test
//    @DisplayName("test bill existence")
//    fun isBillExist() {
//        `when`(billRepository.existsById(anyLong())).thenReturn(true)
//        billService.isBillExist(1)
//        verify(billRepository, times(1)).existsById(anyLong())
//    }
//
//    @Test
//    @DisplayName("update the bill")
//    fun updateBill() {
//
//        `when`(billRepository.findById(anyLong())).thenReturn(Optional.of(bill1))
//        `when`(billRepository.save(any(Bill::class.java))).thenReturn(bill1)
//        val updateBill = billService.updateBill(billUpdateDto)
//        assertAll("test generated bill details",
//                { assertEquals(bill1.billName, updateBill.billName) },
//                { assertEquals(bill1.amount, updateBill.amount) }
//        )
//
//    }
//}