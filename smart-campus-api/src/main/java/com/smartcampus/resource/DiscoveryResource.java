package com.smartcampus.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApiInfo(@Context UriInfo uriInfo) {
        String baseUri = uriInfo.getBaseUri().toString();
        
        Map<String, Object> apiInfo = new HashMap<>();
        apiInfo.put("name", "Smart Campus API");
        apiInfo.put("version", "v1");
        
        Map<String, String> links = new HashMap<>();
        links.put("rooms", baseUri + "rooms");
        links.put("sensors", baseUri + "sensors");
        apiInfo.put("links", links);

        return Response.ok(apiInfo).build();
    }
}
