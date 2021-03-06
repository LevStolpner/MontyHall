package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        List<Door> closedDoors = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        int chosenDoor = random.nextInt(closedDoors.size());
        return closedDoors.get(chosenDoor).getNumber();
    }
}
