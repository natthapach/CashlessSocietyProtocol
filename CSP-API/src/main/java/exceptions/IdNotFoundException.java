package exceptions;

public class IdNotFoundException extends Exception {
    public IdNotFoundException() {
    }

    public IdNotFoundException(String message) {
        super(message);
    }
}
