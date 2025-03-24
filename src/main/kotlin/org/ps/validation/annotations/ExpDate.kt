package org.ps.validation.annotations

import jakarta.validation.Constraint
import org.ps.validation.constraints.ExpDateConstraint
import kotlin.reflect.KClass


/**
 * Annotation for validating expiration date fields.
 *
 * This annotation is used to mark fields within a class that represent expiration dates.
 * The validation logic is implemented using the `ExpDateConstraint` class.
 *
 * The annotated field must follow a specific expiration date format "MMYY", where:
 * - `MM`: Represents the month, ranging from "01" to "12".
 * - `YY`: Represents the last two digits of a year.
 *
 * Additionally, the validation ensures that the encoded date corresponds to the current year
 * and month or a future date. Invalid formats or past dates will result in a validation error.
 *
 * @property message Custom message to display when validation fails. Defaults to "Not following the pattern".
 * @property groups Specifies the validation groups the constraint belongs to.
 * @property payload Can be used to define custom payload objects during validation.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExpDateConstraint::class])
annotation class ExpDate(
    val message: String = "Not following the pattern",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Any>> = []
)

