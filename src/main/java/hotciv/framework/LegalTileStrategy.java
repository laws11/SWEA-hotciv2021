package hotciv.framework;

public interface LegalTileStrategy {
    boolean legalTile(Position from, Position to, Game game);
}
