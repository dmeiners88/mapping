package de.dmeiners.mapping.impl.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;

public class Slf4jDiagnosticListener implements DiagnosticListener<JavaFileObject> {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jDiagnosticListener.class);

    @Override
    public void report(Diagnostic<? extends JavaFileObject> d) {

        String msg = "Code: {}, Position: {}, Start Position: {}, End Position: {}, Source: {}, Message: '{}'";

        switch (d.getKind()) {

            case NOTE:
            case OTHER:
                logger.trace(msg, d.getCode(), d.getPosition(), d.getStartPosition(), d.getEndPosition(), d.getSource(), d.getMessage(null));
                break;
            case WARNING:
            case MANDATORY_WARNING:
                logger.warn(msg, d.getCode(), d.getPosition(), d.getStartPosition(), d.getEndPosition(), d.getSource(), d.getMessage(null));
                break;
            case ERROR:
                logger.error(msg, d.getCode(), d.getPosition(), d.getStartPosition(), d.getEndPosition(), d.getSource(), d.getMessage(null));
                break;
        }
    }
}
