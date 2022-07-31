package hotciv.client;

import frds.broker.Requestor;
import hotciv.framework.City;
import hotciv.framework.Player;

public class CityProxy implements City {

    private final Requestor requestor;

    public CityProxy(Requestor requestor) {
        this.requestor = requestor;
    }
    @Override
    public Player getOwner() {
        return requestor.sendRequestAndAwaitReply("city", OperationNames.GET_OWNER, Player.class);
    }

    @Override
    public int getSize() {
        return requestor.sendRequestAndAwaitReply("city", OperationNames.GET_SIZE, int.class);
    }

    @Override
    public int getTreasury() {
        return requestor.sendRequestAndAwaitReply("city", OperationNames.GET_TREASURY, int.class);
    }

    @Override
    public String getProduction() {
        return requestor.sendRequestAndAwaitReply("city", OperationNames.GET_PRODUCTION, String.class);
    }

    @Override
    public String getWorkforceFocus() {
        return requestor.sendRequestAndAwaitReply("city", OperationNames.GET_WORKFORCE_FOCUS, String.class);
    }
}
