package hotciv.stub;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.view.CivDrawing;

import java.util.ArrayList;
import java.util.Collection;

public class StubObserver implements GameObserver {
    private ArrayList<Position> positionNotification = new ArrayList<>();
    private ArrayList<Player> playerNotification = new ArrayList<>();
    private ArrayList<Integer> ageNotification = new ArrayList<>();

    @Override
    public void worldChangedAt(Position pos) {
        positionNotification.add(pos);

    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {
        playerNotification.add(nextPlayer);
        ageNotification.add(age);

    }

    @Override
    public void tileFocusChangedAt(Position position) {

    }

    public ArrayList<Position> getPositionNotification() {
        return positionNotification;
    }

    public ArrayList<Player> getPlayerNotification() {
        return playerNotification;
    }
}
