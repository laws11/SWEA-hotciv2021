package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.AlphaCivFactory;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogDecoratorTest {
    private Game game;

    /** Fixture for alphaciv testing. */
    @BeforeEach
    public void setUp() throws FileNotFoundException { game = new LogDecorator(new GameImpl(new AlphaCivFactory())); }


    @Test
    public void shouldMoveRedArcherFrom20To21() {
        assertThat("no unit is present at 2,1 before move", game.getUnitAt(new Position(2,1)), is(nullValue()));
        game.moveUnit(new Position(2,0), (new Position(2,1)));
        assertThat("Red archer has moved to tile (2,1)", game.getUnitAt(new Position(2,1)), is(notNullValue()));
    }

    @Test
    public void redCityShouldProduceALegionAfter3Rounds() {
        game.changeProductionInCityAt(new Position(1,1), GameConstants.LEGION);
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        game.endOfTurn();
        assertThat("Should be a Legion on city tile after producing", game.getUnitAt(new Position(1,1)).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void testThatLoggingCanBeTurnedOff(){
        ((LogDecorator)game).toggleLogging();
        game.endOfTurn();
    }
}
