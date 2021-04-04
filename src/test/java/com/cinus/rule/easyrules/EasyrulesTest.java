package com.cinus.rule.easyrules;

import com.cinus.rule.Weather;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.junit.Assert;
import org.junit.Test;

public class EasyrulesTest {


    @Test
    public void test_easyrules() {
        Weather weather = new Weather();
        weather.setRain(false);
        Facts facts = new Facts();
        facts.put("weather", weather);

        Rules rules = new Rules();
        rules.register(new WeatherRule());

        // fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        rulesEngine.fire(rules, facts);
        Assert.assertTrue(null == weather.getMessage());

        weather.setRain(true);
        rulesEngine.fire(rules, facts);
        Assert.assertEquals("It rains, take an umbrella!", weather.getMessage());
    }
}
