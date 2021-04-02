package com.cinus.xml;


import com.cinus.reflect.sample.TestObject;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class XmlUtilsTest {
    @Test
    public void test_toXml() throws Exception {
        List<TestObject> testList = new LinkedList<>();
        TestObject a = new TestObject("a");
        TestObject b = new TestObject("b");
        testList.add(a);
        testList.add(b);
        String xmlStr = XmlUtils.toXml(testList);
        List<TestObject> list = XmlUtils.toObject(xmlStr, new TypeReference<List<TestObject>>() {
        });
        for (int i = 0; i < list.size(); i++) {
            TestObject object01 = list.get(i);
            TestObject object02 = testList.get(i);
            assertEquals(object01, object02);
        }
    }
}
