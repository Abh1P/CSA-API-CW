package com.smartcampus.resource;

import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import com.smartcampus.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class SensorReadingResource {

    private String sensorId;
    private DataStore store = DataStore.getInstance();

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // GET readings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReadings() {

        if (!store.getSensors().containsKey(sensorId)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        List<SensorReading> readings = store.getReadings().get(sensorId);

        if (readings == null) {
            readings = new ArrayList<>();
        }

        return Response.ok(readings).build();
    }

    // POST reading
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {

        Sensor sensor = store.getSensors().get(sensorId);

        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Sensor not found")
                    .build();
        }

        // ✅ SET VALUES
        reading.setSensorId(sensorId);
        reading.setTimestamp(System.currentTimeMillis());

        // ✅ UPDATE SENSOR VALUE
        sensor.setCurrentValue(reading.getValue());

        // ✅ STORE READING
        store.getReadings()
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}