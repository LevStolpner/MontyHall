package ru.stolpner.montyhall;

import java.util.List;

public interface PlayerStrategy {
    int chooseDoor(List<Door> doors);
}
