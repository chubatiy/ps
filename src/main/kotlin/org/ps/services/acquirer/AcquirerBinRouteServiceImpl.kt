package org.ps.services.acquirer

import org.ps.services.payment.PaymentReadService
import org.springframework.stereotype.Service

/**
 * Implementation of the `AcquirerRouteService` interface responsible for routing transactions
 * to the appropriate acquirer based on the transaction details, specifically the card number.
 *
 * This service uses a list of available acquirers and determines the correct acquirer
 * for a transaction by evaluating which acquirer supports the given card number.
 *
 * @constructor Creates an instance of the service with the required `PaymentReadService`
 * and a list of available acquirers.
 * @param paymentReadService Service responsible for retrieving payment details using transaction IDs.
 * @param acquirers List of available acquirers used to identify the appropriate acquirer for a given transaction.
 */
@Service
class AcquirerBinRouteServiceImpl(
    private val paymentReadService: PaymentReadService,
    private val acquirers: List<Acquirer>
) : AcquirerRouteService {

    override fun routeTransactionById(id: String): Acquirer {
        val payment = paymentReadService.getDetailById(id = id, maskDetails = false)
        //
        return acquirers.firstOrNull { it.support(payment.cardNumber) }
            ?: throw IllegalArgumentException("No acquirer found for card number ${payment.cardNumber}")
    }
}