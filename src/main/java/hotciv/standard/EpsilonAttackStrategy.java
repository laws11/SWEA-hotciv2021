package hotciv.standard;

import hotciv.framework.*;

public class EpsilonAttackStrategy implements AttackStrategy {
    private Die d1;
    private Die d2;


    public EpsilonAttackStrategy(Die d1,Die d2){
        this.d1 = d1;
        this.d2 = d2;
    }

    @Override
    public boolean attackerWins(Game game, Position attacker, Position defender) {
        int attackersStrength = (getAttackingStrength(game, attacker) + supportBonus(game, attacker)) * terrainBonus(game, attacker) * d1.rollDie();
        int defendersStrength = (getDefensiveStrength(game, defender) + supportBonus(game, defender)) * terrainBonus(game, defender) * d2.rollDie();

        boolean attackerIsStrongest = attackersStrength > defendersStrength;
        return attackerIsStrongest;
    }

    private int terrainBonus(Game game, Position unitPostion) {
        boolean isCity = game.getCityAt(unitPostion) != null;
        if(isCity) return 3;

        boolean isForest = game.getTileAt(unitPostion).getTypeString().equals(GameConstants.FOREST);
        boolean isHill = game.getTileAt(unitPostion).getTypeString().equals(GameConstants.HILLS);
        if(isForest || isHill) return 2;

        return 1;
    }

    private int supportBonus(Game game, Position unitPosition) {
        int supportBonus = 0;
        for(Position p: Utility.get8neighborhoodOf(unitPosition)) {
            if(game.getUnitAt(p) != null && game.getUnitAt(p).getOwner().equals(game.getUnitAt(unitPosition).getOwner())) {
                supportBonus++;
            }
        }
        return supportBonus;
    }

    private int getAttackingStrength(Game game, Position attacker) {
        return game.getUnitAt(attacker).getAttackingStrength();
    }

    private int getDefensiveStrength(Game game, Position defender) {
        return game.getUnitAt(defender).getDefensiveStrength();
    }


}
