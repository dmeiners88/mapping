package de.dmeiners.mapping.api;

public class NameResolutionException extends RuntimeException {

    public NameResolutionException() {
        super();
    }

    public NameResolutionException(String message) {
        super(message);
    }

    public NameResolutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameResolutionException(Throwable cause) {
        super(cause);
    }

    protected NameResolutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
