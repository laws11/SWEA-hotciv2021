package hotciv.standard;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.client.GameProxy;
import hotciv.client.UnitProxy;
import hotciv.framework.*;

import hotciv.marshall.GameInvoker;
import hotciv.marshall.UnitInvoker;
import hotciv.standard.Factories.AlphaCivFactory;
import hotciv.stub.StubClientRequestHandler;
import hotciv.stub.NullObserver;
import hotciv.stub.StubBrokerUnit;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBrokerUnit {
    private Unit unit;

    @BeforeEach
    public void setUp() {
        Unit servant = new StubBrokerUnit();

        Invoker invoker = new UnitInvoker(servant);

        ClientRequestHandler crh = new StubClientRequestHandler(invoker);

        Requestor requestor = new StandardJSONRequestor(crh);

        unit = new UnitProxy(requestor);

    }

    @Test
    public void shouldGetTypeString() {
        assertThat(unit.getTypeString(), is(GameConstants.ARCHER));
    }

    @Test
    public void shouldGetOwner() {
        assertThat(unit.getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldGetMoveCount() {
        assertThat(unit.getMoveCount(), is(100));
    }

    @Test
    public void shouldGetDefensiveStrength() {
        assertThat(unit.getDefensiveStrength(), is(420));
    }

    @Test
    public void shouldGetAttackingStrength() {
        assertThat(unit.getAttackingStrength(), is(1337));
    }
}
