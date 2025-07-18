package com.jago

import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.ws.rs.WebApplicationException
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.hamcrest.CoreMatchers.equalTo
import org.jboss.resteasy.reactive.RestResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*


@QuarkusTest
class CasaResourceTest {
    @InjectMock
    @RestClient
    lateinit var casaService: CasaService

    lateinit var accountId: String

    @BeforeEach
    fun setUp() {
        accountId = UUID.randomUUID().toString()
    }

    @Test
    fun `should return success when deposit is successful`() {
        val depositRequest = CasaResource.DepositRequest(1)
        val casaServiceRequest = CasaService.CasaDepositRequest(accountId, depositRequest.amount)

        every { casaService.deposit(casaServiceRequest) } returns RestResponse.ok(CasaService.CasaDepositResponse("success"))

        given()
            .contentType(ContentType.JSON)
            .body("""{"amount": 1}""")
            .`when`()
            .post("/casa/deposit/$accountId")
            .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("result", equalTo("success"))
    }

    @Test
    fun `should handle account not found for deposit`() {
        val depositRequest = CasaResource.DepositRequest(1)
        val casaServiceRequest = CasaService.CasaDepositRequest(accountId, depositRequest.amount)

        every { casaService.deposit(casaServiceRequest) } throws WebApplicationException(
            "Account not found",
            RestResponse.Status.NOT_FOUND.statusCode
        )

        given()
            .contentType(ContentType.JSON)
            .body("""{"amount": 1}""")
            .`when`()
            .post("/casa/deposit/$accountId")
            .then()
            .log().all()
            .statusCode(404)
    }

    @Test
    fun `should handle unknown error for deposit`() {
        val depositRequest = CasaResource.DepositRequest(1)
        val casaServiceRequest = CasaService.CasaDepositRequest(accountId, depositRequest.amount)

        every { casaService.deposit(casaServiceRequest) } throws WebApplicationException(
            "Unknown error",
            RestResponse.Status.INTERNAL_SERVER_ERROR.statusCode
        )

        given()
            .contentType(ContentType.JSON)
            .body("""{"amount": 1}""")
            .`when`()
            .post("/casa/deposit/$accountId")
            .then()
            .log().all()
            .statusCode(500)
    }

    @Test
    fun `should return the balance`() {
        every { casaService.balance(accountId) } returns RestResponse.ok(CasaService.CasaBalanceResponse(1))

        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/casa/balance/$accountId")
            .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("balance", equalTo(1))
    }
}
