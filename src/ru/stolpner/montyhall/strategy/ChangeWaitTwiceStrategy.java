package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChangeWaitTwiceStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    private int waitCounter = 2;

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        List<Door> closedDoors = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        if (closedDoors.size() == 1) {
            return closedDoors.get(0).getNumber();
        }

        if (waitCounter == 2) {
            int chosenDoor = random.nextInt(closedDoors.size());
            if (lastChosenDoor == null) {
                return closedDoors.get(chosenDoor).getNumber();
            }

            while (lastChosenDoor.equals(closedDoors.get(chosenDoor).getNumber())) {
                chosenDoor = random.nextInt(closedDoors.size());
            }

            waitCounter = 0;
            return closedDoors.get(chosenDoor).getNumber();
        }

        waitCounter += 1;
        return lastChosenDoor;
    }
}
