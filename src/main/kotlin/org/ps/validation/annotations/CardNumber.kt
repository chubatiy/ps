package org.ps.validation.annotations

import org.ps.validation.constraints.CardNumberConstraint
import jakarta.validation.Constraint
import kotlin.reflect.KClass


/**
 * Annotation for validating credit or debit card numbers.
 *
 * This annotation is used to mark fields within a class that represent credit or debit card numbers.
 * The validation logic is implemented using the `CardNumberConstraint` class, which applies the Luhn
 * algorithm to ensure the card number's validity.
 *
 * The annotated field must adhere to the Luhn algorithm, a widely used checksum formula for
 * validating identification numbers such as credit card numbers. Invalid card numbers will result in
 * a validation error.
 *
 * @property message Custom message to be displayed in case of a validation failure. Defaults to "Not following the pattern".
 * @property groups Allows specifying validation groups to which this constraint belongs.
 * @property payload Can be used by clients to assign custom payload objects to the constraint.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CardNumberConstraint::class])
annotation class CardNumber(
    val message: String = "Not following the pattern",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)

