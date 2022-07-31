package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.EpsilonCivFactory;
import hotciv.standard.Factories.EpsilonCivTestFactory;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestEpsilonCiv {
    private Game game;
    private AttackStrategy attackStrategy;

    /**
     * Fixture for alphaciv testing.
     */
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        game = new GameImpl(new EpsilonCivTestFactory());
        attackStrategy = new EpsilonAttackStrategy(new FixedDie(1), new FixedDie(1));
    }

    @Test
    public void blueShouldWinAfterThreeSuccessfulAttacks() {
        ThreeAttacksWinStrategy winStrategy = new ThreeAttacksWinStrategy();
        winStrategy.incrementSuccessfulAttacks(Player.BLUE);
        winStrategy.incrementSuccessfulAttacks(Player.BLUE);
        winStrategy.incrementSuccessfulAttacks(Player.BLUE);
        assertThat("After three successful attacks blue players is the winner", winStrategy.getWinner(game),is(Player.BLUE));
    }

    @Test
    public void redShouldWinAfterThreeSuccessfulAttacks() {
        ThreeAttacksWinStrategy winStrategy = new ThreeAttacksWinStrategy();
        winStrategy.incrementSuccessfulAttacks(Player.RED);
        winStrategy.incrementSuccessfulAttacks(Player.RED);
        winStrategy.incrementSuccessfulAttacks(Player.RED);
        assertThat("After three successful attacks red players is the winner", winStrategy.getWinner(game), is(Player.RED));
    }

    @Test
    public void attackerWinsWithHigherAttackPower() {
        game.endOfTurn();
        game.moveUnit(new Position(3,2), new Position(3,1));
         assertThat("Blue Legion wins when attacking red Archer", attackStrategy.attackerWins(game, new Position(3,1), new Position(2,0)), is(true));
    }

    @Test
    public void DefenderWinsWithHigherDefensePower() {
        assertThat("Red Settler losses when attacking blue Legion", attackStrategy.attackerWins(game, new Position(4,3), new Position(3,2)), is(false));
    }

    @Test
    public void attackerHasIncreasedAttackingStrenghtWhenSupportedByOwnUnit() {
        game.moveUnit(new Position(2,0), new Position(2,1));
        game.moveUnit(new Position(4,3), new Position(4,2));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(4,2), new Position(3,1));
        assertThat("Red Archer supported by red Settler wins when attacking blue Legion", attackStrategy.attackerWins(game, new Position(2,1), new Position(3,2)), is(true));
    }

    @Test
    public void defenderGetsBonusWhenAttackedInCity() {
        game.moveUnit(new Position(2,0), new Position(1,1));
        game.endOfTurn();
        game.moveUnit(new Position(3,2), new Position(2,1));
        assertThat("Blue Legion lose, when attacking red Archer in city", attackStrategy.attackerWins(game, new Position(2,1), new Position(1,1)), is(false));
    }

    @Test
    public void attackerGetsBonusWhenAttackingFromCity() {
        game.moveUnit(new Position(2,0), new Position(1,1));
        game.endOfTurn();
        game.moveUnit(new Position(3,2), new Position(2,1));
        assertThat("Red Archer wins when attacking blue Legion from a city", attackStrategy.attackerWins(game, new Position(1,1), new Position(2,1)), is(true));
    }

    @Test
    public void defenderGetsBonusWhenDefendingInForest() {
        assertThat("Blue Legion loss when attacking red Archer in forest", attackStrategy.attackerWins(game, new Position(15, 14), new Position(15,15)), is(false));
    }

    @Test
    public void defenderGetsBonusWhenDefendingFromHill() {
        assertThat("Blue Legion loss when attacking red Legion on hill", attackStrategy.attackerWins(game, new Position(15, 1), new Position(15,0)), is(false));
    }

    @Test
    public void weakUnitWinsIfItHasAGoodDieRoll() {
        AttackStrategy fixedAttackStrategy = new EpsilonAttackStrategy(new FixedDie(1), new FixedDie(6));
        game.moveUnit(new Position(15, 15), new Position(14, 14));
        assertThat("Red Legion wins, when attacked by blue Legion because of a good die roll",
                fixedAttackStrategy.attackerWins(game, new Position(15,14), new Position(14,14)), is(false));
    }

    @Test
    public void redWinsAfterThreeSuccessfulAttacks() {
        game.moveUnit(new Position(12,0), new Position(12,1));
        game.moveUnit(new Position(15,15), new Position(15,14));
        game.moveUnit(new Position(15,0), new Position(15,1));
        assertThat("After three successful attacks red wins", game.getWinner(), is(Player.RED));
    }

    @Test
    public void blueWinsAfterThreeSuccessfulAttacks() {
        game.moveUnit(new Position(15,15), new Position(14,14));
        game.moveUnit(new Position(15,0), new Position(14,1));
        game.endOfTurn();
        game.moveUnit(new Position(10, 0), new Position(10, 1));
        game.moveUnit(new Position(15, 14), new Position(14, 14));
        game.moveUnit(new Position(15, 1), new Position(14, 1));
        assertThat("After three successful attacks blue wins", game.getWinner(), is(Player.BLUE));
    }
}

