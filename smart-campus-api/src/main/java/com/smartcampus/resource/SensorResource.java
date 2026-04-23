package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sensors")
public class SensorResource {

    private DataStore store = DataStore.getInstance();

    // CREATE SENSOR
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {

        if (sensor == null || sensor.getId() == null || sensor.getRoomId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid sensor")
                    .build();
        }

        store.getSensors().put(sensor.getId(), sensor);

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    // GET SENSOR
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensor(@PathParam("id") String id) {

        Sensor sensor = store.getSensors().get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok(sensor).build();
    }

    // GET ALL SENSORS (with optional filtering)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors(@QueryParam("type") String type) {

        java.util.List<Sensor> sensors = new java.util.ArrayList<>(store.getSensors().values());

        // If no filter provided, return all sensors
        if (type == null || type.isEmpty()) {
            return Response.ok(sensors).build();
        }

        // Filter sensors by type
        java.util.List<Sensor> filtered = sensors.stream()
                .filter(s -> type.equalsIgnoreCase(s.getType()))
                .collect(java.util.stream.Collectors.toList());

        return Response.ok(filtered).build();
    }

    // DELETE SENSOR
    @DELETE
    @Path("/{id}")
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = store.getSensors().remove(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok("Sensor deleted").build();
    }

    // SUB-RESOURCE
    @Path("/{id}/readings")
    public SensorReadingResource getSensorReadings(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }
}