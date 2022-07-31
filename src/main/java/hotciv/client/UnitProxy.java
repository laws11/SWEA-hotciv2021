package hotciv.client;

import frds.broker.Requestor;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit {

    private final Requestor requestor;

    public UnitProxy(Requestor requestor) {
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        return requestor.sendRequestAndAwaitReply("unit", OperationNames.GET_TYPE_STRING, String.class);
    }

    @Override
    public Player getOwner() {
        return requestor.sendRequestAndAwaitReply("unit", OperationNames.GET_OWNER, Player.class);
    }

    @Override
    public int getMoveCount() {

        return requestor.sendRequestAndAwaitReply("unit", OperationNames.GET_MOVE_COUNT, int.class);
    }

    @Override
    public int getDefensiveStrength() {
        return requestor.sendRequestAndAwaitReply("unit", OperationNames.GET_DEFENSIVE_STRENGTH, int.class);
    }

    @Override
    public int getAttackingStrength() {
        return requestor.sendRequestAndAwaitReply("unit", OperationNames.GET_ATTACKING_STRENGTH, int.class);
    }
}
