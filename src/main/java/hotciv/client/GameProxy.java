package hotciv.client;

import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.TileImpl;

import java.util.ArrayList;

public class GameProxy implements Game {
    private final Requestor requestor;
    private ArrayList<GameObserver> proxyObservers = new ArrayList<>();

    public GameProxy(Requestor requestor) {
        this.requestor = requestor;

    }

    @Override
    public int getRound() {
        return requestor.sendRequestAndAwaitReply("game", OperationNames.GET_ROUND, int.class);
    }

    @Override
    public Tile getTileAt(Position p) {
        String typeString = requestor.sendRequestAndAwaitReply("game", OperationNames.GET_TILE_AT, String.class, p);
        return new TileImpl(typeString);
    }

    @Override
    public Unit getUnitAt(Position p) {
        return null;
    }

    @Override
    public City getCityAt(Position p) {
        return null;
    }

    @Override
    public Player getPlayerInTurn() {
        return requestor.sendRequestAndAwaitReply("game", OperationNames.PLAYER_IN_TURN, Player.class);
    }

    @Override
    public Player getWinner() {
        return requestor.sendRequestAndAwaitReply("game", OperationNames.GET_WINNER, Player.class);
    }

    @Override
    public int getAge() {
        return requestor.sendRequestAndAwaitReply("game", OperationNames.GET_AGE, int.class);
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        notifyWorldChangeAt(from);
        notifyWorldChangeAt(to);
        return requestor.sendRequestAndAwaitReply("game", OperationNames.MOVE_UNIT, boolean.class, from.getRow(), from.getColumn(), to.getRow(), to.getColumn());

    }

    @Override
    public void endOfTurn() {
        notifyTurnEnd(getPlayerInTurn(), getAge());
        requestor.sendRequestAndAwaitReply("game", OperationNames.END_OF_TURN, void.class);
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        requestor.sendRequestAndAwaitReply("game", OperationNames.CHANGE_WORK_FOCUS_IN_CITY_AT, void.class, balance, p.getRow(), p.getColumn());
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        requestor.sendRequestAndAwaitReply("game", OperationNames.CHANGE_PRODUCTION_IN_CITY_AT, void.class, unitType, p.getRow(), p.getColumn());
    }

    @Override
    public void performUnitActionAt(Position p) {
        requestor.sendRequestAndAwaitReply("game", OperationNames.PERFORM_UNIT_ACTION_AT, void.class, p);
    }

    @Override
    public void addObserver(GameObserver observer) {
        proxyObservers.add(observer);
    }

    @Override
    public void setTileFocus(Position position) {
        for (GameObserver proxyObserver: proxyObservers){
            proxyObserver.tileFocusChangedAt(position);
        }
    }

    public void notifyTurnEnd(Player nextPlayer, int age) {
        for (GameObserver proxyObserver: proxyObservers) {
            proxyObserver.turnEnds(nextPlayer, age);
        }

    }

    public void notifyWorldChangeAt(Position pos) {
        for (GameObserver proxyObserver: proxyObservers) {
            proxyObserver.worldChangedAt(pos);
        }
    }
}
