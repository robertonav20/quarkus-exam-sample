package com.exam;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.exam.service.HealthProxyService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/proxy")
public class HealthProxyResource {

    @Inject
    @RestClient
    private HealthProxyService proxyService;

    @GET
    @Path("/liveness")
    public String liveness() {
        return proxyService.liveness();
    }

    @GET
    @Path("/readiness")
    public String readiness() {
        return proxyService.readiness();
    }

}
