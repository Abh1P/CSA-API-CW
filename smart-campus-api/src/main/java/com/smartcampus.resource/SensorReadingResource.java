// REPLACE your entire SensorReadingResource.java with this SIMPLE VERSION

package com.smartcampus.resource;

import javax.ws.rs.Path;

@Path("/readings")
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // TEMP: leave empty so project compiles
}
