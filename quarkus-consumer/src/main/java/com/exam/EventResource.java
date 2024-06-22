package com.exam;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.metrics.annotation.Counted;
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

@Path("/event")
public class EventResource {

    @Remote("${cache.name}")
    private RemoteCache<String, String> cache;

    @GET
    @Path("/cache/{key}")
    @RolesAllowed({"quarkus-exam-role"})
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(failureRatio = 1, successThreshold = 1)
    @Counted(name = "event-retrieved-cache-api",
            description = "Number of event retrieved cache via api")
    public Event retrieveCache(@PathParam(value = "key") String key) {
        return Event.builder().key(key).value(cache.get(key)).build();
    }

    @DELETE
    @Path("/cache/{key}")
    @RolesAllowed({"quarkus-exam-role"})
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "event-deleted-cache-api",
            description = "Number of event deleted cache via api")
    public Event deleteCache(@PathParam(value = "key") String key) {
        return Event.builder().key(key).value(cache.remove(key)).build();
    }

    @GET
    @Path("/db/{key}")
    @RolesAllowed({"quarkus-exam-role"})
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(failureRatio = 1, successThreshold = 1)
    @Counted(name = "event-retrieved-db-api", description = "Number of event retrieved db via api")
    public Event retrieveDb(@PathParam(value = "key") String key) {
        return Event.builder().key(key).value(EventEntity.findByKey(key).getValue()).build();
    }

    @DELETE
    @Path("/db/{key}")
    @RolesAllowed({"quarkus-exam-role"})
    @Produces(MediaType.APPLICATION_JSON)
    @Counted(name = "event-deleted-db-api", description = "Number of event deleted db via api")
    @Transactional
    public void deleteDb(@PathParam(value = "key") String key) {
        EventEntity.deleteByKey(key);
    }
}
