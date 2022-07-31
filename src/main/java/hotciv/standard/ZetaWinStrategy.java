package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinStrategy;

public class ZetaWinStrategy implements WinStrategy {
    private WinStrategy ConquerAllCitiesWinStrategy;
    private WinStrategy ThreeAttacksWinStrategy;
    private WinStrategy currentState;

    public ZetaWinStrategy() {
        this.ConquerAllCitiesWinStrategy = new ConquerAllCitiesWinStrategy();
        this.ThreeAttacksWinStrategy = new ThreeAttacksWinStrategy();
        this.currentState = ConquerAllCitiesWinStrategy;
    }

    @Override
    public Player getWinner(Game game) {
        if(game.getRound() > 20) {
            currentState = ThreeAttacksWinStrategy;
        }
        else {
            currentState = ConquerAllCitiesWinStrategy;
        }
        return currentState.getWinner(game);
    }

    @Override
    public void incrementSuccessfulAttacks(Player player) {
        currentState.incrementSuccessfulAttacks(player);
    }


}
