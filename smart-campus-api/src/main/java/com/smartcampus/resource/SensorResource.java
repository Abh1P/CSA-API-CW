package com.smartcampus.resource;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.model.Sensor;
import com.smartcampus.repository.Database;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    private Map<String, Sensor> sensors = Database.getSensors();

    /**
     * Sub-resource locator for SensorReadings
     * Delegates requests matching /sensors/{id}/readings to SensorReadingResource
     */
    @Path("/{id}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("id") String sensorId) {
        // You should probably check if the sensor exists first
        if (!sensors.containsKey(sensorId)) {
            throw new LinkedResourceNotFoundException("Sensor with id " + sensorId + " not found.");
        }
        // Delegate to the sub-resource
        return new SensorReadingResource(sensorId);
    }

    /**
     * GET /api/v1/sensors
     * Supports optional query parameter: ?type=CO2
     */
    @GET
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> resultList = new ArrayList<>();
        
        // TODO: Implement logic to iterate through sensors and filter by type if provided
        
        return Response.ok(resultList).build();
    }

    /**
     * POST /api/v1/sensors
     * Creates a new sensor. MUST validate that the room exists.
     */
    @POST
    public Response createSensor(Sensor sensor, @Context UriInfo uriInfo) {
        // TODO: Implement sensor creation logic
        // 1. Generate ID
        // 2. Validate Room exists using Database.getRooms()
        // 3. Add to Database.getSensors()
        // 4. Update the Room's sensorIds list
        // 5. Return 201 Created with Location header
        
        return Response.serverError().entity("Not implemented yet").build();
    }
}
