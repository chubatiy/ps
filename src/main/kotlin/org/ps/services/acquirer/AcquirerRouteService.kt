package org.ps.services.acquirer

/**
 * Defines the contract for services responsible for routing transactions to the appropriate acquirer
 * based on specific criteria such as payment details or card information.
 */
interface AcquirerRouteService {

    /**
     * Routes a transaction to the appropriate acquirer based on the transaction ID.
     * The routing is determined by evaluating which acquirer supports the
     * provided transaction's card number or specific conditions.
     *
     * @param id The unique identifier of the transaction to be routed.
     * @return The acquirer responsible for handling the transaction.
     * @throws IllegalArgumentException if no acquirer supports the specified transaction.
     */
    fun routeTransactionById(id: String): Acquirer
}