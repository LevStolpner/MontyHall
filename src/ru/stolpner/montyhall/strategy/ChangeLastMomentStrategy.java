package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChangeLastMomentStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        List<Door> closedDoors = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        if (closedDoors.size() == 1) {
            return closedDoors.get(0).getNumber();
        }

        if (closedDoors.size() == 2) {
            return closedDoors.stream()
                    .filter(door -> !lastChosenDoor.equals(door.getNumber()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No door found"))
                    .getNumber();
        }

        if (lastChosenDoor != null) {
            return lastChosenDoor;
        }

        return random.nextInt(closedDoors.size());
    }
}
