package org.ps.services.orchestration

import org.ps.dto.events.TransactionCompleted
import org.ps.dto.events.TransactionCreated
import org.ps.dto.events.TransactionFailed

/**
 * Interface representing the orchestration service that interacts with various event messages
 * related to payment transactions and triggers the appropriate service actions.
 */
interface OrchestrationService {

    /**
     * Handles incoming messages related to the creation of payment transactions.
     * Processes the payment by routing it to the appropriate acquirer and proceeds
     * with the associated actions required to finalize the creation process.
     *
     * @param message The transaction creation event containing details about the newly created payment,
     * including its unique identifier.
     */
    fun receiveCreatedPayment(message: TransactionCreated)

    /**
     * Handles the processing of a completed payment transaction event.
     *
     * This method is triggered when a payment transaction is marked as completed.
     * It updates the status of the transaction to COMPLETED in the system using the provided event details.
     *
     * @param message The transaction completion event which contains the unique identifier of
     *                the transaction that has been completed.
     */
    fun receiveCompletedPayment(message: TransactionCompleted)

    /**
     * Handles incoming messages related to failed payment transactions.
     * Updates the payment status to FAILED in the system and logs or processes the failure message.
     *
     * @param message The transaction failure event containing details about the failed payment,
     * including its unique identifier and an associated failure description.
     */
    fun receiveFailedPayment(message: TransactionFailed)
}