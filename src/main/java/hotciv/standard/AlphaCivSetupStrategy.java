package hotciv.standard;

import hotciv.framework.*;

import java.io.FileNotFoundException;
import java.util.Map;

public class AlphaCivSetupStrategy implements SetupStrategy {

    private CfgSetupStrategy cfgSetupStrategy;

    public AlphaCivSetupStrategy() throws FileNotFoundException {
        cfgSetupStrategy = new CfgSetupStrategy("cfg/alphaciv.cfg");
    }

    @Override
    public Map<Position, Tile> getWorldMap() {
        return cfgSetupStrategy.getWorldMap();
    }

    @Override
    public Map<Position, City> getCityMap() {
        return cfgSetupStrategy.getCityMap();
    }

    @Override
    public Map<Position, Unit> getUnitMap() {
        return cfgSetupStrategy.getUnitMap();
    }
}
