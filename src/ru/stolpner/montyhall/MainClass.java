package ru.stolpner.montyhall;

import ru.stolpner.montyhall.strategy.ChangeLastMomentStrategy;
import ru.stolpner.montyhall.strategy.ChangeTwiceWaitStrategy;
import ru.stolpner.montyhall.strategy.ChangeWaitStrategy;
import ru.stolpner.montyhall.strategy.ChangeWaitTwiceStrategy;
import ru.stolpner.montyhall.strategy.ChangingStrategy;
import ru.stolpner.montyhall.strategy.ChangingWithRandomStrategy;
import ru.stolpner.montyhall.strategy.DynamicTwoDoorStrategy;
import ru.stolpner.montyhall.strategy.DynamicTwoDoorWithChangingStrategy;
import ru.stolpner.montyhall.strategy.FullChangingStrategy;
import ru.stolpner.montyhall.strategy.PlayerStrategy;
import ru.stolpner.montyhall.strategy.RandomStrategy;
import ru.stolpner.montyhall.strategy.RandomTwoDoorStrategy;
import ru.stolpner.montyhall.strategy.StubbornStrategy;
import ru.stolpner.montyhall.strategy.StubbornTwoDoorStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MainClass {

    private static final Random random = new Random();
    public static final EnumSet<PlayerStrategyType> strategyTypes = EnumSet.allOf(PlayerStrategyType.class);
    private static final int NUMBER_OF_DOORS = 20;
    private static final int NUMBER_OF_RUNS = 10000;

    public static void main(String[] args) {
        //TODO refactor code to easily readable, make repo more "attractive"

        System.out.printf("Number of doors=%d, number of runs=%d%n", NUMBER_OF_DOORS, NUMBER_OF_RUNS);

        List<StrategyResult> strategyResults = new ArrayList<>();
        for (PlayerStrategyType strategyType : strategyTypes) {
            StrategyResult result = runGames(strategyType);
            strategyResults.add(result);
        }

        strategyResults.stream()
                .sorted(Comparator.comparingDouble(StrategyResult::getSuccessPercentage).reversed())
                .forEach(System.out::println);
    }

    /**
     * Запускает игры с определенной стратегией
     *
     * @param strategyType  тип стратегии выбора дверей
     * @return результат работы стратегии
     */
    private static StrategyResult runGames(PlayerStrategyType strategyType) {
        int successCounter = 0;
        for (int i = 0; i < NUMBER_OF_RUNS; i++) {
            List<Door> doors = setupDoors();

            PlayerStrategy strategy = createPlayerStrategy(strategyType);
            boolean success = playGame(doors, strategy);
            successCounter += success ? 1 : 0;
        }
        double successPercentage = successCounter / (double) NUMBER_OF_RUNS;

        return new StrategyResult(strategyType.getName(), successPercentage);
    }

    /**
     * Подготовить список дверей для игры
     *
     * @return список дверей
     */
    private static List<Door> setupDoors() {
        int prizeDoorNumber = random.nextInt(NUMBER_OF_DOORS);

        List<Door> doors = new ArrayList<>(NUMBER_OF_DOORS);
        for (int i = 0; i < NUMBER_OF_DOORS; i++) {
            doors.add(new Door(i, prizeDoorNumber == i));
        }

        return doors;
    }

    /**
     * Создает новую стратегию на основе типа
     *
     * @param strategyType тип стратегии
     * @return новая стратегия
     */
    private static PlayerStrategy createPlayerStrategy(PlayerStrategyType strategyType) {
        return switch (strategyType) {
            case RANDOM -> new RandomStrategy();
            case RANDOM_TWO_DOOR -> new RandomTwoDoorStrategy();
            case STUBBORN -> new StubbornStrategy();
            case STUBBORN_TWO_DOOR -> new StubbornTwoDoorStrategy();
            case DYNAMIC_TWO_DOOR -> new DynamicTwoDoorStrategy();
            case DYNAMIC_TWO_DOOR_WITH_CHANGING -> new DynamicTwoDoorWithChangingStrategy();
            case CHANGING -> new ChangingStrategy();
            case CHANGING_WITH_RANDOM -> new ChangingWithRandomStrategy();
            case FULL_CHANGING -> new FullChangingStrategy();
            case CHANGE_WAIT -> new ChangeWaitStrategy();
            case CHANGE_TWICE_WAIT -> new ChangeTwiceWaitStrategy();
            case CHANGE_WAIT_TWICE -> new ChangeWaitTwiceStrategy();
            case CHANGE_LAST_MOMENT -> new ChangeLastMomentStrategy();
            default -> throw new IllegalStateException("Unsupported strategy");
        };
    }

    /**
     * Провести одну игру с заданными дверями и стратегией
     *
     * @param doors          двери
     * @param playerStrategy стратегия выбора дверей
     * @return true, если игра выиграна, иначе false
     */
    private static boolean playGame(List<Door> doors, PlayerStrategy playerStrategy) {
        Integer lastChosenDoor = null;
        while (true) {
            int chosenDoor = playerStrategy.chooseDoor(doors, lastChosenDoor);
            lastChosenDoor = chosenDoor;

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
