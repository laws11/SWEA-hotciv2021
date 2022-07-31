package hotciv.standard;

import hotciv.framework.*;

public class ThetaUnitActionStrategy implements UnitActionStrategy {
    private UnitActionStrategy gammaUnitActionStrategy = new GammaUnitActionStrategy();

    @Override
    public void performActionAt(Unit unit, Game game, Position p) {
        if(unit.getTypeString().equals(GameConstants.SANDWORM)) {
            devour(game, p);
        }
        else gammaUnitActionStrategy.performActionAt(unit, game, p);

    }

    private void devour(Game game, Position position) {
        for (Position p : Utility.get8neighborhoodOf(position)) {
            if(game.getUnitAt(p) != null) {
                if(!game.getPlayerInTurn().equals(game.getUnitAt(p).getOwner())  ) {
                    ((GameImpl) game).removeUnitAt(p);
                }
            }
        }
    }
}


