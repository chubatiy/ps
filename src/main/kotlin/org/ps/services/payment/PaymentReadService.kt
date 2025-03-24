package org.ps.services.payment

import org.ps.dto.responces.PaymentDetailResponse
import org.ps.dto.responces.PaymentStatusResponse

interface PaymentReadService {

    fun getById(id: String): PaymentStatusResponse

    fun getDetailById(id: String, maskDetails: Boolean = true): PaymentDetailResponse
}