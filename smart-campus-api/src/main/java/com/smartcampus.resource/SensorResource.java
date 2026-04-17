package com.smartcampus.resource;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.model.Sensor;
import com.smartcampus.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/sensors")
public class SensorResource {

    private DataStore store = DataStore.getInstance();

    // POST /api/v1/sensors - create a sensor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {
        if (sensor.getId() == null || sensor.getRoomId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Sensor id and roomId are required")
                    .build();
        }
        // Check if room exists
        if (!store.getRooms().containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException("Room " + sensor.getRoomId() + " does not exist");
        }
        // Add sensor to room's sensor list
        store.getRooms().get(sensor.getRoomId()).getSensorIds().add(sensor.getId());
        store.getSensors().put(sensor.getId(), sensor);
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    // GET /api/v1/sensors - get all sensors, optional filter by type
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSensors(@QueryParam("type") String type) {
        List<Sensor> sensors = new ArrayList<>(store.getSensors().values());
        if (type != null) {
            List<Sensor> filtered = new ArrayList<>();
            for (Sensor s : sensors) {
                if (s.getType() != null && s.getType().equalsIgnoreCase(type)) {
                    filtered.add(s);
                }
            }
            return Response.ok(filtered).build();
        }
        return Response.ok(sensors).build();
    }

    // DELETE /api/v1/sensors/{id} - delete a sensor
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteSensor(@PathParam("id") String id) {
        try {
            Sensor sensor = store.getSensors().get(id);

            if (sensor == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Sensor not found")
                        .build();
            }

            String roomId = sensor.getRoomId();

            if (roomId != null && store.getRooms().containsKey(roomId)) {
                if (store.getRooms().get(roomId) != null &&
                        store.getRooms().get(roomId).getSensorIds() != null) {

                    store.getRooms().get(roomId).getSensorIds().remove(id);
                }
            }

            store.getSensors().remove(id);

            return Response.ok("Sensor deleted").build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Delete failed")
                    .build();
        }
    }

    // Sub-resource locator for readings
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}