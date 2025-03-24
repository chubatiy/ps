package org.ps.services.notification

import org.ps.enums.TransactionStatus

/**
 * Interface representing a service responsible for handling notifications related to payment transactions.
 *
 * Notifications are sent based on the transaction status, such as pending, completed, or failed.
 * Implementations of this interface define the mechanism for sending these notifications,
 * which may involve integrating with external messaging systems or queues.
 */
interface NotificationService {

    /**
     * Sends a notification about a transaction update to the messaging system or process.
     * The notification includes the transaction ID, its updated status, and an optional message.
     *
     * @param id The unique identifier of the transaction.
     * @param status The current status of the transaction. It can be PENDING, COMPLETED, or FAILED.
     * @param message An optional message providing additional information about the transaction,
     *                particularly in the case of a FAILED status.
     */
    fun notify(id: String, status: TransactionStatus, message: String? = null)
}