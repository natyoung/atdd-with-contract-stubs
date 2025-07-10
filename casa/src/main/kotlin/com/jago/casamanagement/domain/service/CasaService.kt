package com.jago.casamanagement.domain.service

import com.jago.casamanagement.domain.model.CasaAccount
import jakarta.enterprise.context.ApplicationScoped
import java.math.BigDecimal

@ApplicationScoped
class CasaService {
    fun deposit(account: CasaAccount, amount: BigDecimal) {
        account.balance = account.balance.add(amount)
    }
}