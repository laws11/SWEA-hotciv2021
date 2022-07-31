package hotciv.marshall;

import com.google.gson.*;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.client.OperationNames;
import hotciv.framework.Game;
import hotciv.framework.Position;


public class GameInvoker implements Invoker {
    private final Gson gson;
    private final Game game;

    public GameInvoker(Game game) {
        this.game = game;
        this.gson = new Gson();
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject =
                gson.fromJson(request, RequestObject.class);
        JsonArray array = new JsonParser().parse(requestObject.getPayload()).getAsJsonArray();

        switch (requestObject.getOperationName()) {
            case OperationNames.GET_AGE:
                return gson.toJson(new ReplyObject(200, gson.toJson(game.getAge())));
            case OperationNames.GET_ROUND:
                return gson.toJson(new ReplyObject(200, gson.toJson(game.getRound())));
            case OperationNames.END_OF_TURN:
                game.endOfTurn();
                return gson.toJson(new ReplyObject(200, ""));
            case OperationNames.PLAYER_IN_TURN:
                return gson.toJson(new ReplyObject(200, gson.toJson(game.getPlayerInTurn())));
            case OperationNames.GET_WINNER:
                return gson.toJson(new ReplyObject(200, gson.toJson(game.getWinner())));
            case OperationNames.GET_TILE_AT:
                Position[] p = gson.fromJson(requestObject.getPayload(),Position[].class);
                return gson.toJson(new ReplyObject(200,gson.toJson(game.getTileAt(p[0]).getTypeString())));
            case OperationNames.PERFORM_UNIT_ACTION_AT:
                Position[] pos = gson.fromJson(requestObject.getPayload(),Position[].class);
                game.performUnitActionAt(pos[0]);
                return gson.toJson(new ReplyObject(200,""));
            case OperationNames.CHANGE_WORK_FOCUS_IN_CITY_AT:
                String balance = gson.fromJson(array.get(0), String.class);
                Position focusPos = new Position(gson.fromJson(array.get(1), int.class), gson.fromJson(array.get(2), int.class));
                game.changeWorkForceFocusInCityAt(focusPos, balance);
                return gson.toJson(new ReplyObject(200, ""));
            case OperationNames.CHANGE_PRODUCTION_IN_CITY_AT:
                String unitType = gson.fromJson(array.get(0), String.class);
                Position prodPos = new Position(gson.fromJson(array.get(1), int.class), gson.fromJson(array.get(2), int.class));
                game.changeProductionInCityAt(prodPos, unitType);
                return gson.toJson(new ReplyObject(200, ""));
            case OperationNames.MOVE_UNIT:
                Position from = new Position(gson.fromJson(array.get(0), int.class), gson.fromJson(array.get(1), int.class));
                Position to = new Position(gson.fromJson(array.get(2), int.class), gson.fromJson(array.get(3), int.class));
                return gson.toJson(new ReplyObject(200, gson.toJson(game.moveUnit(from, to))));
            default:
                return gson.toJson(new ReplyObject(500, "Error"));
        }


    }

}
