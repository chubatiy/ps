package org.ps.dto.events

class TransactionFailed(id: String, val message: String) : BaseTransactionEvent(id)