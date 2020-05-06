package ru.stolpner.montyhall;

public class Door {
    private final boolean prize;
    private final boolean open;

    public Door(boolean prize, boolean open) {
        this.prize = prize;
        this.open = open;
    }

    public boolean hasPrize() {
        return prize;
    }

    public boolean isOpen() {
        return open;
    }
}
