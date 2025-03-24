package org.ps.dto.responces

import org.ps.entities.PaymentTransactionEntity
import org.ps.enums.Currency
import org.ps.enums.TransactionStatus
import org.ps.extensions.getMasked
import java.math.BigDecimal
import java.time.ZonedDateTime

data class PaymentDetailResponse(
    val id: String,
    val status: TransactionStatus,
    val amount: BigDecimal,
    val cardNumber: String,
    val currency: Currency,
    val merchantId: String? = null,
    val crated: ZonedDateTime,
    val updated: ZonedDateTime,
    val message: String? = null
) {
    companion object {
        fun fromEntity(entity: PaymentTransactionEntity, maskDetails: Boolean) = PaymentDetailResponse(
            id = entity.id!!,
            status = entity.status!!,
            amount = entity.amount!!,
            cardNumber = if (maskDetails) entity.card!!.number!!.getMasked() else entity.card!!.number!!,
            currency = entity.card!!.currency!!,
            merchantId = if (maskDetails) entity.merchantId!!.getMasked() else entity.merchantId!!,
            crated = entity.created!!,
            updated = entity.updated!!,
            message = entity.message
        )
    }
}