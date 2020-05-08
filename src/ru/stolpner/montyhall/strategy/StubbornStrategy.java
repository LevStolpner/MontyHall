package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StubbornStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    private Integer selectedDoor;

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        if (selectedDoor != null) {
            return selectedDoor;
        }

        List<Door> doorsToChooseFrom = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        int chosenDoor = random.nextInt(doorsToChooseFrom.size());
        selectedDoor = doorsToChooseFrom.get(chosenDoor).getNumber();
        return selectedDoor;
    }
}
