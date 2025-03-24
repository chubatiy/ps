package org.ps.entities

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.ps.entities.converter.LocalDateTimeToZonedDateTimeConverter
import org.ps.enums.TransactionStatus
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID

@Entity
@Table(name = "payment_transaction")
class PaymentTransactionEntity {

    @Id
    @Column(name = "id")
    var id: String? = null

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "card_number", referencedColumnName = "number")
    @NotNull
    var card: CardEntity? = null

    @Column(name = "amount")
    @NotNull
    var amount: BigDecimal? = null

    @Column(name = "merchantId")
    @NotNull
    var merchantId: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    var status: TransactionStatus? = null

    @Column(name = "message")
    var message: String? = null

    @Column(name = "created")
    @Convert(converter = LocalDateTimeToZonedDateTimeConverter::class)
    @NotNull
    var created: ZonedDateTime? = null

    @Column(name = "updated")
    var updated: ZonedDateTime? = null

    @PrePersist
    private fun onCreate() {
        if (id == null) id = UUID.randomUUID().toString()
        created = ZonedDateTime.now()
        updated = created
    }

    @PreUpdate
    private fun onUpdate() {
        updated = ZonedDateTime.now()
    }
}