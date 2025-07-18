package com.jago

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.resteasy.reactive.RestResponse

@ApplicationScoped
@Path("/casa")
class CasaResource {
    data class DepositRequest(val amount: Int = 0, var accountId: String = "")

    @Inject
    @RestClient
    private lateinit var casaService: CasaService

    @POST
    @Path("/deposit/{accountId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun deposit(
        @PathParam("accountId") accountId: String,
        request: DepositRequest
    ): RestResponse<CasaService.CasaDepositResponse> {
        try {
            val casaDepositRequest = CasaService.CasaDepositRequest(accountId, request.amount)
            val response: RestResponse<CasaService.CasaDepositResponse> = casaService.deposit(casaDepositRequest)
            return when (response.status) {
                RestResponse.StatusCode.OK -> return response
                RestResponse.StatusCode.NOT_FOUND -> RestResponse.status(RestResponse.Status.NOT_FOUND)
                else -> throw WebApplicationException("Unexpected status: ${response.status}", response.status)
            }
        } catch (e: WebApplicationException) {
            return RestResponse.status(e.response.status)
        }
    }

    @GET
    @Path("/balance/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    fun balance(@PathParam("accountId") accountId: String): RestResponse<CasaService.CasaBalanceResponse> {
        try {
            val response: RestResponse<CasaService.CasaBalanceResponse> = casaService.balance(accountId)
            return when (response.status) {
                RestResponse.StatusCode.OK -> return response
                RestResponse.StatusCode.NOT_FOUND -> RestResponse.status(RestResponse.Status.NOT_FOUND)
                else -> throw WebApplicationException("Unexpected status: ${response.status}", response.status)
            }
        } catch (e: WebApplicationException) {
            return RestResponse.status(e.response.status)
        }
    }
}
