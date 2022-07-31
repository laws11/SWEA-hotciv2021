package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.LegalTileStrategy;
import hotciv.framework.Position;

public class SimpleLegalTileStrategy implements LegalTileStrategy {

    @Override
    public boolean legalTile(Position from, Position to, Game game) {
        boolean tileIsLegal = !game.getTileAt(to).getTypeString().equals(GameConstants.OCEANS) && !game.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
        return tileIsLegal;
    }
}

