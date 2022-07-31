package hotciv.standard;

import hotciv.framework.*;

public class GammaUnitActionStrategy implements UnitActionStrategy {
    @Override
    public void performActionAt(Unit unit, Game game, Position p) {
        if(unit.getTypeString().equals(GameConstants.SETTLER)) {
            ((GameImpl)game).buildCity(p, game.getPlayerInTurn());
        }

        if(unit.getTypeString().equals(GameConstants.ARCHER)) {
            ((GameImpl)game).fortifyArcher(p, game);
        }
    }

}
