package com.exam;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.infinispan.client.hotrod.RemoteCache;

import com.exam.model.Event;
import com.exam.model.EventEntity;

import io.quarkus.infinispan.client.Remote;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/event")
@RolesAllowed({"quarkus-exam-role"})
public class EventResource {

    @Remote("${cache.name}")
    private RemoteCache<String, String> cache;

    @Operation(
        summary = "Get Event by key",
        description = "Get an Event from Cache by key"
    )
    @GET
    @Path("/cache/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(failureRatio = 1, successThreshold = 1)
    @Counted(name = "event-retrieved-cache-api",
            description = "Number of event retrieved cache via api")
    public Response retrieveCache(@PathParam(value = "key") String key) {
        return buildResponse(key, cache.get(key));
    }

    @Operation(
        summary = "Delete Event by key",
        description = "Delete an Event from Cache by key"
    )
    @DELETE
    @Path("/cache/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "event-deleted-cache-api",
            description = "Number of event deleted cache via api")
    public Response deleteCache(@PathParam(value = "key") String key) {
        return buildResponse(key, cache.remove(key));
    }

    @Operation(
        summary = "Get Event by key",
        description = "Get an Event from DB by key"
    )
    @GET
    @Path("/db/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(failureRatio = 1, successThreshold = 1)
    @Counted(name = "event-retrieved-db-api", description = "Number of event retrieved db via api")
    public Response retrieveDb(@PathParam(value = "key") String key) {
        return buildResponse(key, EventEntity.findByKey(key).getValue());
    }

    @Operation(
        summary = "Count Event by key",
        description = "Return the number of DB event by key"
    )
    @GET
    @Path("/db/{key}/count")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(failureRatio = 1, successThreshold = 1)
    @Counted(name = "event-retrieved-count-db-api", description = "Number of event retrieved count db via api")
    public Response countDb(@PathParam(value = "key") String key) {
        return Response.ok(EventEntity.countByKey(key)).build();
    }

    @Operation(
        summary = "Delete Event by key",
        description = "Delete an Event from Db by key"
    )
    @DELETE
    @Path("/db/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "event-deleted-db-api", description = "Number of event deleted db via api")
    @Transactional
    public void deleteDb(@PathParam(value = "key") String key) {
        EventEntity.deleteByKey(key);
    }

    private Response buildResponse(String key, String value) {
        if (value == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(Event.builder().key(key).value(value).build()).build();
    }
}
