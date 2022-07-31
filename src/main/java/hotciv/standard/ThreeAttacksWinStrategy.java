package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinStrategy;

public class ThreeAttacksWinStrategy implements WinStrategy {
    private int blueSuccessfulAttacks = 0;
    private int redSuccessfulAttacks = 0;

    @Override
    public Player getWinner(Game game) {
        boolean blueWins = blueSuccessfulAttacks == 3;
        if(blueWins) return Player.BLUE;

        boolean redWins = redSuccessfulAttacks == 3;
        if(redWins) return Player.RED;
        return null;
    }

    @Override
    public void incrementSuccessfulAttacks(Player attackWinner){
        boolean blueWinner = attackWinner == Player.BLUE;
        if(blueWinner) blueSuccessfulAttacks ++;

        boolean redWinner = attackWinner == Player.RED;
        if(redWinner) {
            redSuccessfulAttacks ++;
        }
    }


}
