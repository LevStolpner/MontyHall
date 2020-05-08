package ru.stolpner.montyhall;

public enum PlayerStrategyType {
    RANDOM("Random"),
    STUBBORN("Stubborn"),
    DYNAMIC_TWO_DOOR("DynamicTwoDoor"),
    STUBBORN_TWO_DOOR("StubbornTwoDoor"),
    RANDOM_TWO_DOOR("RandomTwoDoor")
    ;

    private final String name;
    PlayerStrategyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
