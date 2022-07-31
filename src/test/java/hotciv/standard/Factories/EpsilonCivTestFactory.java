package hotciv.standard.Factories;

import hotciv.framework.*;
import hotciv.standard.*;

import java.io.FileNotFoundException;

public class EpsilonCivTestFactory implements GameFactory {
    private Die d1;
    private Die d2;

    public EpsilonCivTestFactory(){
        this.d1 = new FixedDie(1);
        this.d2 = new FixedDie(1);
    }

    @Override
    public WinStrategy createWinStrategy() {
        return new ThreeAttacksWinStrategy();
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new NoUnitActionStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new EpsilonAttackStrategy(d1, d2);
    }

    @Override
    public SetupStrategy createSetupStrategy() throws FileNotFoundException {
        return new CfgSetupStrategy("cfg/easyTestAttack.cfg");
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public LegalTileStrategy createLegalTileStrategy() { return new SimpleLegalTileStrategy();}
}
