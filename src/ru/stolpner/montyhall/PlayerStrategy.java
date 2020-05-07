package ru.stolpner.montyhall;

import java.util.List;

public interface PlayerStrategy {
    String getName();
    int chooseDoor(List<Door> doors);
}
