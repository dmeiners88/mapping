package de.dmeiners.mapping.impl.mvel;

import org.mvel2.ast.ASTNode;
import org.mvel2.integration.Interceptor;
import org.mvel2.integration.VariableResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(ClassInterceptor.class);

    @Override
    public int doBefore(ASTNode node, VariableResolverFactory factory) {

        logger.debug("Before '{}'", node.getAbsoluteName());

        return 0;
    }

    @Override
    public int doAfter(Object exitStackValue, ASTNode node, VariableResolverFactory factory) {

        logger.debug("After '{}'", node.getAbsoluteName());

        return 0;
    }
}
