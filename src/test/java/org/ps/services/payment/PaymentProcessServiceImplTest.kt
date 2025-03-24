package org.ps.services.payment

import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.ps.enums.TransactionStatus
import org.ps.services.acquirer.Acquirer
import org.ps.services.notification.NotificationService

class PaymentProcessServiceImplTest {

    @Test
    fun `should process payment successfully and send notification`() {
        // Arrange
        val notificationService = mock(NotificationService::class.java)
        val acquirer = mock(Acquirer::class.java)
        val service = PaymentProcessServiceImpl(notificationService)
        val transactionId = "12345"
        val transactionStatus = TransactionStatus.COMPLETED
        val transactionMessage = "Transaction completed successfully"

        `when`(acquirer.processTransaction(transactionId))
            .thenReturn(Pair(transactionStatus, transactionMessage))

        // Act
        service.processPayment(transactionId, acquirer)

        // Assert
        verify(acquirer, times(1)).processTransaction(transactionId)
        verify(notificationService, times(1)).notify(transactionId, transactionStatus, transactionMessage)
    }

    @Test
    fun `should process payment failure and send notification`() {
        // Arrange
        val notificationService = mock(NotificationService::class.java)
        val acquirer = mock(Acquirer::class.java)
        val service = PaymentProcessServiceImpl(notificationService)
        val transactionId = "12345"
        val transactionStatus = TransactionStatus.FAILED
        val transactionMessage = "Transaction failed due to insufficient balance"

        `when`(acquirer.processTransaction(transactionId))
            .thenReturn(Pair(transactionStatus, transactionMessage))

        // Act
        service.processPayment(transactionId, acquirer)

        // Assert
        verify(acquirer, times(1)).processTransaction(transactionId)
        verify(notificationService, times(1)).notify(transactionId, transactionStatus, transactionMessage)
    }

    @Test
    fun `should send notification with null message if acquirer does not provide a message`() {
        // Arrange
        val notificationService = mock(NotificationService::class.java)
        val acquirer = mock(Acquirer::class.java)
        val service = PaymentProcessServiceImpl(notificationService)
        val transactionId = "12345"
        val transactionStatus = TransactionStatus.COMPLETED

        `when`(acquirer.processTransaction(transactionId))
            .thenReturn(Pair(transactionStatus, null))

        // Act
        service.processPayment(transactionId, acquirer)

        // Assert
        verify(acquirer, times(1)).processTransaction(transactionId)
        verify(notificationService, times(1)).notify(transactionId, transactionStatus, null)
    }
}