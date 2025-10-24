package com.lru.cache.resources;

import com.lru.cache.model.CacheEntry;
import com.lru.cache.service.CacheService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/cache")
public class CacheResource {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCache() {
        return Response.ok(CacheService.getCache()).build();
    }

    @GET
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchKey(@PathParam("key") String key) {
        return Response.ok(CacheService.get(key)).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCache(CacheEntry entry) {
        CacheService.update(entry.getKey(), entry.getValue());
        return Response.status(Response.Status.CREATED).entity(entry).build();
    }

    @POST
    @Path("/clear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearCache() {
        CacheService.clear();
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCache(CacheEntry entry) {
        boolean status = CacheService.update(entry.getKey(), entry.getValue());
        if(! status) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.APPLICATION_JSON)
                    .entity("{\"error\":\"Key not found: "+entry.getKey()+"\"}")
                    .build();
        }
        return Response.accepted(entry).build();
    }

    @DELETE
    @Path("/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteKey(@PathParam("key") String key) {
        System.out.println("this is the key: " + key);
        CacheService.delete(key);
        return Response.noContent().build();
    }
}