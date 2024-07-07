package com.exam;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.exam.service.EventProxyService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/proxy")
@RolesAllowed({"quarkus-exam-role"})
public class ConsumerProxyResource {

    @Inject
    @RestClient
    private EventProxyService proxyService;

    @GET
    @Path("/cache/{key}")
    public String getCacheByKey(@PathParam(value = "key") String key) {
        return proxyService.getCacheByKey(key);
    }

    @DELETE
    @Path("/cache/{key}")
    public String deleteCacheByKey(@PathParam(value = "key") String key) {
        return proxyService.deleteCacheByKey(key);
    }

    @GET
    @Path("/db/{key}")
    public String getDbByKey(@PathParam(value = "key") String key) {
        return proxyService.getDbByKey(key);
    }

    @DELETE
    @Path("/db/{key}")
    public String deleteDbByKey(@PathParam(value = "key") String key) {
        return proxyService.deleteDbByKey(key);
    }

}
