package exceptions;

public class VersionNotSupportException extends Exception {
    public VersionNotSupportException() {
    }

    public VersionNotSupportException(String message) {
        super(message);
    }
}
