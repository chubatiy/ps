package org.ps.services.payment

import org.ps.services.acquirer.Acquirer

interface PaymentProcessService {

    fun processPayment(id: String, acquirer: Acquirer)
}