package ru.stolpner.montyhall.strategy;

import ru.stolpner.montyhall.Door;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomTwoDoorStrategy implements PlayerStrategy {

    private static final Random random = new Random();

    private Integer firstSelectedDoor = null;
    private Integer secondSelectedDoor = null;

    @Override
    public int chooseDoor(List<Door> doors, Integer lastChosenDoor) {
        if (firstSelectedDoor == null) {
            //first step in game, select 2 random doors and try to stick to them
            int firstChosenDoor = random.nextInt(doors.size());

            //making sure we picked two different doors
            int secondChosenDoor = random.nextInt(doors.size());
            while (firstChosenDoor == secondChosenDoor) {
                secondChosenDoor = random.nextInt(doors.size());
            }

            firstSelectedDoor = doors.get(firstChosenDoor).getNumber();
            secondSelectedDoor = doors.get(secondChosenDoor).getNumber();

            return firstSelectedDoor;
        }

        List<Door> closedDoors = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());
        if (closedDoors.isEmpty()) {
            throw new IllegalStateException("No closed doors to choose from!");
        }
        if (closedDoors.size() == 1) {
            return closedDoors.get(0).getNumber();
        }

        if (lastChosenDoor.equals(firstSelectedDoor)) {
            Door secondDoor = doors.stream()
                    .filter(door -> secondSelectedDoor.equals(door.getNumber()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No door found"));

            if (secondDoor.isClosed()) {
                return secondDoor.getNumber();
            }

            //second door was opened, go nuts
            int newRandomDoor = random.nextInt(closedDoors.size());
            return closedDoors.get(newRandomDoor).getNumber();
        }

        if (lastChosenDoor.equals(secondSelectedDoor)) {
            Door firstDoor = doors.stream()
                    .filter(door -> firstSelectedDoor.equals(door.getNumber()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No door found"));

            if (firstDoor.isClosed()) {
                return firstDoor.getNumber();
            }

            //first door was opened, go nuts
            int newRandomDoor = random.nextInt(closedDoors.size());
            return closedDoors.get(newRandomDoor).getNumber();
        }

        int newRandomDoor = random.nextInt(closedDoors.size());
        return closedDoors.get(newRandomDoor).getNumber();
    }
}
