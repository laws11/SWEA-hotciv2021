package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    private final String unitType;
    private final Player owner;
    private int moveCount;
    private boolean isFortified = false;

    public UnitImpl(String unitType, Player owner) {
        this.unitType = unitType;
        this.owner = owner;
        if(unitType.equals(GameConstants.SANDWORM)){moveCount = 2;}
        else {moveCount = 1;}
    }

    @Override
    public String getTypeString() {
        return unitType;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        if(unitType.equals(GameConstants.ARCHER)) {
            if(isFortified) {
                return 6;
            }
            else {
                return 3;
            }
        }
        if(unitType.equals(GameConstants.LEGION)) return 2;
        if(unitType.equals(GameConstants.SETTLER)) return 3;
        return 10;
    }

    @Override
    public int getAttackingStrength() {
        if(unitType.equals(GameConstants.ARCHER)) return 2;
        if(unitType.equals(GameConstants.LEGION)) return 4;
        if(unitType.equals(GameConstants.SETTLER)) return 0;
        return 0;
    }

    public void decreaseMoveCount() {
        moveCount -= 1;
    }

    public void resetMoveCount(){
        if(unitType.equals(GameConstants.SANDWORM)){moveCount = 2;}
        else {moveCount = 1;};
    }

    public void fortify() {
        if (isFortified) {
            isFortified = false;
        } else {
            isFortified = true;
        }
    }
}
