package org.ps.repositories

import org.ps.entities.CardEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


/**
 * Repository interface for managing card information in the database.
 * Extends Spring's `CrudRepository` to provide basic CRUD operations for `CardEntity`.
 *
 * This repository is used to store, retrieve, update, and delete card information,
 * such as the card number, expiry date, CVV, currency, and optional description.
 */
@Repository
interface CardRepository : CrudRepository<CardEntity, String> {
}