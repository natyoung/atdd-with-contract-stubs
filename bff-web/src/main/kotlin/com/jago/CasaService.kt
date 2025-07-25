package com.jago

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import org.jboss.resteasy.reactive.RestResponse

@ApplicationScoped
@Path("/")
@RegisterRestClient(configKey = "casa-api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
interface CasaService {
    data class CasaDepositRequest(val accountId: String, val amount: Int)
    data class CasaDepositResponse(val result: String = "")
    data class CasaBalanceResponse(val balance: Int = 0)

    @Path("/deposit")
    @POST
    fun deposit(request: CasaDepositRequest): RestResponse<CasaDepositResponse>

    @Path("/balance/{accountId}")
    @GET
    fun balance(@PathParam("accountId") accountId: String): RestResponse<CasaBalanceResponse>
}
