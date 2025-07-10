package com.jago.casamanagement.infrastructure.persistence.panache

import com.jago.casamanagement.domain.model.CasaAccount
import com.jago.casamanagement.infrastructure.persistence.CasaRepository
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CasaPanacheRepository : PanacheRepository<CasaAccount>, CasaRepository {
    override fun findByAccountId(accountId: String): CasaAccount? = find("accountId", accountId).firstResult()
    override fun save(account: CasaAccount): CasaAccount {
        persist(account)
        return account
    }
}
