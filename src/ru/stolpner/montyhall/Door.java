package ru.stolpner.montyhall;

public class Door {
    private final int number;
    private final boolean prize;
    private boolean open;

    public Door(int number, boolean prize) {
        this.number = number;
        this.prize = prize;
        this.open = false;
    }

    public int getNumber() {
        return number;
    }

    public boolean hasPrize() {
        return prize;
    }

    public boolean hasNoPrize() {
        return !prize;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isClosed() {
        return !open;
    }

    public void open() {
        this.open = true;
    }

    @Override
    public String toString() {
        return "Door{" + number + ", " + (prize ? "lucky" : "empty") + ", " + (open ? "open" : "closed") + '}';
    }
}
