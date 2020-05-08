package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChangeWaitStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    private boolean changeFlag = true;

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        List<Door> closedDoors = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        if (closedDoors.size() == 1) {
            return closedDoors.get(0).getNumber();
        }

        if (changeFlag) {
            int chosenDoor = random.nextInt(closedDoors.size());
            if (lastChosenDoor == null) {
                return closedDoors.get(chosenDoor).getNumber();
            }

            while (lastChosenDoor.equals(closedDoors.get(chosenDoor).getNumber())) {
                chosenDoor = random.nextInt(closedDoors.size());
            }
            return closedDoors.get(chosenDoor).getNumber();
        }

        return lastChosenDoor;
    }
}
