package com.exam.service;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/q/health")
@RegisterClientHeaders
@RegisterRestClient(configKey = "proxy-health-consumer-api")
public interface HealthProxyService {
    

    @GET
    @Path("/live")
    @Produces(MediaType.TEXT_PLAIN)
    public String liveness();

    @GET
    @Path("/ready")
    @Produces(MediaType.TEXT_PLAIN)
    public String readiness();

}
