package hotciv.standard.Factories;

import hotciv.framework.*;
import hotciv.standard.*;

import java.io.FileNotFoundException;

public class FractalTestFactory implements GameFactory {
    @Override
    public WinStrategy createWinStrategy() {
        return new RedWinsIn3000BCWinStrategy();
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
        return new FractalSetupStrategy(new AlphaCivSetupStrategy());
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public LegalTileStrategy createLegalTileStrategy() {
        return new SimpleLegalTileStrategy();
    }
}
