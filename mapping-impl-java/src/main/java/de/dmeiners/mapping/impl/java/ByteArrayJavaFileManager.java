package de.dmeiners.mapping.impl.java;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ByteArrayJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private List<ByteArrayJavaFileObject> fileObjects = new ArrayList<>();

    public ByteArrayJavaFileManager(StandardJavaFileManager fileManager) {
        super(fileManager);
    }

    public List<ByteArrayJavaFileObject> getFileObjects() {
        return fileObjects;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {

        ByteArrayJavaFileObject file = new ByteArrayJavaFileObject(toUri(className));
        fileObjects.add(file);
        return file;
    }

    private static URI toUri(String path) {
        try {
            return new URI(null, null, path, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException("exception parsing uri", e);
        }
    }
}
