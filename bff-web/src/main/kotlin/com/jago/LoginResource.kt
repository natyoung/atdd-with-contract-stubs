package com.jago

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestResponse

@ApplicationScoped
@Path("/login")
class LoginResource {
    data class Login(val accountId: String = "", val password: String = "")
    data class User(val username: String)

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun login(login: Login): RestResponse<User> {
        return RestResponse.ok(User(login.accountId))
    }
}
