package com.cinus.expression;

import com.cinus.expression.expr.str;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.templates.TemplateRuntime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ExpressionUtils {

    private static Map<String, Serializable> EXPRESSION_CACHE = new HashMap<>();
    private static final ParserContext PCTX;

    static {
        PCTX = new ParserContext();
        PCTX.addImport("STR", str.class);
    }

    public static Object executeExpression(String expr, Map<String, Object> vars) {
        Serializable expression = EXPRESSION_CACHE.get(expr);
        if (expression == null) {
            expression = MVEL.compileExpression(expr, PCTX);
            EXPRESSION_CACHE.put(expr, expression);
        }
        return MVEL.executeExpression(expression, vars);
    }

    public static Object executeTemplateExpression(String expr, Map<String, Object> vars) {
        return TemplateRuntime.eval(expr, vars);
    }
}
