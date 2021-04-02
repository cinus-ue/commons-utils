package com.cinus.expression;

import com.cinus.thirdparty.springframework.util.StopWatch;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExpressionUtilsTest {

    private StopWatch sw = new StopWatch("test_expression");

    @Test
    public void test_expression() {
        Map<String, Object> env = new HashMap<>();
        env.put("msg", "xxx message");
        String expression = "STR.endsWith(msg,'ge')";
        sw.start("task1");
        assertTrue((Boolean) ExpressionUtils.executeExpression(expression, env));
        sw.stop();

        env.clear();
        env.put("domain", "cinus");
        sw.start("task2");
        assertEquals("www.cinus.com", ExpressionUtils.executeTemplateExpression("www.@{domain}.com", env));
        sw.stop();
        System.out.println(sw.prettyPrint());
    }
}
