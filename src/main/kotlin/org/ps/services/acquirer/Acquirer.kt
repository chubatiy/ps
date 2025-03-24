package org.ps.services.acquirer

import org.ps.enums.TransactionStatus

/**
 * Represents an acquirer responsible for handling transactions and determining
 * support based on card numbers or specific conditions.
 */
interface Acquirer {

    /**
     * Returns the unique identifier of the acquirer.
     *
     * @return the class name of the implementing acquirer as the identifier.
     */
    fun id(): String

    /**
     * Checks if the current acquirer supports processing transactions for the given card number.
     *
     * @param cardNumber The card number to check for support.
     * @return True if the acquirer supports the card number, false otherwise.
     */
    fun support(cardNumber: String): Boolean

    /**
     * Processes a transaction based on the provided transaction ID.
     * Determines the transaction status and optionally provides a message
     * describing the reason for any failure.
     *
     * @param id The unique identifier of the transaction to be processed.
     * @return A pair containing the transaction status and an optional failure message.
     *         If the transaction succeeds, the message will be null.
     */
    fun processTransaction(id: String): Pair<TransactionStatus, String?>
}