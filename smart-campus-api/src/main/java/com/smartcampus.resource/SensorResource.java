package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

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

        if (!store.getRooms().containsKey(sensor.getRoomId())) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        store.getRooms().get(sensor.getRoomId()).getSensorIds().add(sensor.getId());
        store.getSensors().put(sensor.getId(), sensor);

        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    // GET ALL SENSORS
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors() {
        return Response.ok(new ArrayList<>(store.getSensors().values())).build();
    }

    // GET SENSOR BY ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensorById(@PathParam("id") String id) {

        Sensor sensor = store.getSensors().get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        return Response.ok(sensor).build();
    }

    // DELETE SENSOR
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteSensor(@PathParam("id") String id) {

        Sensor sensor = store.getSensors().get(id);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        String roomId = sensor.getRoomId();

        if (roomId != null && store.getRooms().containsKey(roomId)) {
            store.getRooms().get(roomId).getSensorIds().remove(id);
        }

        store.getSensors().remove(id);

        return Response.ok("Sensor deleted").build();
    }
}