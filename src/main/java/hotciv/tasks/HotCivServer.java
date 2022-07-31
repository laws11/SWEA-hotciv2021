package hotciv.tasks;

import frds.broker.Invoker;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.marshall.GameInvoker;
import hotciv.stub.FakeObjectGame;
import hotciv.stub.NullObserver;
import hotciv.stub.FakeObjectGame;

import java.io.FileNotFoundException;


public class HotCivServer {

    public static void main(String[] args) throws FileNotFoundException {
        int port = 37321;
        Game gameServant = new FakeObjectGame();
        GameObserver nullObserver = new NullObserver();
        gameServant.addObserver(nullObserver);

        Invoker invoker = new GameInvoker(gameServant);

        SocketServerRequestHandler ssrh = new SocketServerRequestHandler();
        ssrh.setPortAndInvoker(port, invoker);
        ssrh.start();
    }
}
