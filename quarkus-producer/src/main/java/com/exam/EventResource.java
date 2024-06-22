package com.exam;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import com.exam.model.Event;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/event")
public class EventResource {

    @Channel("events")
    Emitter<Event> eventEmitter;

    @POST
    @Path("/produce")
    @RolesAllowed({"quarkus-exam-role"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(
        name = "event-produced-api",
        description = "Number of event produced via api"
    )
    public void produce(Event event) {
        eventEmitter.send(event);
    }
}
