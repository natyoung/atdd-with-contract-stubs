package com.jago.casamanagement.infrastructure.rest

import com.jago.casamanagement.application.CasaApplicationService
import com.jago.casamanagement.domain.model.CasaAccount
import io.mockk.every
import io.quarkiverse.test.junit.mockk.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import jakarta.ws.rs.WebApplicationException
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import java.math.BigDecimal


@QuarkusTest
class CasaResourceTest {

    @InjectMock
    lateinit var casaApplicationService: CasaApplicationService

    @Test
    fun `should deposit to existing account successfully`() {
        every { casaApplicationService.deposit("1", 1) } returns CasaAccount().apply {
            accountId = "1"
            balance = BigDecimal(1)
        }

        print(casaApplicationService)
        given()
            .contentType(ContentType.JSON)
            .body("""{"amount": 1, "accountId": "1"}""")
            .`when`()
            .post("/deposit")
            .then()
            .log().all()
            .statusCode(200)
            .body("result", equalTo("success"))
    }

    @Test
    fun `should return 404 for non-existing account`() {
        every { casaApplicationService.deposit("2", 1) } throws WebApplicationException("Account not found", 404)

        given()
            .contentType(ContentType.JSON)
            .body("""{"amount": 1, "accountId": "2"}""")
            .`when`()
            .post("/deposit")
            .then()
            .log().all()
            .statusCode(404)
    }
}
