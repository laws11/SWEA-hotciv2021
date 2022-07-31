package hotciv.standard;

import hotciv.framework.*;

import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/** Skeleton implementation of HotCiv.
 
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

public class GameImpl implements Game {
  private Player playerInTurn = Player.RED;
  private Map<Position, Unit> unitMap;
  private Map<Position, City> cityMap;
  private Map<Position, Tile> worldMap;
  private int currentAge = -4000;
  private int currentRound = 1;
  private AgeStrategy ageStrategy;
  private WinStrategy winStrategy;
  private UnitActionStrategy unitActionStrategy;
  private SetupStrategy setupStrategy;
  private AttackStrategy attackStrategy;
  private LegalTileStrategy legalTileStrategy;
  private ArrayList<GameObserver> gameObservers = new ArrayList<>();

  public GameImpl( GameFactory gameFactory) throws FileNotFoundException {
      this.winStrategy = gameFactory.createWinStrategy();
      this.ageStrategy = gameFactory.createAgeStrategy();
      this.unitActionStrategy = gameFactory.createUnitActionStrategy();
      this.attackStrategy = gameFactory.createAttackStrategy();
      this.legalTileStrategy = gameFactory.createLegalTileStrategy();
      worldMap = gameFactory.createSetupStrategy().getWorldMap();
      unitMap = gameFactory.createSetupStrategy().getUnitMap();
      cityMap = gameFactory.createSetupStrategy().getCityMap();
    }



  public Tile getTileAt( Position p ) { return worldMap.get(p); }
  public Unit getUnitAt( Position p ) { return unitMap.get(p); }
  public City getCityAt( Position p ) { return cityMap.get(p); }
  public Player getPlayerInTurn() { return playerInTurn; }
  public Player getWinner() { return winStrategy.getWinner(this); }
  public int getAge() { return currentAge; }
  public int getRound() {return currentRound;}


  public boolean moveUnit( Position from, Position to ) {
    if(!legalMove(from, to)) {
      notifyWorldChangeAt(from);
      return false;
    }
    if(!conquerTile(from, to)) return false;
    actualMoveOfUnit(from, to);
    handleAttackCity(to);

    //notifyWorldChangeAt(to);
    //notifyWorldChangeAt(from);
    return true;
  }

  private boolean conquerTile(Position from, Position to) {
      boolean enemyUnitOnToTile = getUnitAt(to) != null;
      if(!enemyUnitOnToTile) return true;
      boolean playerInTurnWonAttack = attackStrategy.attackerWins(this, from, to);
      if (!playerInTurnWonAttack) return false;
      winStrategy.incrementSuccessfulAttacks(playerInTurn);
      return true;
  }

  private void handleAttackCity(Position to) {
    boolean isAttackingCity = cityAttack(to);
    if (isAttackingCity) {
      ((CityImpl) getCityAt(to)).setOwner(playerInTurn);
    }
  }

  private void actualMoveOfUnit(Position from, Position to) {
    unitMap.put(to, getUnitAt(from));
    unitMap.remove(from);
    ((UnitImpl)getUnitAt(to)).decreaseMoveCount();
    notifyWorldChangeAt(from);
    notifyWorldChangeAt(to);

  }

  /** Helper method that checks the 8 tile neighboorhood of a tile in the game
   */
  private boolean isIn8Neighbourhood (Position from, Position to){
    boolean isInNeighbourHood = false;
    for (Position p : Utility.get8neighborhoodOf(from)) {
      if (p.equals(to)) {
        isInNeighbourHood = true;
        break;
      }
    }
    return isInNeighbourHood;
  }
  /** Handles check of whether a move is legal
   *Checks that player in turn own the unit that is being moved
   * Checks that the tile is legal to move to
   * Checks if a unit attacks a city and changes owner if it's the case
   * Checks that tile is free and not occupied by own unit already
   * Checks that move is only 1 tile
   * Checks that unit has not moved already
   * */
  private boolean legalMove(Position from, Position to) {
    boolean playerInTurnIsMover = playerInTurn.equals(getUnitAt(from).getOwner());
    if(!playerInTurnIsMover) {
      return false;
    }

    
    boolean tileIsLegal = legalTileStrategy.legalTile(from, to, this);
    if(!tileIsLegal) {
      return false;
    }

    boolean unitAtPositionIsSameOwner = unitMap.get(to) != null && unitMap.get(to).getOwner().equals(playerInTurn);
    if (unitAtPositionIsSameOwner) {
      return false;
    }

    boolean moveToPositionIsIn8Neighboorhood = isIn8Neighbourhood(from, to);
    if (!moveToPositionIsIn8Neighboorhood) {
      return false;
    }

    boolean moveCountIsZero = (getUnitAt(from)).getMoveCount() == 0;
    if(moveCountIsZero){
      return false;
    }

    return true;
  }



  /** Check if a city is attacked
   * Checks that there is a city at position and it's not owned by player in turn
   */
  private boolean cityAttack(Position to) {
    boolean thereIsACity = getCityAt(to) != null;
    if(!thereIsACity) return false;
    boolean cityIsOwnedByPlayerInTurn = getCityAt(to).getOwner() == playerInTurn;
    if(cityIsOwnedByPlayerInTurn) return false;
    return true;
  }
  /** Handles end og turn
   * Changes the player in turn
   * Calls the end of round after 2 turns
   * */
  public void endOfTurn() {
    if (playerInTurn.equals(Player.RED)) {playerInTurn = Player.BLUE;}
    else {
      playerInTurn = Player.RED;
      endOfRound();
    }
    notifyTurnEnd(playerInTurn, getAge());
  }

  /** Handles end of round.
   * Check all cities if they have enough production to produce a unit
   * Changes treasury if unit was produced
   * Changes game age
   * resets movecount of units
   * sets gamewinner if applicable
   * */
  public void endOfRound() {
    endOfRoundCityProduction();
    resetMoveCountOnAllUnits();
    shiftToNextAge();
    currentRound ++;
    for (Position position: unitMap.keySet()) {
      notifyWorldChangeAt(position);
    }
  }

  private void shiftToNextAge() {
    int newCurrentAge = ageStrategy.increaseAge(currentAge);
    currentAge = newCurrentAge;
  }

  private void resetMoveCountOnAllUnits() {
    for (Unit unit : unitMap.values()) {
      ((UnitImpl) unit).resetMoveCount();
    }
  }

  private void endOfRoundCityProduction() {
    cityMap.forEach((position, city) -> {
      ((CityImpl)city).endOfRoundProduction(6);
      if (city.getTreasury() >= ((CityImpl)city).getUnitPrice()){
        placeProducedUnit(position, city.getProduction());
        ((CityImpl)city).setTreasury(city.getTreasury()-((CityImpl) city).getUnitPrice());

      }
    });
  }

  /** Handles unit placement on map. Checks if city is empty and places unit on city. I not empty iterates the
   8 tile neighboorhood and places units starting from topleft corner if tiles are legal. */
  private void placeProducedUnit(Position cityPosition, String unitType) {
    boolean unitAtPosition = getUnitAt(cityPosition) != null;
    boolean sandworm = unitType.equals(GameConstants.SANDWORM);
    if(!unitAtPosition){
      if (sandworm) {
        boolean tileIsDesert = getTileAt(cityPosition).getTypeString().equals(GameConstants.DESERT);
        if (tileIsDesert) {
          unitMap.put(cityPosition, new UnitImpl(unitType, getCityAt(cityPosition).getOwner()));
          notifyWorldChangeAt(cityPosition);
          return;
        }
      }
      else {unitMap.put(cityPosition, new UnitImpl(unitType, getCityAt(cityPosition).getOwner()));
        notifyWorldChangeAt(cityPosition);
        return;
      }
    }

    for (Position p : Utility.get8neighborhoodOf(cityPosition)) {
      boolean unitOnPosition = getUnitAt(p) != null;
      boolean tileIsMountain = getTileAt(p).getTypeString().equals(GameConstants.MOUNTAINS);
      boolean tileIsOcean = getTileAt(p).getTypeString().equals(GameConstants.OCEANS);
      boolean tileIsDessert = getTileAt(p).getTypeString().equals(GameConstants.DESERT);
      if (sandworm) {
        if (tileIsDessert) {
          unitMap.put(p, new UnitImpl(unitType, getCityAt(cityPosition).getOwner()));
          notifyWorldChangeAt(p);
          return;
        }
      }
      else if (!unitOnPosition && !tileIsMountain && !tileIsOcean) {
          unitMap.put(p, new UnitImpl(unitType, getCityAt(cityPosition).getOwner()));
          notifyWorldChangeAt(p);
          return;
      }
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  /** Changes production of a City
   * Checks if player in turn owns city where production is changed
   * Set the production of the city*/
  public void changeProductionInCityAt( Position p, String unitType ) {
    boolean ownerIsPlayerInTurn = getCityAt(p).getOwner().equals(playerInTurn);
    if (ownerIsPlayerInTurn) {
      ((CityImpl) getCityAt(p)).setUnitProduction(unitType);
    }

  }
  public void performUnitActionAt( Position p ) {
    boolean playerInTurnIsOwner = getUnitAt(p).getOwner().equals(playerInTurn);
    if (playerInTurnIsOwner)
        unitActionStrategy.performActionAt(getUnitAt(p), this, p);
  }

  @Override
  public void addObserver(GameObserver observer) {
    gameObservers.add(observer);
  }

  @Override
  public void setTileFocus(Position position) {
    for (GameObserver gameObserver: gameObservers){
      gameObserver.tileFocusChangedAt(position);
    }
  }

  public ArrayList<GameObserver> getGameObservers() {
    return gameObservers;
  }

  public void notifyTurnEnd(Player nextPlayer, int age) {
    for (GameObserver gameObserver: gameObservers) {
      gameObserver.turnEnds(nextPlayer, age);
    }

  }

  public void notifyWorldChangeAt(Position pos) {
    for (GameObserver gameObserver: gameObservers) {
      gameObserver.worldChangedAt(pos);
    }
  }




  public Collection<City> getCityMap() {
    return cityMap.values();
  }

  public void buildCity(Position p, Player playerInTurn) {
    cityMap.put(p, new CityImpl(playerInTurn));
    unitMap.remove(p);
  }

  public void fortifyArcher(Position p, Game game) {
    ((UnitImpl)game.getUnitAt(p)).fortify();
  }

  public void removeUnitAt(Position p) {
    unitMap.remove(p);
    notifyWorldChangeAt(p);
  }
}
