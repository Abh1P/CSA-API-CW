package com.smartcampus.resource;

import com.smartcampus.model.SensorReading;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    // Constructor to receive the parent sensor's ID
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * GET /api/v1/sensors/{id}/readings
     */
    @GET
    public Response getReadings() {
        // TODO: Implement retrieving readings for this.sensorId from Database
        return Response.serverError().entity("Not implemented yet").build();
    }

    /**
     * POST /api/v1/sensors/{id}/readings
     */
    @POST
    public Response addReading(SensorReading reading, @Context UriInfo uriInfo) {
        // TODO: Implement adding a reading
        // 1. Generate ID and Timestamp
        // 2. Add reading to Database.getSensorReadings()
        // 3. Update the Sensor's currentValue
        // 4. Return 201 Created
        return Response.serverError().entity("Not implemented yet").build();
    }
}
