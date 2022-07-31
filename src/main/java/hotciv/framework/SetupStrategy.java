package hotciv.framework;

import java.util.Map;

public interface SetupStrategy {
    public Map<Position, Tile> getWorldMap();
    public Map<Position, City> getCityMap();
    public Map<Position, Unit> getUnitMap();

}
