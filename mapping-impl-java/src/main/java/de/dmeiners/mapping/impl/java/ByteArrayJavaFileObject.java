package de.dmeiners.mapping.impl.java;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class ByteArrayJavaFileObject extends SimpleJavaFileObject {

    private ByteArrayOutputStream outputStream;

    public ByteArrayJavaFileObject(URI uri) {
        super(uri, Kind.CLASS);
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public String getName() {
        return uri.getRawSchemeSpecificPart();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {

        return outputStream = new ByteArrayOutputStream();
    }
}
