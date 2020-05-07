package ru.stolpner.montyhall;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainClass {

    private static final Random random = new Random();
    private static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("##.##%");
    private static final int NUMBER_OF_RUNS = 10000;

    public static void main(String[] args) {
        //TODO implement different strategies, compare results

        for (int i = 3; i < 7; i++) {
            runCalculation(i);
        }
    }
    
    private static void runCalculation(int numberOfDoors) {
        int successCounter = 0;
        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            List<Door> doors = setupDoors(numberOfDoors);
            boolean success = playGameWithRandomStrategy(doors);
            successCounter += success ? 1 : 0;
        }
        System.out.println("Calculation finished.");
        System.out.println(String.format("Number of games played for %d doors=%d", numberOfDoors, NUMBER_OF_RUNS));

        double successPercentage = successCounter / (double) NUMBER_OF_RUNS;
        String formattedPercentage = PERCENTAGE_FORMAT.format(successPercentage);
        System.out.println(String.format("Success percentage=%s", formattedPercentage));
    }

    private static List<Door> setupDoors(int numberOfDoors) {
        int prizeDoorNumber = random.nextInt(numberOfDoors);

        List<Door> doors = new ArrayList<>(numberOfDoors);
        for (int i = 0; i < numberOfDoors; i++) {
            doors.add(new Door(i, prizeDoorNumber == i));
        }

        return doors;
    }

    private static boolean playGameWithRandomStrategy(List<Door> doors) {
        while (true) {
            int chosenDoor = chooseRandomDoor(doors);

            Result result = openDoor(doors, chosenDoor);
            switch (result) {
                case DOOR_OPENED:
                    continue;
                case GAME_WON:
                    return true;
                case GAME_LOST:
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
