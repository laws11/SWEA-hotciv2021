package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.AlphaCivFactory;
import hotciv.standard.Factories.FractalTestFactory;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FractalSetupTest {
    private Game game;
    private GameFactory factory = new FractalTestFactory();

    /** Fixture for alphaciv testing. */
    @BeforeEach
    public void setUp() throws FileNotFoundException { game = new GameImpl(factory); }

    @Test
    public void smokeTestForFractalGenerator() throws FileNotFoundException {
        Set<String> arr = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            game = new GameImpl(factory);
            arr.add(game.getTileAt(new Position(0, 0)).getTypeString());
        }
        assertThat(arr.size(), is(not(1)));
    }
}
