package com.exam;

import org.eclipse.microprofile.openapi.annotations.Operation;
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

    @Operation(
        summary = "Get Event by key",
        description = "Get an Event from Cache by key"
    )
    @GET
    @Path("/cache/{key}")
    public String getCacheByKey(@PathParam(value = "key") String key) {
        return proxyService.getCacheByKey(key);
    }

    @Operation(
        summary = "Delete Event by key",
        description = "Delete an Event from Cache by key"
    )
    @DELETE
    @Path("/cache/{key}")
    public String deleteCacheByKey(@PathParam(value = "key") String key) {
        return proxyService.deleteCacheByKey(key);
    }

    @Operation(
        summary = "Get Event by key",
        description = "Get an Event from DB by key"
    )
    @GET
    @Path("/db/{key}")
    public String getDbByKey(@PathParam(value = "key") String key) {
        return proxyService.getDbByKey(key);
    }

    @Operation(
        summary = "Count Event by key",
        description = "Return the number of DB event by key"
    )
    @GET
    @Path("/db/{key}/count")
    public String countDbByKey(@PathParam(value = "key") String key) {
        return proxyService.countDbByKey(key);
    }

    @Operation(
        summary = "Delete Event by key",
        description = "Delete an Event from Db by key"
    )
    @DELETE
    @Path("/db/{key}")
    public String deleteDbByKey(@PathParam(value = "key") String key) {
        return proxyService.deleteDbByKey(key);
    }

}
