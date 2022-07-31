package hotciv.standard.Factories;

import hotciv.framework.*;
import hotciv.standard.*;

import java.io.FileNotFoundException;



public class ZetaCivTestFactory implements GameFactory {
    @Override
    public WinStrategy createWinStrategy() {
        return new ZetaWinStrategy();
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
        return new CfgSetupStrategy("cfg/testZetaCiv.cfg");
    }
    @Override
    public AgeStrategy createAgeStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public LegalTileStrategy createLegalTileStrategy() { return new SimpleLegalTileStrategy();}
}


