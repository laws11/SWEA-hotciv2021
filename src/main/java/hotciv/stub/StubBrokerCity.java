package hotciv.stub;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

public class StubBrokerCity implements City {
    @Override
    public Player getOwner() {
        return Player.RED;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public int getTreasury() {
        return 40;
    }

    @Override
    public String getProduction() {
        return GameConstants.ARCHER;
    }

    @Override
    public String getWorkforceFocus() {
        return GameConstants.foodFocus;
    }
}
