package org.ps.repositories

import org.ps.entities.PaymentTransactionEntity
import org.ps.enums.TransactionStatus
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for managing payment transactions in the database.
 * Extends Spring's `CrudRepository` to provide basic CRUD operations.
 * Includes a custom query to update the status and message of a `PaymentTransactionEntity` by its ID.
 */
@Repository
interface PaymentTransactionRepository : CrudRepository<PaymentTransactionEntity, String> {

    @Modifying
    @Query("""
        UPDATE PaymentTransactionEntity p 
        SET p.status = :status, 
            p.message = :message 
        WHERE p.id = :id
    """)
    fun updatePaymentStatusById(id: String, status: TransactionStatus, message: String? = null)
}