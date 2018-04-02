package de.dmeiners.mapping.impl.jexl;

import org.apache.commons.jexl3.introspection.JexlSandbox;

public class JexlSandboxFactory {

    private JexlSandboxFactory() {
    }

    public static JexlSandbox create() {

        JexlSandbox sandbox = new JexlSandbox();
        sandbox.black(System.class.getName()).execute("exit");
        sandbox.black(Runtime.class.getName()).execute("exit");
        sandbox.black(Class.class.getName()).execute("forName");
        sandbox.black(Byte.class.getName()).execute("forName");
        sandbox.black(Short.class.getName()).execute("forName");
        sandbox.black(Integer.class.getName()).execute("forName");
        sandbox.black(Long.class.getName()).execute("forName");
        sandbox.black(Float.class.getName()).execute("forName");
        sandbox.black(Double.class.getName()).execute("forName");
        sandbox.black(Boolean.class.getName()).execute("forName");
        sandbox.black(Character.class.getName()).execute("forName");

        return sandbox;
    }
}
