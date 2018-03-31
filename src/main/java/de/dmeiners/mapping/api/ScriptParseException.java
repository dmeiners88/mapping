package de.dmeiners.mapping.api;

public class ScriptParseException extends RuntimeException {

    public ScriptParseException() {
        super();
    }

    public ScriptParseException(String message) {
        super(message);
    }

    public ScriptParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptParseException(Throwable cause) {
        super(cause);
    }

    protected ScriptParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
