package com.jago.casamanagement.application

import com.jago.casamanagement.domain.model.CasaAccount
import com.jago.casamanagement.domain.service.CasaService
import com.jago.casamanagement.infrastructure.persistence.CasaRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.WebApplicationException
import java.math.BigDecimal

@ApplicationScoped
class CasaApplicationService @Inject constructor(
    private val casaRepository: CasaRepository,
    private val casaService: CasaService
) {
    fun deposit(accountId: String, amount: Int): CasaAccount {
        val account = casaRepository.findByAccountId(accountId)
            ?: throw WebApplicationException("Account not found", 404)
        casaService.deposit(account, BigDecimal(amount))
        return casaRepository.save(account)
    }
}
