package org.ps.dto.requests

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.ps.enums.Currency
import org.ps.validation.annotations.CardNumber
import org.ps.validation.annotations.ExpDate
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin

class PaymentCreateRequest {

    @NotEmpty
    @CardNumber
    var cardNumber: String? = null

    @NotEmpty
    @Size(min = 4, max = 4)
    @ExpDate
    var expiryDate: String? = null

    @NotEmpty
    @Size(min = 3, max = 3)
    var cvv: String? = null

    @NotNull
    @DecimalMin("0.01")
    var amount: BigDecimal? = null

    @NotNull
    var currency: Currency? = null

    @NotEmpty
    @NotEmpty
    var merchantId: String? = null
}