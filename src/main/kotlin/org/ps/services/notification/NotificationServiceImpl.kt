package org.ps.services.notification

import org.ps.dto.events.TransactionCompleted
import org.ps.dto.events.TransactionCreated
import org.ps.dto.events.TransactionFailed
import org.ps.consts.*
import org.ps.enums.TransactionStatus
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

/**
 * Implementation of the NotificationService interface responsible for handling the dispatch of
 * transaction notifications to a messaging system using RabbitMQ.
 *
 * Notifications are routed to specific queues based on the transaction status:
 * - PENDING: Routes messages to the `PAYMENT_CREATED_QUEUE`.
 * - COMPLETED: Routes messages to the `PAYMENT_COMPLETED_QUEUE`.
 * - FAILED: Routes messages to the `PAYMENT_FAILED_QUEUE` with an associated error message.
 *
 * Utilizes the RabbitTemplate for interacting with RabbitMQ to publish the transaction events.
 *
 * @constructor Initializes the service with a RabbitTemplate instance used for message publication.
 */
@Service
class NotificationServiceImpl(private val rabbitTemplate: RabbitTemplate) : NotificationService {

    override fun notify(id: String, status: TransactionStatus, message: String?) {

        val destination = when (status) {
            TransactionStatus.PENDING -> PAYMENT_CREATED_QUEUE to TransactionCreated(id = id)
            TransactionStatus.COMPLETED -> PAYMENT_COMPLETED_QUEUE to TransactionCompleted(id = id)
            TransactionStatus.FAILED -> PAYMENT_FAILED_QUEUE to TransactionFailed(id = id, message = message!!)
        }
        //
        rabbitTemplate.convertAndSend(
            PAYMENT_EXCHANGE,
            destination.first,
            destination.second
        )
    }
}