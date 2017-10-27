/**
 * Exception thrown when a method or class is not provided with sufficient data to work.
 */
public class InsufficientDataException extends RuntimeException {
    //Default Throwable constructors

    public InsufficientDataException() {
    }

    public InsufficientDataException(String message) {
        super(message);
    }

    public InsufficientDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientDataException(Throwable cause) {
        super(cause);
    }

    public InsufficientDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
