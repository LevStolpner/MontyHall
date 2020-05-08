package ru.stolpner.montyhall;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChangingStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        List<Door> doorsToChooseFrom = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        if (doorsToChooseFrom.size() == 1) {
            return doorsToChooseFrom.get(0).getNumber();
        }

        int chosenDoor = random.nextInt(doorsToChooseFrom.size());
        if (lastChosenDoor == null) {
            return doorsToChooseFrom.get(chosenDoor).getNumber();
        }

        while (lastChosenDoor.equals(doorsToChooseFrom.get(chosenDoor).getNumber())) {
            chosenDoor = random.nextInt(doorsToChooseFrom.size());
        }

        return doorsToChooseFrom.get(chosenDoor).getNumber();
    }
}
