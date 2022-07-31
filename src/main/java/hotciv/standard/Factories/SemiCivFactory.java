package hotciv.standard.Factories;

import hotciv.framework.*;
import hotciv.standard.*;

import java.io.FileNotFoundException;

public class SemiCivFactory implements GameFactory {
    @Override
    public WinStrategy createWinStrategy() {
        return new ThreeAttacksWinStrategy();
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new GammaUnitActionStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new EpsilonAttackStrategy(new randomDie(), new randomDie());
    }

    @Override
    public SetupStrategy createSetupStrategy() throws FileNotFoundException {
        return new CfgSetupStrategy("cfg/deltaciv.cfg");
    }

    @Override
    public AgeStrategy createAgeStrategy() {return new BetaCivAgeStrategy();}

    @Override
    public LegalTileStrategy createLegalTileStrategy() { return new SimpleLegalTileStrategy();}
}



