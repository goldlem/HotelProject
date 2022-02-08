package by.urbel.hotel.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Reservation extends Entity {
    private int reservationId;
    private User user;
    private BigDecimal totalCost;
    private Date bookingDate;
    private Date checkInDate;
    private Date checkOutDate;
    private Room room;

    public Reservation() {
    }

    public Reservation(User user, BigDecimal totalCost, Date bookingDate, Date checkInDate,
                       Date checkOutDate, Room room) {
        this.user = user;
        this.totalCost = totalCost;
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    public Reservation(int reservationId, User user, BigDecimal totalCost, Date bookingDate, Date checkInDate,
                       Date checkOutDate, Room room) {
        this.reservationId = reservationId;
        this.user = user;
        this.totalCost = totalCost;
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return reservationId == that.reservationId && Objects.equals(user, that.user) && Objects.equals(totalCost, that.totalCost) && Objects.equals(bookingDate, that.bookingDate) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, user, totalCost, bookingDate, checkInDate, checkOutDate, room);
    }
}
