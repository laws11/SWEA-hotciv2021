package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.client.GameProxy;
import hotciv.framework.*;

import hotciv.marshall.GameInvoker;
import hotciv.standard.Factories.AlphaCivFactory;
import hotciv.stub.FakeObjectGame;
import hotciv.stub.StubClientRequestHandler;
import hotciv.stub.NullObserver;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerGame {
    private Game game;

    @BeforeEach
    public void setUp() {
        Game servant = new FakeObjectGame();
        GameObserver nullObserver = new NullObserver();
        servant.addObserver(nullObserver);

        Invoker invoker = new GameInvoker(servant);

        ClientRequestHandler crh = new StubClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        game = new GameProxy(requestor);
        game.addObserver(nullObserver);
    }

    @Test
    public void shouldGetAge() {
        assertThat(game.getAge(), is(-4000));
    }

    @Test
    public void shouldBeRound1(){
        assertThat(game.getRound(), is(1));
    }

    @Test
    public void shouldBeBluePlayer(){
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedWinner(){
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void shouldBePlainsAt1010(){
        assertThat(game.getTileAt(new Position(10,10)).getTypeString(), is(GameConstants.PLAINS));
    }

    @Test
    public void shouldBeAbleToMoveRedArcherFrom20To30(){
        assertThat(game.moveUnit(new Position(2,0), new Position(3,0)), is(true));
    }
}
