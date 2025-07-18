package contracts

import au.com.dius.pact.provider.junit5.HttpTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import au.com.dius.pact.provider.junitsupport.Provider
import au.com.dius.pact.provider.junitsupport.State
import au.com.dius.pact.provider.junitsupport.loader.PactFolder
import com.zaxxer.hikari.HikariDataSource
import io.quarkus.test.junit.QuarkusTest
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith

@Provider("casa")
@PactFolder("../bff-web/build/pacts")
@QuarkusTest
@ExtendWith(PactVerificationInvocationContextProvider::class)
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

    @State("accountId 1 exists with balance of 1")
    fun setUpAccountId1WithBalance1() {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute("DELETE FROM CasaAccount WHERE accountId = '1'")
                statement.execute("INSERT INTO CasaAccount (id, accountId, balance) VALUES (1, '1', 1)")
            }
        }
    }

    @State("accountId 1 exists")
    fun setUpAccountId1Exists() {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute("DELETE FROM CasaAccount WHERE accountId = '1'")
                statement.execute("INSERT INTO CasaAccount (id, accountId, balance) VALUES (1, '1', 0.00)")
            }
        }
    }

    @State("accountId 2 does not exist")
    fun setUpAccountId2NotExists() {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute("DELETE FROM CasaAccount WHERE accountId = '2'")
            }
        }
    }

    // Workaround for the quarkus CDI container being unavailable when @State methods run
    companion object {
        val dataSource: HikariDataSource = HikariDataSource().apply {
            jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
            username = "sa"
            password = ""
        }
    }
}
