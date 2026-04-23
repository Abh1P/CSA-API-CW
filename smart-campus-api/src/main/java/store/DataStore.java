package com.smartcampus.store;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {

    private static DataStore instance = new DataStore();

    private Map<String, Room> rooms = new HashMap<>();
    private Map<String, Sensor> sensors = new HashMap<>();
    private Map<String, List<SensorReading>> readings = new HashMap<>();

    private DataStore() {}

    public static DataStore getInstance() {
        return instance;
    }

    public Map<String, Room> getRooms() {
        return rooms;
    }

    public Map<String, Sensor> getSensors() {
        return sensors;
    }

    public Map<String, List<SensorReading>> getReadings() {
        return readings;
    }
}