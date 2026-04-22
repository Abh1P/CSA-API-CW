package com.smartcampus.store;

import com.smartcampus.model.Room;
import com.smartcampus.model.Sensor;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    private static DataStore instance = new DataStore();

    private Map<String, Room> rooms = new HashMap<>();
    private Map<String, Sensor> sensors = new HashMap<>();

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
}