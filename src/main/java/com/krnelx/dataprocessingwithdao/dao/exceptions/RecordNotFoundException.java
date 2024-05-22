package main.java.com.krnelx.dataprocessingwithdao.dao.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String message) {
        super(message);
    }
}