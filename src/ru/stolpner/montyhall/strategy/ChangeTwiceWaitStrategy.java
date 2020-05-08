package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChangeTwiceWaitStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    private int changeCounter = 0;

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        List<Door> closedDoors = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        if (closedDoors.size() == 1) {
            return closedDoors.get(0).getNumber();
        }

        if (changeCounter == 2) {
            changeCounter = 0;
            return lastChosenDoor;
        }

        int chosenDoor = random.nextInt(closedDoors.size());
        if (lastChosenDoor == null) {
            return closedDoors.get(chosenDoor).getNumber();
        }

        while (lastChosenDoor.equals(closedDoors.get(chosenDoor).getNumber())) {
            chosenDoor = random.nextInt(closedDoors.size());
        }

        changeCounter += 1;
        return closedDoors.get(chosenDoor).getNumber();
    }
}
