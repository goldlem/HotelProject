package by.urbel.hotel.service.validation;

import java.util.Date;

public class ReservationValidator {
    public static boolean isValidOrderDates(Date checkInDate, Date checkOutDate) {
        return checkInDate.before(checkOutDate) || checkInDate.equals(checkOutDate);
    }
}
