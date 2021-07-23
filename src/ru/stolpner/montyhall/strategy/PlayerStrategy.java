package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;

/**
 * Interface for strategies
 */
public interface PlayerStrategy {
    int chooseDoor(List<Door> doors, Integer lastChosenDoor);
}
