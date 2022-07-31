package hotciv.framework;

import hotciv.standard.GameImpl;

public interface WinStrategy {
    Player getWinner(Game game);

    void incrementSuccessfulAttacks(Player player);
}
