package com.cinus.log;

public enum LogLevel {
    ALL("[]", 0), DEBUG("[INFO]", 1), INFO("[INFO]", 2), WARN("[WARN]", 3), ERROR("[ERROR]", 4);

    private String name;
    private int level;

    LogLevel(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isAllow(LogLevel minLevel) {
        return level >= minLevel.getLevel();
    }
}