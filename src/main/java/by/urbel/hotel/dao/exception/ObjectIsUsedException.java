package by.urbel.hotel.dao.exception;

public class ObjectIsUsedException extends RuntimeException{
    public ObjectIsUsedException() {
    }

    public ObjectIsUsedException(String message) {
        super(message);
    }

    public ObjectIsUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectIsUsedException(Throwable cause) {
        super(cause);
    }
}
