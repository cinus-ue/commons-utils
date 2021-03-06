package com.cinus.rule.drools;

import com.cinus.rule.Weather;
import org.junit.Assert;
import org.junit.Test;

public class DroolsTest {


    @Test
    public void test_drools() {
        Weather weather = new Weather();
        weather.setRain(false);
        WeatherRule.run(weather);
        Assert.assertTrue(null == weather.getMessage());

        weather.setRain(true);
        WeatherRule.run(weather);
        Assert.assertEquals("It rains, take an umbrella!", weather.getMessage());
    }
}
