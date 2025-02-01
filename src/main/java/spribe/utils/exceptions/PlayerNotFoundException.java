package spribe.utils.exceptions;

public class PlayerNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Could not find player";
    
    public PlayerNotFoundException() {
        this(EXCEPTION_MESSAGE);
    }
    
    public PlayerNotFoundException(final String message) {
        super(message);
    }
    
    public PlayerNotFoundException(final String fieldName, final String fieldValue) {
        super(String.format(EXCEPTION_MESSAGE + " with %s: '%s'", fieldName, fieldValue));
    }
}
