package ru.stolpner.montyhall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChangingWithRandomStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    private List<Integer> doorsChosenBefore = new ArrayList<>();

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        List<Door> closedDoors = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        if (closedDoors.size() == 1) {
            return closedDoors.get(0).getNumber();
        }

        List<Door> doorsNotChosenBefore = closedDoors.stream()
                .filter(door -> !doorsChosenBefore.contains(door.getNumber()))
                .collect(Collectors.toList());

        if (doorsNotChosenBefore.isEmpty()) {
            int chosenDoor = random.nextInt(closedDoors.size());
            return closedDoors.get(chosenDoor).getNumber();
        }

        int chosenDoor = random.nextInt(doorsNotChosenBefore.size());
        int chosenDoorNumber = doorsNotChosenBefore.get(chosenDoor).getNumber();
        doorsChosenBefore.add(chosenDoorNumber);
        return chosenDoorNumber;
    }
}
