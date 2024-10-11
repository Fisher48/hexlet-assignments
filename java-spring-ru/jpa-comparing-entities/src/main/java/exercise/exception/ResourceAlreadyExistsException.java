package exercise.exception;

// BEGIN
public class ResourceAlreadyExistsException extends RuntimeException {
    ResourceAlreadyExistsException(String message) {
        super(message);
    }
}

// END
