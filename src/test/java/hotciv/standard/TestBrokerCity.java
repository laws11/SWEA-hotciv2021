package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.client.CityProxy;
import hotciv.client.GameProxy;
import hotciv.framework.*;

import hotciv.marshall.CityInvoker;
import hotciv.marshall.GameInvoker;
import hotciv.standard.Factories.AlphaCivFactory;

import hotciv.stub.StubClientRequestHandler;
import hotciv.stub.StubBrokerCity;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerCity {
    private City city;

    @BeforeEach
    public void setUp() {
        City servant = new StubBrokerCity();

        Invoker invoker = new CityInvoker(servant);

        ClientRequestHandler crh = new StubClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        city = new CityProxy(requestor);

    }

    @Test
    public void shouldGetOwner() {
        assertThat(city.getOwner(), is(Player.RED));
    }

    @Test
    public void shouldGetSize() {
        assertThat(city.getSize(), is(2));
    }

    @Test
    public void shouldGetTreasury() {
        assertThat(city.getTreasury(), is(40));
    }

    @Test
    public void shouldGetProduction() {
        assertThat(city.getProduction(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldGetWorkforceFocus() {
        assertThat(city.getWorkforceFocus(), is(GameConstants.foodFocus));
    }
}
