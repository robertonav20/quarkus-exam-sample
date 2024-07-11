package com.exam.service;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@Path("/event")
@RegisterClientHeaders
@RegisterRestClient(configKey = "proxy-event-consumer-api")
public interface EventProxyService {

    @GET
    @Path("/cache/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCacheByKey(@PathParam(value = "key") String key);


    @DELETE
    @Path("/cache/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCacheByKey(@PathParam(value = "key") String key);

    @GET
    @Path("/db/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDbByKey(@PathParam(value = "key") String key);

    @GET
    @Path("/db/{key}/count")
    @Produces(MediaType.APPLICATION_JSON)
    public String countDbByKey(@PathParam(value = "key") String key);

    @DELETE
    @Path("/db/{key}")
    public String deleteDbByKey(@PathParam(value = "key") String key);
}
