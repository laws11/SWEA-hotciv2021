package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.Game;
import hotciv.framework.Position;

public class AttackerAllwaysWinAttackStrategy implements AttackStrategy {
    @Override
    public boolean attackerWins(Game game, Position attacker, Position defender) {
        System.out.println("attackStrategy");
        return true;
    }
}
