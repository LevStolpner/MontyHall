package ru.stolpner.montyhall;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainClass {

    private static final Random random = new Random();
    public static final PlayerStrategy[] strategies = {new RandomStrategy()};
    private static final int NUMBER_OF_RUNS = 10000;
    private static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("##.##%");

    public static void main(String[] args) {
        //TODO implement different strategies, compare results

        for (PlayerStrategy strategy : strategies) {
            for (int i = 3; i < 7; i++) {
                runGames(i, strategy);
            }
        }
    }

    /**
     * Запускает игры для определенного количества дверей с определенной стратегией
     *
     * @param numberOfDoors количество дверей
     * @param strategy      стратегия выбора дверей
     */
    private static void runGames(int numberOfDoors, PlayerStrategy strategy) {
        int successCounter = 0;
        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            List<Door> doors = setupDoors(numberOfDoors);
            boolean success = playGame(doors, strategy);
            successCounter += success ? 1 : 0;
        }
        double successPercentage = successCounter / (double) NUMBER_OF_RUNS;
        String formattedPercentage = PERCENTAGE_FORMAT.format(successPercentage);

        System.out.println(String.format("Strategy=%s. Doors=%d. Games=%s. Success rate=%s",
                strategy.getName(), numberOfDoors, NUMBER_OF_RUNS, formattedPercentage));
    }

    /**
     * Подготовить список дверей для игры
     *
     * @param numberOfDoors количество дверей
     * @return список дверей
     */
    private static List<Door> setupDoors(int numberOfDoors) {
        int prizeDoorNumber = random.nextInt(numberOfDoors);

        List<Door> doors = new ArrayList<>(numberOfDoors);
        for (int i = 0; i < numberOfDoors; i++) {
            doors.add(new Door(i, prizeDoorNumber == i));
        }

        return doors;
    }

    /**
     * Провести одну игру с заданными дверями и стратегией
     *
     * @param doors          двери
     * @param playerStrategy стратегия выбора дверей
     * @return true, если игра выиграна, иначе false
     */
    private static boolean playGame(List<Door> doors, PlayerStrategy playerStrategy) {
        while (true) {
            int chosenDoor = playerStrategy.chooseDoor(doors);

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

    /**
     * Пытается открыть произвольную дверь без приза
     *
     * @param doors      двери
     * @param chosenDoor выбранная игроком дверь
     * @return результат попытки открытия двери
     */
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

    /**
     * Результат попытки открытия двери
     */
    enum Result {
        DOOR_OPENED,
        GAME_LOST,
        GAME_WON
    }
}
