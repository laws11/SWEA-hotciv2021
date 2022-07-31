package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.LegalTileStrategy;
import hotciv.framework.Position;

public class DesertLegalTileStrategy implements LegalTileStrategy {
    @Override
    public boolean legalTile(Position from, Position to, Game game) {
        boolean sandworm = game.getUnitAt(from).getTypeString().equals(GameConstants.SANDWORM);
        if(sandworm) {
            boolean desert = game.getTileAt(to).getTypeString().equals(GameConstants.DESERT);
            if(!desert) {return false;}
        }
        boolean tileIsLegal = !game.getTileAt(to).getTypeString().equals(GameConstants.OCEANS) && !game.getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
        return tileIsLegal;
    }
}
