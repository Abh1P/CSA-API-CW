package com.smartcampus.resource;

import com.smartcampus.exception.LinkedResourceNotFoundException;
import com.smartcampus.exception.RoomNotEmptyException;
import com.smartcampus.model.Room;
import com.smartcampus.repository.Database;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    // Access the static map from Database class
    private Map<String, Room> rooms = Database.getRooms();

    /**
     * GET /api/v1/rooms
     * Lists all rooms.
     */
    @GET
    public Response getAllRooms() {
        // Convert map values to a list and return it
        List<Room> roomList = new ArrayList<>(rooms.values());
        return Response.ok(roomList).build();
    }

    /**
     * GET /api/v1/rooms/{id}
     * Retrieves a specific room by ID.
     */
    @GET
    @Path("/{id}")
    public Response getRoom(@PathParam("id") String id) {
        Room room = rooms.get(id);
        if (room == null) {
            throw new LinkedResourceNotFoundException("Room with id " + id + " not found.");
        }
        return Response.ok(room).build();
    }

    /**
     * POST /api/v1/rooms
     * Creates a new room.
     */
    @POST
    public Response createRoom(Room room, @Context UriInfo uriInfo) {
        // Generate a simple ID for the new room
        String newId = UUID.randomUUID().toString();
        room.setId(newId);
        
        // Save the room to the "database"
        rooms.put(newId, room);
        
        // Build the URI for the newly created resource
        URI location = uriInfo.getAbsolutePathBuilder().path(newId).build();
        
        // Return 201 Created with the new room and the location header
        return Response.created(location).entity(room).build();
    }

    /**
     * DELETE /api/v1/rooms/{id}
     * Deletes a specific room by ID. Fails if the room has sensors.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id) {
        Room room = rooms.get(id);
        if (room == null) {
            throw new LinkedResourceNotFoundException("Room with id " + id + " not found.");
        }
        
        // Business rule: MUST fail if room has sensors
        if (!room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException("Cannot delete room " + id + " because it contains sensors.");
        }
        
        // Remove room
        rooms.remove(id);
        
        // Return 204 No Content
        return Response.noContent().build();
    }
}
