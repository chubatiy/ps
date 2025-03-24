package org.ps.validation.constraints

import jakarta.validation.ConstraintValidator
import org.ps.validation.annotations.ExpDate
import java.time.YearMonth

/**
 * `ExpDateConstraint` is a custom implementation of the `ConstraintValidator` interface that validates
 * expiration date strings annotated with `@ExpDate`.
 *
 * The validation ensures that the input string adheres to the format "MMYY" where:
 * - `MM`: Represents the month, must be between "01" and "12".
 * - `YY`: Represents the last two digits of a year.
 *
 * The input is further validated to confirm that the encoded date corresponds to the current
 * year and month or a future date.
 */
class ExpDateConstraint : ConstraintValidator<ExpDate, String> {

    private val regex = Regex("^(0[1-9]|1[0-2])\\d{2}$")


    override fun isValid(value: String, context: jakarta.validation.ConstraintValidatorContext?): Boolean {
        if (!regex.matches(value)) return false

        val month = value.substring(0, 2).toInt()
        val year = value.substring(2, 4).toInt()

        val expiryDate = YearMonth.of(2000 + year, month)

        return expiryDate >= YearMonth.now()
    }
}