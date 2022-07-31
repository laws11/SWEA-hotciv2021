package hotciv.standard;

import hotciv.framework.*;

public class LogDecorator implements Game {
    private Game loggingGame;
    private boolean logging;

    public LogDecorator(Game game) {
        loggingGame = game;
        logging = true;

    }




    @Override
    public Tile getTileAt(Position p) {
        return loggingGame.getTileAt(p);
    }

    @Override
    public int getRound() {
        return loggingGame.getRound();
    }

    @Override
    public Unit getUnitAt(Position p) {
        return loggingGame.getUnitAt(p);
    }

    @Override
    public City getCityAt(Position p) {
        return loggingGame.getCityAt(p);
    }

    @Override
    public Player getPlayerInTurn() {
        return loggingGame.getPlayerInTurn();
    }

    @Override
    public Player getWinner() {
        return loggingGame.getWinner();
    }

    @Override
    public int getAge() {
        return loggingGame.getAge();
    }

    @Override
    public boolean moveUnit(Position from, Position to) {
        boolean moveIsValid = loggingGame.moveUnit(from, to);
        if (moveIsValid && logging) {
            System.out.println(loggingGame.getPlayerInTurn() + " moves " + loggingGame.getUnitAt(to).getTypeString() + " from " + from + " to " + to);
            return true;
        }
        return moveIsValid;
    }

    @Override
    public void endOfTurn() {
        if (logging) {
            System.out.println(loggingGame.getPlayerInTurn() + " ends turn");
        }
        loggingGame.endOfTurn();
    }

    @Override
    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        if (logging) {
            System.out.println(loggingGame.getPlayerInTurn() + " changes workforce focus in city at " + p + " to " + balance);
        }
        loggingGame.changeWorkForceFocusInCityAt(p, balance);
    }

    @Override
    public void changeProductionInCityAt(Position p, String unitType) {
        if (logging) {
            System.out.println(loggingGame.getPlayerInTurn() + " changes production in city at " + p + " to " + unitType);
        }
        loggingGame.changeProductionInCityAt(p, unitType);
    }

    @Override
    public void performUnitActionAt(Position p) {
        if (logging) {
            System.out.println(loggingGame.getPlayerInTurn() + " performs unit action at " + p);
        }
        loggingGame.performUnitActionAt(p);
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }

    public void toggleLogging(){
        if (logging){
            logging = false;
        }
        else {
            logging = true;
        }
    }

}
