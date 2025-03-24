package org.ps.validation.constraints

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.ps.validation.annotations.CardNumber

/**
 * `CardNumberConstraint` is a custom implementation of the `ConstraintValidator` interface that validates
 * credit or debit card numbers annotated with `@CardNumber`.
 *
 * The validation applies the Luhn algorithm (also known as the Modulus 10 algorithm) to ensure that the
 * card number is well-formed. This algorithm works as follows:
 * - Starting from the rightmost digit (the check digit), every second digit is doubled.
 * - If the doubling results in a two-digit number, the digits of the resulting number are summed.
 * - All digits (both unprocessed and processed) are added together, and the resulting total must be divisible by 10.
 *
 * This validator checks that the provided card number complies with the Luhn algorithm, ensuring its validity.
 */
class CardNumberConstraint : ConstraintValidator<CardNumber, String> {

    override fun initialize(constraintAnnotation: CardNumber) {}

    override fun isValid(value: String, context: ConstraintValidatorContext?): Boolean {
        val nDigits: Int = value.length

        var nSum = 0
        var isSecond = false
        for (i in nDigits - 1 downTo 0) {
            var d: Int = value[i] - '0'

            if (isSecond) d *= 2


            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10
            nSum += d % 10

            isSecond = !isSecond
        }
        return (nSum % 10 == 0)
    }
}