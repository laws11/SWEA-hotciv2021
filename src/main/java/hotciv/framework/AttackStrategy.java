package hotciv.framework;

import hotciv.standard.GameImpl;

public interface AttackStrategy {
    boolean attackerWins(Game game, Position attacker, Position defender);
}
