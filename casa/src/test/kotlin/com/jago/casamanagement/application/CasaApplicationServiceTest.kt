package com.jago.casamanagement.applicationo

import com.jago.casamanagement.application.CasaApplicationService
import com.jago.casamanagement.domain.model.CasaAccount
import com.jago.casamanagement.domain.service.CasaService
import com.jago.casamanagement.infrastructure.persistence.CasaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.ws.rs.WebApplicationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class CasaApplicationServiceTest {
    private val casaRepository: CasaRepository = mockk()
    private val casaService: CasaService = mockk()
    private val casaApplicationService = CasaApplicationService(casaRepository, casaService)

    @Test
    fun `should deposit to existing account successfully`() {
        // Given
        val accountId = "1"
        val amount = 100
        val account = CasaAccount().apply {
            this.accountId = accountId
            this.balance = BigDecimal.ZERO
        }
        val updatedAccount = CasaAccount().apply {
            this.accountId = accountId
            this.balance = BigDecimal(100)
        }

        every { casaRepository.findByAccountId(accountId) } returns account
        every { casaService.deposit(account, BigDecimal(amount)) } returns Unit
        every { casaRepository.save(account) } returns updatedAccount

        // When
        val result = casaApplicationService.deposit(accountId, amount)

        // Then
        assertEquals(updatedAccount, result)
        verify {
            casaRepository.findByAccountId(accountId)
            casaService.deposit(account, BigDecimal(amount))
            casaRepository.save(account)
        }
    }

    @Test
    fun `should throw a 404 error for deposit to non-existing account`() {
        // Given
        val accountId = "2"
        val amount = 100

        every { casaRepository.findByAccountId(accountId) } returns null

        // When
        val exception = assertThrows<WebApplicationException> {
            casaApplicationService.deposit(accountId, amount)
        }

        // Then
        assertEquals("Account not found", exception.message)
        assertEquals(404, exception.response.status)
        verify { casaRepository.findByAccountId(accountId) }
    }

    @Test
    fun `should get the balance of an existing account successfully`() {
        // Given
        val accountId = "1"
        val account = CasaAccount().apply {
            this.accountId = accountId
            this.balance = BigDecimal.ONE
        }

        every { casaRepository.findByAccountId(accountId) } returns account

        // When
        val result = casaApplicationService.balance(accountId)

        // Then
        assertEquals(1, result)
        verify {
            casaRepository.findByAccountId(accountId)
        }
    }

    @Test
    fun `should throw a 404 error for balance query on non-existing account`() {
        // Given
        val accountId = "2"

        every { casaRepository.findByAccountId(accountId) } returns null

        // When
        val exception = assertThrows<WebApplicationException> {
            casaApplicationService.balance(accountId)
        }

        // Then
        assertEquals("Account not found", exception.message)
        assertEquals(404, exception.response.status)
        verify { casaRepository.findByAccountId(accountId) }
    }
}
