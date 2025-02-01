package spribe.utils.exceptions;

public class ServiceNotReachableException extends RuntimeException {
    
    private static final String EXCEPTION_MESSAGE = "Service is not reachable. Aborting test execution.";
    
    public ServiceNotReachableException() {
        this(EXCEPTION_MESSAGE);
    }

    public ServiceNotReachableException(final String message) {
        super(message);
    }
}
