package by.urbel.hotel.service.exception;

import java.util.ArrayList;
import java.util.List;

public class ServiceException extends Exception{
    private List<Exception> errors= new ArrayList<>();

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }

    public ServiceException(String message, List<Exception> e) {
        super(message);
        errors = e;
    }

    public List<Exception> getErrors() {
        return errors;
    }
}
