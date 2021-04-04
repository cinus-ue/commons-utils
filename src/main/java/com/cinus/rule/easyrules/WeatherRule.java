package com.cinus.rule.easyrules;

import com.cinus.rule.Weather;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

@Rule(name = "weather rule", description = "if it rains then take an umbrella")
public class WeatherRule {

    @Condition
    public boolean itRains(@Fact("weather") Weather weather) {
        return weather.isRain();
    }

    @Action
    public void takeAnUmbrella(@Fact("weather") Weather weather) {
        weather.setMessage("It rains, take an umbrella!");
    }
}