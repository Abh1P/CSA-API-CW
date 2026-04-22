package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.store.DataStore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/rooms")
public class RoomResource {

    private DataStore store = DataStore.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room) {

        if (room == null || room.getId() == null || room.getName() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid room")
                    .build();
        }

        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }

        store.getRooms().put(room.getId(), room);

        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRooms() {
        return Response.ok(store.getRooms().values()).build();
    }

    // PUT /api/v1/rooms/{id}3
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoom(@PathParam("id") String id, Room updatedRoom) {

        Room existingRoom = store.getRooms().get(id);

        if (existingRoom == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Room not found")
                    .build();
        }

        if (updatedRoom.getName() != null) {
            existingRoom.setName(updatedRoom.getName());
        }

        if (updatedRoom.getCapacity() > 0) {
            existingRoom.setCapacity(updatedRoom.getCapacity());
        }

        return Response.ok(existingRoom).build();
    }
}
