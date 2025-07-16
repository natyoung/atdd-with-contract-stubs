package com.jago

import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactFolder
import io.quarkus.test.junit.QuarkusTest
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith

@Provider("bff-web")
@PactFolder("../ui-web/pacts")
@QuarkusTest
class ProviderContractTest {

    @ConfigProperty(name = "quarkus.http.test-port")
    private lateinit var quarkusPort: String

    @BeforeEach
    fun before(context: PactVerificationContext) {
        context.target = HttpTestTarget("localhost", quarkusPort.toInt())
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    fun pactVerificationTestTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("accountId 1 exists")
    fun setUpAccountId1Exists() {
        // PACT_FOLDER=<absolute-path-to>/build/pacts PORT=8091 ./go.sh run_pact_stubs
        // We could use wiremock or webmock here, though that interaction wouldn't be verified on both sides in a CI pipeline after every commit.
    }

    @State("accountId 2 does not exist")
    fun setUpAccountId2NotExists() {
    }
}
