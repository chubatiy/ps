package org.ps.services.acquirer

import org.ps.services.payment.PaymentReadService
import org.springframework.stereotype.Component

/**
 * Implementation of the `AcquirerBinAbstract` class that defines support for processing transactions
 * based on the sum of the digits in the Bank Identification Number (BIN).
 *
 * This specific implementation supports transactions where the BIN sum is an even number.
 *
 * @constructor Initializes the acquirer with the provided `PaymentReadService`.
 * @param paymentReadService The service used to access payment details required for processing transactions.
 */
@Component
class AcquirerAImpl(
    paymentReadService: PaymentReadService
) : AcquirerBinAbstract(
    paymentReadService = paymentReadService
) {
    override fun support(binSum: Int) = binSum % 2 == 0
}