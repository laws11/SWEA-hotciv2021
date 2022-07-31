package hotciv.standard.Factories;

import hotciv.framework.*;
import hotciv.standard.*;

import java.io.FileNotFoundException;

public class EpsilonCivFactory implements GameFactory {
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
        return new EpsilonAttackStrategy(new randomDie(), new randomDie());
    }

    @Override
    public SetupStrategy createSetupStrategy() throws FileNotFoundException {
        return new CfgSetupStrategy("cfg/alphaciv.cfg");
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public LegalTileStrategy createLegalTileStrategy() { return new SimpleLegalTileStrategy();}
}


