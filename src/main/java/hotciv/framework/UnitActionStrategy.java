package hotciv.framework;

public interface UnitActionStrategy {
    void performActionAt(Unit unit, Game game, Position p);
}
