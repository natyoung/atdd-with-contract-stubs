package com.jago

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
class LoginResourceTest {
    @Test
    fun `should log in`() {
        given()
            .contentType(ContentType.JSON)
            .body("""{"accountId": "accountId", "password": "xyz"}""")
            .`when`()
            .post("/login")
            .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("username", equalTo("accountId"))
    }
}
