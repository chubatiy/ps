package org.ps.services.payment

import lombok.extern.slf4j.Slf4j
import org.hibernate.query.sqm.tree.SqmNode.log
import org.ps.services.acquirer.Acquirer
import org.ps.services.notification.NotificationService
import org.springframework.stereotype.Service

@Slf4j
@Service
class PaymentProcessServiceImpl(private val notificationService: NotificationService) : PaymentProcessService {

    override fun processPayment(id: String, acquirer: Acquirer) {
        log.info("Processing payment with id: $id")
        val result = acquirer.processTransaction(id)
        //
        log.info("Payment processed with result: ${result.first} and message: ${result.second}")
        notificationService.notify(
            id = id,
            status = result.first,
            message = result.second
        )
    }

}