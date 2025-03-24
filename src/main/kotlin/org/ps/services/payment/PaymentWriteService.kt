package org.ps.services.payment

import org.ps.dto.requests.PaymentCreateRequest
import org.ps.dto.responces.PaymentStatusResponse
import org.ps.enums.TransactionStatus

interface PaymentWriteService {

    fun createPayment(request: PaymentCreateRequest): PaymentStatusResponse

    fun updateStatus(id: String,
                     status: TransactionStatus,
                     message: String? = null)
}