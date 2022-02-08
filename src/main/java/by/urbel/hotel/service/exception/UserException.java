package by.urbel.hotel.service.exception;

public class UserException extends ServiceException {
    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Exception e) {
        super(e);
    }

    public UserException(String message, Exception e) {
        super(message, e);
    }
}
