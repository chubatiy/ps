package org.ps.entities

import jakarta.persistence.*
import org.ps.enums.Currency

@Entity
@Table
class CardEntity {

    @Id
    var number: String? = null

    @Column(name = "expiry_date")
    var expiryDate: String? = null

    @Column(name = "cvv")
    var cvv: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    var currency: Currency? = null

    @Column(name = "description")
    var description: String? = null
}