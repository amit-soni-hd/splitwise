package com.example.splitwise.splitwise.service

import com.example.splitwise.splitwise.dto.request.BillGenerateDto
import com.example.splitwise.splitwise.dto.request.BillUpdateDto
import com.example.splitwise.splitwise.enum.BillStatus
import com.example.splitwise.splitwise.enum.PaymentStatus
import com.example.splitwise.splitwise.exception.BillNotFoundException
import com.example.splitwise.splitwise.module.Bill
import com.example.splitwise.splitwise.module.UserBill
import com.example.splitwise.splitwise.repository.BillRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.modelmapper.ModelMapper
import java.time.LocalDateTime
import java.util.*

@DisplayName("test bill service")
internal class BillServiceImplTest {

    @InjectMocks
    private lateinit var billService: BillServiceImpl

    @Mock
    private lateinit var modelMapper: ModelMapper

    @Mock
    private lateinit var billRepository: BillRepository

    @Mock
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userBillService: UserBillService


    companion object {
        var billUpdateDto = BillUpdateDto(billId = 1, billName = "party", description = "tour to goa", amount = 7000.0, date = LocalDateTime.now())
        var billGenerateDto = BillGenerateDto(billId = 1, ownerId = 1, billName = "party", description = "tour to goa", amount = 5000.0, involvedUserIds = mutableListOf(1, 2))
        private var bill1 = Bill(billId = 1, ownerId = 1, billName = "party", description = "tour of goa", amount = 5000.0, date = LocalDateTime.now())
        private var userBill = UserBill(id = 1, userId = 2, dueAmount = 1000.0, billId = 1, ownerId = 1, userShare = 1000.0, paymentStatus = PaymentStatus.PENDING)

    }


    @BeforeEach
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    @DisplayName("generate the new bill")
    fun generateBill() {
        `when`(modelMapper.map(billGenerateDto, Bill::class.java)).thenReturn(bill1)
        `when`(billRepository.save(any(Bill::class.java))).thenReturn(bill1)
        val generateBill = billService.generateBill(billGenerateDto = billGenerateDto)
        assertAll("test generated bill details",
                { assertEquals(bill1.billName, generateBill.billName) },
                { assertEquals(bill1.noOfUser, generateBill.noOfUser) }
        )
    }

    @Test
    @DisplayName("get the bill by id")
    fun getBill() {
        `when`(billRepository.findByIdAndBillStatus(anyLong())).thenReturn(Optional.of(bill1))

        `when`(billRepository.findById(anyLong())).thenReturn(Optional.of(bill1))
        val bill = billService.getBill(1)
        assertAll("check bill details",
                { assertEquals(bill1.amount, bill.amount) },
                { assertEquals(bill1.billId, bill.billId) }
        )
    }

    @Test
    @DisplayName("test bill existence")
    fun isBillExist() {
        `when`(billRepository.findByIdAndBillStatus(anyLong())).thenReturn(Optional.of(bill1))
        billService.isBillExist(1)
        verify(billRepository, times(1)).findByIdAndBillStatus(anyLong())
    }

    @Test
    @DisplayName("bill does not exist")
    fun `bill does not exist`() {
        `when`(billRepository.findByIdAndBillStatus(anyLong())).thenReturn(Optional.empty())

        val expectedException = assertThrows<BillNotFoundException>("Should throw an exception") {
            billService.isBillExist(1)
        }
        assertAll("check exception",
                { assertEquals("Bill does not exist with id 1", expectedException.message) }
        )
        verify(billRepository, times(1)).findByIdAndBillStatus(anyLong())
    }

    @Test
    @DisplayName("update the bill")
    fun updateBill() {

        `when`(billRepository.findByIdAndBillStatus(anyLong())).thenReturn(Optional.of(bill1))

        `when`(billRepository.findById(anyLong())).thenReturn(Optional.of(bill1))
        `when`(userBillService.getUserBillsByBillId(anyLong())).thenReturn(listOf(userBill))
        `when`(billRepository.save(any(Bill::class.java))).thenReturn(bill1)
        val updateBill = billService.updateBill(billUpdateDto)
        assertAll("test generated bill details",
                { assertEquals(bill1.billName, updateBill.billName) },
                { assertEquals(bill1.amount, updateBill.amount) }
        )

    }

    @Test
    @DisplayName("delete the bill")
    fun deleteBill() {
        bill1.billStatus = BillStatus.PRESENT
        `when`(billRepository.findByIdAndBillStatus(anyLong())).thenReturn(Optional.of(bill1))

        `when`(billRepository.findById(anyLong())).thenReturn(Optional.of(bill1))
        `when`(billRepository.save(any(Bill::class.java))).thenReturn(bill1)
        val deleteBill = billService.deleteBill(1)
        assertAll("test bill deletion",
                { assertEquals(bill1.billName, deleteBill.billName) },
                { assertEquals(bill1.amount, deleteBill.amount) },
                { assertEquals(bill1.billStatus, BillStatus.DELETED) }
        )
    }

    @Test
    @DisplayName("undo the bill")
    fun `undo the Bill`() {
        bill1.billStatus = BillStatus.DELETED
        `when`(billRepository.existsById(anyLong())).thenReturn(true)

        `when`(billRepository.findById(anyLong())).thenReturn(Optional.of(bill1))
        `when`(billRepository.save(any(Bill::class.java))).thenReturn(bill1)
        val deleteBill = billService.undoBill(1)
        assertAll("test bill deletion",
                { assertEquals(bill1.billName, deleteBill.billName) },
                { assertEquals(bill1.amount, deleteBill.amount) },
                { assertEquals(bill1.billStatus, BillStatus.PRESENT) }
        )
    }
}