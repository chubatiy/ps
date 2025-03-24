package org.ps.services.payment

import org.ps.services.acquirer.Acquirer
import org.ps.services.notification.NotificationService
import org.springframework.stereotype.Service

@Service
class PaymentProcessServiceImpl(private val notificationService: NotificationService) : PaymentProcessService {

    override fun processPayment(id: String, acquirer: Acquirer) {
        val result = acquirer.processTransaction(id)
        //
        notificationService.notify(
            id = id,
            status = result.first,
            message = result.second
        )
    }

}