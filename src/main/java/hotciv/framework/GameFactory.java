package hotciv.framework;

import java.io.FileNotFoundException;

public interface GameFactory {
    WinStrategy createWinStrategy();
    UnitActionStrategy createUnitActionStrategy();
    AttackStrategy createAttackStrategy();
    SetupStrategy createSetupStrategy() throws FileNotFoundException;
    AgeStrategy createAgeStrategy();
    LegalTileStrategy createLegalTileStrategy();
}
