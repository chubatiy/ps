package org.ps.services.payment

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.ps.dto.responces.PaymentDetailResponse
import org.ps.dto.responces.PaymentStatusResponse
import org.ps.entities.CardEntity
import org.ps.exception.NotFoundException
import org.ps.entities.PaymentTransactionEntity
import org.ps.enums.Currency
import org.ps.enums.TransactionStatus
import org.ps.repositories.PaymentTransactionRepository
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

class PaymentReadServiceImplTest {

    private val paymentTransactionRepository = mock<PaymentTransactionRepository>()

    private val paymentReadServiceImpl = PaymentReadServiceImpl(paymentTransactionRepository)

    @Test
    fun `getById should return PaymentStatusResponse when transaction exists`() {
        // Arrange
        val paymentId = "123"
        val mockEntity = PaymentTransactionEntity().apply {
            id = paymentId
            status = TransactionStatus.COMPLETED
            message = "Payment successful"
        }
        `when`(paymentTransactionRepository.findById(paymentId)).thenReturn(Optional.of(mockEntity))

        // Act
        val result = paymentReadServiceImpl.getById(paymentId)

        // Assert
        assertEquals(PaymentStatusResponse.fromEntity(mockEntity), result)
        verify(paymentTransactionRepository, times(1)).findById(paymentId)
    }

    @Test
    fun `getById should throw NotFoundException when transaction does not exist`() {
        // Arrange
        val paymentId = "123"
        `when`(paymentTransactionRepository.findById(paymentId)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows(NotFoundException::class.java) {
            paymentReadServiceImpl.getById(paymentId)
        }
        verify(paymentTransactionRepository, times(1)).findById(paymentId)
    }

    @Test
    fun `getDetailById should return PaymentDetailResponse with masked details when maskDetails is true`() {
        // Arrange
        val paymentId = "123"
        val mockEntity = PaymentTransactionEntity().apply {
            id = paymentId
            status = TransactionStatus.COMPLETED
            amount = BigDecimal.ZERO
            card = CardEntity().apply {
                number = "11112323213213"
                currency = Currency.USD
            }
            message = "Payment successful"
            merchantId = "merchant001"
            created = ZonedDateTime.now()
            updated = ZonedDateTime.now()
        }
        val maskDetails = true
        `when`(paymentTransactionRepository.findById(paymentId)).thenReturn(Optional.of(mockEntity))

        // Act
        val result = paymentReadServiceImpl.getDetailById(paymentId, maskDetails)

        // Assert
        assertEquals(PaymentDetailResponse.fromEntity(mockEntity, maskDetails), result)
        verify(paymentTransactionRepository, times(1)).findById(paymentId)
    }

    @Test
    fun `getDetailById should return PaymentDetailResponse without masked details when maskDetails is false`() {
        // Arrange
        val paymentId = "123"
        val mockEntity = PaymentTransactionEntity().apply {
            id = paymentId
            status = TransactionStatus.COMPLETED
            amount = BigDecimal.ZERO
            card = CardEntity().apply {
                number = "11112323213213"
                currency = Currency.USD
            }
            message = "Payment successful"
            merchantId = "merchant001"
            created = ZonedDateTime.now()
            updated = ZonedDateTime.now()
        }
        val maskDetails = false
        `when`(paymentTransactionRepository.findById(paymentId)).thenReturn(Optional.of(mockEntity))

        // Act
        val result = paymentReadServiceImpl.getDetailById(paymentId, maskDetails)

        // Assert
        assertEquals(PaymentDetailResponse.fromEntity(mockEntity, maskDetails), result)
        verify(paymentTransactionRepository, times(1)).findById(paymentId)
    }

    @Test
    fun `getDetailById should throw NotFoundException when transaction does not exist`() {
        // Arrange
        val paymentId = "123"
        val maskDetails = true
        `when`(paymentTransactionRepository.findById(paymentId)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows(NotFoundException::class.java) {
            paymentReadServiceImpl.getDetailById(paymentId, maskDetails)
        }
        verify(paymentTransactionRepository, times(1)).findById(paymentId)
    }
}