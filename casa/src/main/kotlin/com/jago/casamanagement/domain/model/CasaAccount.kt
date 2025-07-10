package com.jago.casamanagement.domain.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import java.math.BigDecimal

@Entity
class CasaAccount : PanacheEntity() {
    lateinit var accountId: String
    var balance: BigDecimal = BigDecimal.ZERO
}
