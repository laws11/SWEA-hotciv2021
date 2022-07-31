package hotciv.marshall;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.client.OperationNames;
import hotciv.framework.Unit;

public class UnitInvoker implements Invoker {
    private final Gson gson;
    private final Unit unit;

    public UnitInvoker(Unit unit) {
        this.unit = unit;
        this.gson = new Gson();
    }
    @Override
    public String handleRequest(String request) {
        RequestObject requestObject =
                gson.fromJson(request, RequestObject.class);

        switch (requestObject.getOperationName()) {
            case OperationNames.GET_TYPE_STRING:
                return gson.toJson(new ReplyObject(200, gson.toJson(unit.getTypeString())));
            case OperationNames.GET_OWNER:
                return gson.toJson(new ReplyObject(200, gson.toJson(unit.getOwner())));
            case OperationNames.GET_MOVE_COUNT:
                return gson.toJson(new ReplyObject(200, gson.toJson(unit.getMoveCount())));
            case OperationNames.GET_DEFENSIVE_STRENGTH:
                return gson.toJson(new ReplyObject(200, gson.toJson(unit.getDefensiveStrength())));
            case OperationNames.GET_ATTACKING_STRENGTH:
                return gson.toJson(new ReplyObject(200, gson.toJson(unit.getAttackingStrength())));
            default:
                return gson.toJson(new ReplyObject(500, "Error"));
        }


    }

}
