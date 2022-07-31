package hotciv.stub;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class StubBrokerUnit implements Unit {
    @Override
    public String getTypeString() {
        return GameConstants.ARCHER;
    }

    @Override
    public Player getOwner() {
        return Player.BLUE;
    }

    @Override
    public int getMoveCount() {
        return 100;
    }

    @Override
    public int getDefensiveStrength() {
        return 420;
    }

    @Override
    public int getAttackingStrength() {
        return 1337;
    }
}
