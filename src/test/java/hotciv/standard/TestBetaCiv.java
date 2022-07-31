package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.BetaCivFactory;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBetaCiv {
    private Game game;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        game = new GameImpl(new BetaCivFactory());
    }

    @Test
    public void blueShouldWinWhenConqueringRedCity() {
        game.endOfTurn();
        game.moveUnit(new Position(3, 2), new Position(2, 1));
        game.endOfTurn();
        game.endOfTurn();
        game.moveUnit(new Position(2, 1), new Position(1, 1));
        assertThat("Blue takes red city at 1,1 and win", game.getWinner(), is(Player.BLUE));
    }

    @Test
    public void afterFortyRoundsYearIsMinus1() {
        for (int i = 0; i < 80; i++) {
            game.endOfTurn();
        }
        assertThat("After 40 rounds year is -1", game.getAge(), is(-1));
    }

    @Test
    public void afterFortyOneRoundsYearIs1() {
        for (int i = 0; i < 82; i++) {
            game.endOfTurn();
        }
        assertThat("After 41 rounds year is 1", game.getAge(), is(1));
    }

    @Test
    public void after42RoundsYearIs50AD() {
        for (int i = 0; i < 84; i++) {
            game.endOfTurn();
        }
        assertThat("After 42 rounds year is 50AD", game.getAge(), is(50));
    }

    @Test
    public void after76RoundsYearIs1750AD() {
        for (int i = 0; i < 152; i++) {
            game.endOfTurn();
        }
        assertThat("After 76 rounds year is 1750AD", game.getAge(), is(1750));
    }

    @Test
    public void after82RoundsYearIs1900AD() {
        for (int i = 0; i < 164; i++) {
            game.endOfTurn();
        }
        assertThat("After 82 rounds year is 1900AD", game.getAge(), is(1900));
    }

    @Test
    public void after96RoundsYearIs1970AD() {
        for (int i = 0; i < 192; i++) {
            game.endOfTurn();
        }
        assertThat("After 96 rounds year is 1970AD", game.getAge(), is(1970));
    }

    @Test
    public void after147RoundsYearIs2021AD() {
        for (int i = 0; i < 294; i++) {
            game.endOfTurn();
        }
        assertThat("After 147 rounds year is 2021AD", game.getAge(), is(2021));
    }
}

