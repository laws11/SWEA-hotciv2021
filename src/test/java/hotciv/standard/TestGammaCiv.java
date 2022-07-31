package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.GammaCivFactory;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGammaCiv {
    private Game game;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        game = new GameImpl(new GammaCivFactory());
    }

    @Test
    public void RedSettlerShouldCreateCity() {
        game.performUnitActionAt(new Position(4,3));
        assertThat("Red settler can create a city at 4,3", game.getCityAt(new Position(4,3)), is(notNullValue()));
    }

    @Test
    public void archerCanFortifyAndDoubleDefence() {
        game.performUnitActionAt(new Position(2,0));
        assertThat("Red archer at 2,0 can fortify and double its defence", game.getUnitAt(new Position(2,0)).getDefensiveStrength(), is(6));
    }
}
