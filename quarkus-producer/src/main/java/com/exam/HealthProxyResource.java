package com.exam;

import org.eclipse.microprofile.openapi.annotations.Operation;
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

    @Operation(summary = "Liveness proxy service", description = "Return liveness of proxy micro service")
    @GET
    @Path("/liveness")
    public String liveness() {
        return proxyService.liveness();
    }

    @Operation(summary = "Readiness proxy service", description = "Return readiness of proxy micro service")
    @GET
    @Path("/readiness")
    public String readiness() {
        return proxyService.readiness();
    }

}
