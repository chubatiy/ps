package org.ps.controllers

import jakarta.validation.Valid
import org.ps.dto.requests.PaymentCreateRequest
import org.ps.dto.responces.ApiResponse
import org.ps.services.payment.PaymentReadService
import org.ps.services.payment.PaymentWriteService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/payments")
class PaymentController(
    private val paymentWriteService: PaymentWriteService,
    private val paymentReadService: PaymentReadService
) {

    @PostMapping
    fun createPayment(@Valid @RequestBody payment: PaymentCreateRequest) =
        paymentWriteService.createPayment(request = payment).let {
            ApiResponse.success(it)
        }

    @GetMapping("/status/{id}")
    fun getPaymentStatus(@PathVariable("id") id: String) = paymentReadService.getById(id = id).let {
        ApiResponse.success(it)
    }

    @GetMapping("/status/{id}/detail")
    fun getPaymentDetail(@PathVariable("id") id: String) = paymentReadService.getDetailById(id = id).let {
        ApiResponse.success(it)
    }
}