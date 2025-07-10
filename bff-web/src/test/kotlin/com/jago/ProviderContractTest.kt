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

    // @State methods do not have access to the CDI context (GitHub issue) https://github.com/quarkiverse/quarkus-pact/issues/2
    @State("accountId 1 exists")
    fun setUpAccountId1Exists() {
        // docker run --rm -t --name pact-stubs -p 8091:8091 -v "${PACT_FOLDER}:/app/pacts" pactfoundation/pact-stub-server -p 8091 -d pacts --cors
    }

    @State("accountId 2 does not exist")
    fun setUpAccountId2NotExists() {
        // docker run --rm -t --name pact-stubs -p 8091:8091 -v "${PACT_FOLDER}:/app/pacts" pactfoundation/pact-stub-server -p 8091 -d pacts --cors
    }
}
