package by.urbel.hotel.entity;

import java.util.Objects;

public class Room extends Entity{
    private int roomNumber;
    private RoomCategory category;
    private RoomStatus status;

    public Room() {
    }

    public Room(int roomNumber, RoomCategory category, RoomStatus status) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.status = status;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber && Objects.equals(category, room.category) && status == room.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, category, status);
    }
}
