package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinStrategy;

public class RedWinsIn3000BCWinStrategy implements WinStrategy {
    @Override
    public Player getWinner(Game game) {
        if (game.getAge() == -3000) {
        return Player.RED;
        }
        return null;
    }

    @Override
    public void incrementSuccessfulAttacks(Player player) {

    }
}
