package hotciv.tasks;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.client.GameProxy;
import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.stub.NullObserver;

public class HotCivStoryTest {
    public static void main(String[] args) {
        int port = 37321;
        String hostname = "localhost";
        System.out.println(
                "=== HotCiv Story Test Client (Socket) (host:"+hostname
                        +") on port " + port);
        System.out.println("=== Testing simple methods ===");

        ClientRequestHandler crh = new SocketClientRequestHandler();

        crh.setServer(hostname, port);
        Requestor requestor = new StandardJSONRequestor(crh);

        Game game = new GameProxy(requestor);
        game.addObserver(new NullObserver());
        System.out.println(" -> Game age                    : " + game.getAge());
        System.out.println(" -> Game winner                 : " + game.getWinner());
        System.out.println(" -> Game PlayerInTurn           : " + game.getPlayerInTurn());
        System.out.println(" -> Game move (2,0)-(3,0)   : " + game.moveUnit(new Position(2,0), new Position(3,0)));
        game.endOfTurn();
        System.out.println("PlayerInTurn after calling endOfTurn: " + game.getPlayerInTurn());

    }

}
