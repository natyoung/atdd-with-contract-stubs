package com.jago.casamanagement

import org.h2.tools.Server
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes

@ApplicationScoped
class H2PgServer {
    fun start(@Observes ev: StartupEvent) {
        Server.createPgServer("-pgAllowOthers", "-pgPort", "5435").start()  // Port 5435 to avoid conflicts; change if needed
    }
}
