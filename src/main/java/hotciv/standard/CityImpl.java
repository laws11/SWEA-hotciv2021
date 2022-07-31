package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

public class CityImpl implements City {
    private int treasury;
    private Player owner;
    private String currentProduction = GameConstants.ARCHER;

    public CityImpl(Player owner) {
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return 1;
    }

    public void endOfRoundProduction(int productionPerTurn) {
        treasury += productionPerTurn;
    }

    @Override
    public int getTreasury() {
        return treasury;
    }

    @Override
    public String getProduction() {
        return currentProduction;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    public void setOwner(Player player){
        owner = player;
    }

    public int getUnitPrice() {
        if(getProduction().equals(GameConstants.LEGION)) {
            return 15;
        }
        else if(getProduction().equals(GameConstants.SETTLER)) {
            return 30;
        }
        else if(getProduction().equals(GameConstants.SANDWORM)) {
            return 30;
        }
        else { return 10;}
    }

    public void setUnitProduction(String unitType){
        currentProduction = unitType;
    }

    public void setTreasury(int treasury) {
        this.treasury = treasury;
    }
}
