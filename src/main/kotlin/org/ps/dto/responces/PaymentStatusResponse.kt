package org.ps.dto.responces

import org.ps.entities.PaymentTransactionEntity
import org.ps.enums.TransactionStatus

data class PaymentStatusResponse(
    val id: String,
    val status: TransactionStatus,
    val message: String? = null
) {
    companion object {
        fun fromEntity(entity: PaymentTransactionEntity) = PaymentStatusResponse(
            id = entity.id!!,
            status = entity.status!!,
            message = entity.message
        )
    }
}