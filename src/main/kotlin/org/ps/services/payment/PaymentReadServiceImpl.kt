package org.ps.services.payment

import org.ps.dto.responces.PaymentDetailResponse
import org.ps.dto.responces.PaymentStatusResponse
import org.ps.exception.NotFoundException
import org.ps.repositories.PaymentTransactionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PaymentReadServiceImpl(
    private val paymentTransactionRepository: PaymentTransactionRepository
) : PaymentReadService {

    override fun getById(id: String) = paymentTransactionRepository.findByIdOrNull(id)?.let {
        PaymentStatusResponse.fromEntity(it)
    } ?: throw NotFoundException("Payment not found")

    override fun getDetailById(id: String, maskDetails: Boolean) = paymentTransactionRepository.findByIdOrNull(id)?.let {
        PaymentDetailResponse.fromEntity(entity = it, maskDetails = maskDetails)
    } ?: throw NotFoundException("Payment not found")
}