package hotciv.standard.Factories;

import hotciv.framework.*;
import hotciv.standard.*;

import java.io.FileNotFoundException;

public class BetaCivFactory implements GameFactory{
    @Override
    public WinStrategy createWinStrategy() {
        return new ConquerAllCitiesWinStrategy();
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new NoUnitActionStrategy();
    }

    @Override
    public AttackStrategy createAttackStrategy() {
        return new AttackerAllwaysWinAttackStrategy();
    }

    @Override
    public SetupStrategy createSetupStrategy() throws FileNotFoundException {
        return new CfgSetupStrategy("cfg/alphaciv.cfg");
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return new BetaCivAgeStrategy();
    }

    @Override
    public LegalTileStrategy createLegalTileStrategy() { return new SimpleLegalTileStrategy();}
}


