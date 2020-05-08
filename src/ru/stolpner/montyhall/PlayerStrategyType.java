package ru.stolpner.montyhall;

public enum PlayerStrategyType {
    RANDOM("Random"),
    RANDOM_TWO_DOOR("RandomTwoDoor"),
    STUBBORN("Stubborn"),
    STUBBORN_TWO_DOOR("StubbornTwoDoor"),
    DYNAMIC_TWO_DOOR("DynamicTwoDoor"),
    CHANGING("Changing"),
    CHANGING_WITH_RANDOM("ChangingWithRandom"),
    FULL_CHANGING("FullChanging"),
//    DYNAMIC_THREE_DOOR("DynamicThreeDoor")
    ;

    private final String name;
    PlayerStrategyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
