package com.jago.casamanagement.infrastructure.rest

import com.jago.casamanagement.application.CasaApplicationService
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.math.BigDecimal

data class DepositResponse(val result: String)
data class DepositRequest(val amount: Int = 0, var accountId: String = "")
data class BalanceResponse(val balance: Int)

@Path("/")
class CasaResource @Inject constructor(
    private val casaApplicationService: CasaApplicationService
) {
    @POST
    @Path("/deposit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun deposit(request: DepositRequest): Response {
        val result = casaApplicationService.deposit(request.accountId, request.amount)
        return Response.ok(DepositResponse("success")).build()
    }

    @GET
    @Path("/balance/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun balance(@PathParam("accountId") accountId: String): Response {
        val result = casaApplicationService.balance(accountId)
        return Response.ok(BalanceResponse(result)).build()
    }
}
