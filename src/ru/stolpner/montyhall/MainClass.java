package ru.stolpner.montyhall;

import java.util.Random;

public class MainClass {

    private static final Random random = new Random();

    public static void main(String[] args) {

        //TODO I want to test some theories about Monty Hall problem
        //  What I want to do in this project:
        //  1) Create primitive simulation of one Monty Hall game with 3 doors and random strategy
        //  2) Expand primitive simulation to n doors and random strategy
        //  3) Store results for all games, print statistics out
        //  4) Try random strategy for huge amount of games with 1..n doors, compare results
        //  5) Think of different strategies to play with
        //  6) Implement up to 5 different strategies, compare them with random

        Door[] doors = setupDoors(3);

    }

    private static Door[] setupDoors(int numberOfDoors) {
        int luckyDoorNumber = random.nextInt(numberOfDoors);

        Door[] doors = new Door[numberOfDoors];
        for (int i = 0; i < numberOfDoors; i++) {
            if (luckyDoorNumber == i) {
                doors[i] = new Door(true, false);
            } else {
                doors[i] = new Door(false, false);
            }
        }

        return doors;
    }
}
