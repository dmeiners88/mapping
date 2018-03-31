package de.dmeiners.mapping.api;

public class ScriptExecutionException extends RuntimeException {

    public ScriptExecutionException() {
        super();
    }

    public ScriptExecutionException(String message) {
        super(message);
    }

    public ScriptExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptExecutionException(Throwable cause) {
        super(cause);
    }

    protected ScriptExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
