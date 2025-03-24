package org.ps.services.orchestration

import org.ps.dto.events.TransactionCompleted
import org.ps.dto.events.TransactionCreated
import org.ps.dto.events.TransactionFailed
import org.ps.consts.*
import org.ps.enums.TransactionStatus
import org.ps.services.acquirer.AcquirerRouteService
import org.ps.services.notification.NotificationService
import org.ps.services.payment.PaymentProcessService
import org.ps.services.payment.PaymentWriteService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

/**
 * Service responsible for orchestrating various operations related to payment transactions.
 *
 * It listens to message queues for specific transaction events and performs the corresponding
 * actions by leveraging other services such as routing, processing, updating status, and sending notifications.
 *
 * @constructor Initializes the service with its dependencies.
 * @param acquirerRouteService Service responsible for routing transactions to the appropriate acquirer.
 * @param paymentProcessService Service responsible for processing payments with a routed acquirer.
 * @param paymentWriteService Service responsible for updating the status of payment transactions.
 * @param notificationService Service responsible for sending notifications regarding transaction statuses.
 */
@Service
class OrchestrationServiceImpl(
    private val acquirerRouteService: AcquirerRouteService,
    private val paymentProcessService: PaymentProcessService,
    private val paymentWriteService: PaymentWriteService,
    private val notificationService: NotificationService
) : OrchestrationService {

    /**
     * Handles incoming messages from the PAYMENT_CREATED_QUEUE regarding created payment transactions.
     * Processes the payment by routing it to the appropriate acquirer and updates its status.
     *
     * @param message The transaction creation event containing details about the newly created payment.
     */
    @RabbitListener(queues = [PAYMENT_CREATED_QUEUE])
    override fun receiveCreatedPayment(message: TransactionCreated) {
        try {
            val acquirer = acquirerRouteService.routeTransactionById(id = message.id)
            //
            paymentProcessService.processPayment(
                id = message.id,
                acquirer = acquirer
            )
        } catch (e: IllegalArgumentException) {
            notificationService.notify(
                id = message.id,
                status = TransactionStatus.FAILED,
                message = e.message
            )
        }
    }

    /**
     * Handles the completion of a payment transaction by updating its status to COMPLETED
     * in the system based on the received message.
     *
     * @param message The transaction completion event containing the transaction ID
     *                for which the status should be updated.
     */
    @RabbitListener(queues = [PAYMENT_COMPLETED_QUEUE])
    override fun receiveCompletedPayment(message: TransactionCompleted) =
        paymentWriteService.updateStatus(
            id = message.id,
            status = TransactionStatus.COMPLETED
        )

    /**
     * Handles incoming messages from the PAYMENT_FAILED_QUEUE regarding failed payment transactions.
     * Updates the payment status to FAILED using the provided payment details.
     *
     * @param message The transaction failure event containing details about the failed payment,
     * such as its unique identifier and an associated failure message.
     */
    @RabbitListener(queues = [PAYMENT_FAILED_QUEUE])
    override fun receiveFailedPayment(message: TransactionFailed) =
        paymentWriteService.updateStatus(
            id = message.id,
            status = TransactionStatus.FAILED,
            message = message.message
        )
}