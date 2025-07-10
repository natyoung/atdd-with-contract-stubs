package com.jago.casamanagement.infrastructure.persistence

import com.jago.casamanagement.domain.model.CasaAccount

interface CasaRepository {
    fun findByAccountId(accountId: String): CasaAccount?
    fun save(account: CasaAccount): CasaAccount
}
