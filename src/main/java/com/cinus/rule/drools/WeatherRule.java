package com.cinus.rule.drools;

import com.cinus.rule.Weather;

public class WeatherRule {

    public static void run(Weather weather) {
        new RuleRunner().runRules(new String[]{"src/main/resources/com/cinus/drl/weather.drl"},
                new Object[]{weather});
    }
}
