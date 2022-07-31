package hotciv.marshall;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.client.OperationNames;
import hotciv.framework.City;
import hotciv.framework.Game;

public class CityInvoker implements Invoker {
    private final Gson gson;
    private final City city;

    public CityInvoker(City city) {
        this.city = city;
        this.gson = new Gson();
    }

    @Override
    public String handleRequest(String request) {

        RequestObject requestObject =
                gson.fromJson(request, RequestObject.class);

        switch (requestObject.getOperationName()) {
            case OperationNames.GET_OWNER:
                return gson.toJson(new ReplyObject(200, gson.toJson(city.getOwner())));
            case OperationNames.GET_SIZE:
                return gson.toJson(new ReplyObject(200, gson.toJson(city.getSize())));
            case OperationNames.GET_TREASURY:
                return gson.toJson(new ReplyObject(200, gson.toJson(city.getTreasury())));
            case OperationNames.GET_PRODUCTION:
                return gson.toJson(new ReplyObject(200, gson.toJson(city.getProduction())));
            case OperationNames.GET_WORKFORCE_FOCUS:
                return gson.toJson(new ReplyObject(200, gson.toJson(city.getWorkforceFocus())));
            default:
                return gson.toJson(new ReplyObject(500, "Error"));
        }
    }
}
