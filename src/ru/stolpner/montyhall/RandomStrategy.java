package ru.stolpner.montyhall;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    @Override
    public int chooseDoor(List<Door> doors) {
        List<Door> doorsToChooseFrom = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        int chosenDoor = random.nextInt(doorsToChooseFrom.size());
        return doorsToChooseFrom.get(chosenDoor).getNumber();
    }
}
