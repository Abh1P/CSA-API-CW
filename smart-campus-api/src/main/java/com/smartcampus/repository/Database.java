package com.smartcampus.repository;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An in-memory database mock using HashMaps.
 */
public class Database {
    private static Map<String, Room> rooms = new HashMap<>();
    private static Map<String, Sensor> sensors = new HashMap<>();
    // A map from sensorId to a list of readings
    private static Map<String, List<SensorReading>> sensorReadings = new HashMap<>();

    // Static block to initialize with some dummy data
    static {
        Room r1 = new Room("r1", "Lecture Theater 1", 200);
        Room r2 = new Room("r2", "Computer Lab A", 50);
        rooms.put(r1.getId(), r1);
        rooms.put(r2.getId(), r2);

        Sensor s1 = new Sensor("s1", "Temperature", Sensor.Status.ACTIVE, "r1");
        Sensor s2 = new Sensor("s2", "CO2", Sensor.Status.MAINTENANCE, "r2");
        sensors.put(s1.getId(), s1);
        sensors.put(s2.getId(), s2);
        
        r1.addSensorId("s1");
        r2.addSensorId("s2");

        sensorReadings.put("s1", new ArrayList<>());
        sensorReadings.put("s2", new ArrayList<>());
        
        sensorReadings.get("s1").add(new SensorReading("rdg1", System.currentTimeMillis(), 21.5));
        s1.setCurrentValue(21.5);
    }

    public static Map<String, Room> getRooms() {
        return rooms;
    }

    public static Map<String, Sensor> getSensors() {
        return sensors;
    }

    public static Map<String, List<SensorReading>> getSensorReadings() {
        return sensorReadings;
    }
}
