package org.ps.services.payment

import jakarta.transaction.Transactional
import org.ps.dto.requests.PaymentCreateRequest
import org.ps.dto.responces.PaymentStatusResponse
import org.ps.entities.CardEntity
import org.ps.entities.PaymentTransactionEntity
import org.ps.enums.TransactionStatus
import org.ps.repositories.CardRepository
import org.ps.repositories.PaymentTransactionRepository
import org.ps.services.notification.NotificationService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager

@Service
class PaymentWriteServiceImpl(
    private val paymentTransactionRepository: PaymentTransactionRepository,
    private val cardRepository: CardRepository,
    private val notificationService: NotificationService
) : PaymentWriteService {

    @Transactional
    override fun createPayment(request: PaymentCreateRequest): PaymentStatusResponse {
        val cardEntity = cardRepository.findByIdOrNull(request.cardNumber!!) ?: CardEntity().apply {
            this.number = request.cardNumber
            this.currency = request.currency
            this.expiryDate = request.expiryDate
            this.cvv = request.cvv
        }
        //saving transaction
        val entity = PaymentTransactionEntity().apply {
            this.card = cardEntity
            this.amount = request.amount
            this.status = TransactionStatus.PENDING
            this.merchantId = request.merchantId
        }.let {
            paymentTransactionRepository.save(it)
        }
        //notification
        TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
            override fun afterCommit() {
                notificationService.notify(
                    id = entity.id!!,
                    status = entity.status!!,
                    message = entity.message
                )
            }
        })
        return PaymentStatusResponse.fromEntity(entity)
    }

    @Transactional
    override fun updateStatus(id: String, status: TransactionStatus, message: String?) =
        paymentTransactionRepository.updatePaymentStatusById(
            id = id,
            status = status,
            message = message
        )
}