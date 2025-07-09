package com.jago

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.V4Pact
import au.com.dius.pact.core.model.annotations.Pact
import io.quarkus.test.junit.QuarkusTest
import jakarta.ws.rs.WebApplicationException
import org.eclipse.microprofile.rest.client.RestClientBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URI
import kotlin.test.assertFailsWith

@QuarkusTest
@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "casa")
class CasaConsumerContractTest {

    @Pact(consumer = "bff-web")
    fun depositToAccountSuccess(builder: PactDslWithProvider): V4Pact {
        return builder
            .given("accountId 1 exists")
            .uponReceiving("a deposit request for account 1")
            .path("/deposit")
            .method("POST")
            .headers(mapOf("Content-Type" to "application/json"))
            .body("""{"accountId": "1", "amount": 1}""", "application/json")
            .willRespondWith()
            .status(200)
            .body("""{"result": "success"}""", "application/json")
            .toPact(V4Pact::class.java)
    }

    @Pact(consumer = "bff-web")
    fun testDepositAccountNotFound(builder: PactDslWithProvider): V4Pact {
        return builder
            .given("accountId 2 does not exist")
            .uponReceiving("a deposit request for account 2")
            .path("/deposit")
            .method("POST")
            .headers(mapOf("Content-Type" to "application/json"))
            .body("""{"accountId": "2", "amount": 1}""", "application/json")
            .willRespondWith()
            .status(404)
            .toPact(V4Pact::class.java)
    }

    @Test
    @PactTestFor(pactMethod = "depositToAccountSuccess")
    fun `test deposit success for existing account`(mockServer: MockServer) {
        val casaService = RestClientBuilder.newBuilder()
            .baseUri(URI.create(mockServer.getUrl()))
            .build(CasaService::class.java)

        val result = casaService.deposit(CasaService.CasaDepositRequest("1", 1))
        assertEquals(200, result.status)
    }

    @Test
    @PactTestFor(pactMethod = "testDepositAccountNotFound")
    fun `test deposit failure for non-existing account`(mockServer: MockServer) {
        val casaService = RestClientBuilder.newBuilder()
            .baseUri(URI.create(mockServer.getUrl()))
            .build(CasaService::class.java)

        val exception = assertFailsWith<WebApplicationException> {
            casaService.deposit(CasaService.CasaDepositRequest("2", 1))
        }
        assertEquals(404, exception.response.status)
    }
}
