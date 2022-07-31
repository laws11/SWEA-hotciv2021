package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CfgSetupStrategy implements SetupStrategy {

    private final String filename;
    private Map<Position, Tile> worldMap;
    private Map<Position, City> cityMap;
    private Map<Position, Unit> unitMap;

    public CfgSetupStrategy(String filename) throws FileNotFoundException {
        this.filename = filename;
        loadWorldMap();
        loadUnitAndCityMap();
    }

    @Override
    public Map<Position, Tile> getWorldMap() {
        return worldMap;
    }

    @Override
    public Map<Position, City> getCityMap() {
        return cityMap;
    }

    @Override
    public Map<Position, Unit> getUnitMap() {
        return unitMap;
    }

    private void loadWorldMap() throws FileNotFoundException {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        // Conversion...
        Map<Position, Tile> theWorld = new HashMap<>();
        String line;
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            line = sc.nextLine();
            for (int c = 0; c < GameConstants.WORLDSIZE; c++) {
                char tileChar = line.charAt(c);
                String type = "";
                if (tileChar == '.') {
                    type = GameConstants.OCEANS;
                }
                if (tileChar == 'o') {
                    type = GameConstants.PLAINS;
                }
                if (tileChar == 'M') {
                    type = GameConstants.MOUNTAINS;
                }
                if (tileChar == 'f') {
                    type = GameConstants.FOREST;
                }
                if (tileChar == 'h') {
                    type = GameConstants.HILLS;
                }
                if (tileChar == 'd') {
                    type = GameConstants.DESERT;
                }
                Position p = new Position(r, c);
                theWorld.put(p, new TileImpl(type));
            }
        }
        this.worldMap = theWorld;
    }

    private void loadUnitAndCityMap() throws FileNotFoundException {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        Map<Position, City> cityMap = new HashMap<>();
        Map<Position, Unit> unitMap = new HashMap<>();
        //Go Past the worldMap
        for (int r = 0; r < GameConstants.WORLDSIZE; r++) {
            sc.nextLine();
        }
        Player currentPlayer = null;
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.equals("RED")) {
                currentPlayer = Player.RED;
                continue;
            } else if (line.equals("BLUE")) {
                currentPlayer = Player.BLUE;
                continue;
            }
            String[] pos = line.substring(1).split(",");

            if (line.charAt(0) == 'C') {
                cityMap.put(new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])), new CityImpl(currentPlayer));
            } else if (line.charAt(0) == 'A') {
                unitMap.put(new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])), new UnitImpl(GameConstants.ARCHER, currentPlayer));
            } else if (line.charAt(0) == 'L') {
                unitMap.put(new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])), new UnitImpl(GameConstants.LEGION, currentPlayer));
            } else if (line.charAt(0) == 'S') {
                unitMap.put(new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])), new UnitImpl(GameConstants.SETTLER, currentPlayer));
            } else if (line.charAt(0) == 'W') {
                unitMap.put(new Position(Integer.parseInt(pos[0]), Integer.parseInt(pos[1])), new UnitImpl(GameConstants.SANDWORM, currentPlayer));
            }
        }
        this.cityMap = cityMap;
        this.unitMap = unitMap;
    }
}

