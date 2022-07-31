package hotciv.standard;

import hotciv.framework.*;
import thirdparty.ThirdPartyFractalGenerator;

import java.util.HashMap;
import java.util.Map;

public class FractalSetupStrategy implements SetupStrategy {
    private Map<Position, Tile> worldMap = new HashMap<>();
    private SetupStrategy cityAndUnitSetupStrategy;

    public FractalSetupStrategy(SetupStrategy setupStrategy){
        cityAndUnitSetupStrategy = setupStrategy;
        loadTileMap();
    }

    private void loadTileMap() {
        ThirdPartyFractalGenerator generator =
                new ThirdPartyFractalGenerator();
        for ( int r = 0; r < 16; r++ ) {
            for ( int c = 0; c < 16; c++ ) {
                worldMap.put(new Position(r, c), new TileImpl(charToString(generator.getLandscapeAt(r,c))));
            }
        }
    }

    private String charToString(char c){
        switch (c){
            case '.': return GameConstants.OCEANS;
            case 'o': return GameConstants.PLAINS;
            case 'M': return GameConstants.MOUNTAINS;
            case 'f': return GameConstants.FOREST;
            case 'h': return GameConstants.HILLS;
            case 'd': return GameConstants.DESERT;
            default: return "INVALID TILE CHAR";
        }
    }

    @Override
    public Map<Position, Tile> getWorldMap() {
        return worldMap;
    }

    @Override
    public Map<Position, City> getCityMap() {
        return cityAndUnitSetupStrategy.getCityMap();
    }

    @Override
    public Map<Position, Unit> getUnitMap() {
        return cityAndUnitSetupStrategy.getUnitMap();
    }

}
