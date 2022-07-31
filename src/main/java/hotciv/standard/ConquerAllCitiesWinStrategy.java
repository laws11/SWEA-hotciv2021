package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinStrategy;

public class ConquerAllCitiesWinStrategy implements WinStrategy {
    @Override
    public Player getWinner(Game game) {
        int blueCities = 0;
        int redCities = 0;
        for (City city : ((GameImpl) game).getCityMap()) {
            if (city.getOwner().equals(Player.BLUE)) {
                blueCities += 1;
            } else {
                redCities += 1;
            }

        }
        if (blueCities == 0) {
            return Player.RED;
        }
        if (redCities == 0) {
            return Player.BLUE;
        }
        return null;
    }

    @Override
    public void incrementSuccessfulAttacks(Player player) {

    }
}
