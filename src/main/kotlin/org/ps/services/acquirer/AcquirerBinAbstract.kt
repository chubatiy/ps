package org.ps.services.acquirer

import org.ps.enums.TransactionStatus
import org.ps.extensions.isEven
import org.ps.services.payment.PaymentReadService

/**
 * Represents an abstract implementation of the `Acquirer` interface, providing a base for specific acquirer implementations
 * that use BIN (Bank Identification Number) or derived sums to determine support for processing transactions.
 *
 * This class offers shared functionality such as obtaining a unique identifier, evaluating BIN-based support,
 * and processing transactions while delegating specific support logic to subclasses.
 *
 * @constructor Initializes the abstract acquirer with the provided `PaymentReadService`.
 * @param paymentReadService The service used to access payment details required for processing transactions.
 */
abstract class AcquirerBinAbstract(
    private val paymentReadService: PaymentReadService
) : Acquirer {

    override fun id(): String = this.javaClass.name

    override fun support(cardNumber: String): Boolean {
        val bin = cardNumber.take(6)
        val binSum = bin.map { it - '0' }.sum()
        return support(binSum)
    }

    /**
     * Determines whether the current acquirer supports processing transactions based on the provided BIN sum.
     *
     * @param binSum The sum of the digits in the Bank Identification Number (BIN) of the card.
     * @return True if the acquirer supports processing transactions for the given BIN sum, false otherwise.
     */
    abstract fun support(binSum: Int): Boolean

    override fun processTransaction(id: String): Pair<TransactionStatus, String?> {
        val payment = paymentReadService.getDetailById(id = id, maskDetails = false)

        return if (payment.amount.isEven())
            TransactionStatus.COMPLETED to null
        else
            TransactionStatus.FAILED to "Amount ${payment.amount} is not valid. Should be even"
    }
}