package ru.stolpner.montyhall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainClass {

    private static final Random random = new Random();
    private static final int NUMBER_OF_DOORS = 3;

    public static void main(String[] args) {

        //TODO I want to test some theories about Monty Hall problem
        //  What I want to do in this project:
        //  1) Create primitive simulation of one Monty Hall game with 3 doors and random strategy
        //  2) Expand primitive simulation to n doors and random strategy
        //  3) Store results for all games, print statistics out
        //  4) Try random strategy for huge amount of games with 1..n doors, compare results
        //  5) Think of different strategies to play with
        //  6) Implement up to 5 different strategies, compare them with random

        List<Door> doors = setupDoors();
        System.out.println(doors);

        playGameWithRandomStrategy(doors);
    }

    private static List<Door> setupDoors() {
        int prizeDoorNumber = random.nextInt(NUMBER_OF_DOORS);

        List<Door> doors = new ArrayList<>(NUMBER_OF_DOORS);
        for (int i = 0; i < NUMBER_OF_DOORS; i++) {
            doors.add(new Door(i, prizeDoorNumber == i));
        }

        return doors;
    }

    private static boolean playGameWithRandomStrategy(List<Door> doors) {
        while (true) {
            int chosenDoor = chooseRandomDoor(doors);
            System.out.println("Chosen door number " + chosenDoor);

            Result result = openDoor(doors, chosenDoor);
            switch (result) {
                case DOOR_OPENED:
                    System.out.println(doors);
                    System.out.println("Opened door, continuing.");
                    continue;
                case GAME_WON:
                    System.out.println("Game is won");
                    return true;
                case GAME_LOST:
                    System.out.println(doors);
                    System.out.println("Game is lost");
                    return false;
            }
        }
    }

    private static int chooseRandomDoor(List<Door> doors) {
        List<Door> doorsToChooseFrom = doors.stream()
                .filter(Door::isClosed)
                .collect(Collectors.toList());

        int chosenDoor = random.nextInt(doorsToChooseFrom.size());
        return doorsToChooseFrom.get(chosenDoor).getNumber();
    }

    private static Result openDoor(List<Door> doors, int chosenDoor) {
        List<Door> doorsToOpen = doors.stream()
                .filter(Door::isClosed)
                .filter(Door::hasNoPrize)
                .filter(door -> door.getNumber() != chosenDoor)
                .collect(Collectors.toList());

        if (doorsToOpen.isEmpty()) {
            Door doorWithPrize = doors.stream()
                    .filter(Door::hasPrize)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No door with prize found"));

            return doorWithPrize.getNumber() == chosenDoor
                    ? Result.GAME_WON
                    : Result.GAME_LOST;
        }

        int doorToOpenNumber = random.nextInt(doorsToOpen.size());
        doorsToOpen.get(doorToOpenNumber).open();
        return Result.DOOR_OPENED;
    }

    enum Result {
        DOOR_OPENED,
        GAME_LOST,
        GAME_WON
    }
}
