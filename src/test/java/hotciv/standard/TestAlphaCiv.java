package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.AlphaCivFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.FileNotFoundException;
import java.util.*;

/** Skeleton class for AlphaCiv test cases

   Updated Aug 2020 for JUnit 5 includes
   Updated Oct 2015 for using Hamcrest matchers

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
public class TestAlphaCiv {
  private Game game;

  /** Fixture for alphaciv testing. */
  @BeforeEach
  public void setUp() throws FileNotFoundException {
    game = new GameImpl(new AlphaCivFactory());

  }


  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void shouldBeBlueAfterRed() {
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  @Test
  public void ReadStartsANewRound() {
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  @Test
  public void citiesPopulationSizeIsAlways1() {
    assertThat(game.getCityAt(new Position(1,1)).getSize(),is(1));
  }

  @Test
  public void citiesProduce6AfterFirstRoundEnds() {
    game.endOfTurn();
    game.endOfTurn();
    assertThat("After end of first round city should have produced 6", game.getCityAt(new Position(1, 1)).getTreasury(), is(6));
  }

  /* Tror vi skal tage den her ud, nu hvor vi automatisk producerer enheder, så har byen jo produceret en archer efter to runder og dermed har den kun 2 production
  @Test
  public void treasuryShouldHave12ProductionAfterSecondRound() {
    for(int i=0; i<4; i++) {
      game.endOfTurn();
    }
    assertThat("After 2nd round treasury should have 12 production", game.getCityAt(new Position(1, 1)).getTreasury(), is(12));
  }
  */

  @Test
    public void thereArePlainsOnMostTiles() {
    assertThat("Tile in position 0,0 is plain", game.getTileAt(new Position(0,0)).getTypeString(),is(GameConstants.PLAINS));
    assertThat("Tile in position 7,4 is plain", game.getTileAt(new Position(7,4)).getTypeString(),is(GameConstants.PLAINS));
    assertThat("Tile in position 15,15 is plain", game.getTileAt(new Position(15,15)).getTypeString(),is(GameConstants.PLAINS));
  }

  @Test
  public void thereIsOceanAt1_0() {
    assertThat("Tile in position 1,0 is ocean", game.getTileAt(new Position(1,0)).getTypeString(),is(GameConstants.OCEANS));
  }

  @Test
  public void thereIsHillsAt0_1() {
    assertThat("Tile in position 0,1 is hills", game.getTileAt(new Position(0,1)).getTypeString(),is(GameConstants.HILLS));
  }

  @Test
  public void thereIsMountainsAt2_2() {
    assertThat("Tile in position 2,2 is mountains", game.getTileAt(new Position(2,2)).getTypeString(),is(GameConstants.MOUNTAINS));
  }

  @Test
  public void shouldBeRedCityAt1_1() {
    assertThat("Red city should be at 1,1", game.getCityAt(new Position(1,1)).getOwner(), is(Player.RED));
  }

  @Test
  public void shouldBeBlueCityAt4_1() {
    assertThat("Blue city should be at 4,1", game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
  }

  @Test
  public void shouldBeRedSettlerAt4_3() {
    assertThat("Red settler is at 4,3", game.getUnitAt(new Position(4,3)).getOwner(), is(Player.RED));
    assertThat("The red unit is a settler", game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
  }

  @Test
  public void shouldBlueLegionAt3_2() {
    assertThat("Blue unit at 3,2", game.getUnitAt(new Position(3,2)).getOwner(), is(Player.BLUE));
    assertThat("The blue unit is a legion", game.getUnitAt(new Position(3,2)).getTypeString(), is(GameConstants.LEGION));
  }

  @Test
  public void shouldStartIn4000BC() {
    assertThat("Game start in 4000 BC", game.getAge(),is(-4000));
  }

  @Test
  public void shouldBe3800BCAfterTwoRounds() {
    for(int i=0; i<4; i++) {
      game.endOfTurn();
    }
    assertThat("Age is 3800 BC after two rounds", game.getAge(),is(-3800));
  }

  @Test
  public void shouldBeRedArcherAt2_0() {
    assertThat("There should be an archer at 2,0", game.getUnitAt(new Position(2,0)).getTypeString(), is(GameConstants.ARCHER));
    assertThat("Archer at 2,0 should belong to red player", game.getUnitAt(new Position(2,0)).getOwner(), is(Player.RED));
  }

  @Test
  public void redShouldWinInYear3000BC() {
    for(int i=0; i<20; i++) {
      game.endOfTurn();
    }
    assertThat("Year should be 3000 BC", game.getAge(), is(-3000));
    assertThat("Red should win the game in year 3000 BC", game.getWinner(), is(Player.RED));
  }

  @Test
  public void redShouldNotWinIn4000BC() {
    assertThat("Year should be 4000 BC", game.getAge(), is(-4000));
    assertThat("Red should NOT win the game in year 4000 BC", game.getWinner(), is(not(Player.RED)));
  }

  @Test
  public void redShouldBeAbleToMoveArcherFrom2_0To3_0() {
    game.moveUnit(new Position(2,0), new Position(3,0));
    assertThat("Red should be the owner of the Archer", game.getUnitAt(new Position(3,0)).getOwner(), is(Player.RED));
    assertThat("Red should be able to move his archer from pos 2,0 to 3,0",game.getUnitAt(new Position(3,0)).getTypeString(), is(GameConstants.ARCHER));
  }

  @Test
  public void blueShouldNotBeAbleToMoveRedUnit() {
    game.endOfTurn();
    assertThat("Blue player is in turn", game.getPlayerInTurn(), is(Player.BLUE));
    assertThat("Blue should not be able to move red archer from pos 2,0 to 3,0",game.moveUnit(new Position(2,0), new Position(3,0)), is(false));
  }

  @Test
  public void redShouldNotBeAbleToMoveBlueUnit() {
    assertThat("Red player is in turn", game.getPlayerInTurn(), is(Player.RED));
    assertThat("Red should not be able to move blue legion from pos 3,2 to 3,1",game.moveUnit(new Position(3,2), new Position(3,1)), is(false));
  }

  @Test
  public void unitShouldNotBeAbleToMoveToAMountain() {
    game.endOfTurn();
    assertThat("Blue legian at 3,2 can't move to mountain at 2,2",game.moveUnit(new Position(3,2), new Position(2,2)), is(false));
  }

  @Test
  public void unitShouldNotBeAbleToMoveToAnOcean() {
    assertThat("Red archer at 2,0 can't move to ocean at 1,0",game.moveUnit(new Position(2,0), new Position(1,0)), is(false));
  }

  @Test
  public void redArcherShouldDestroyBlueLegionWithAttack() {
    game.moveUnit(new Position(2,0), new Position(3,1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(3,1), new Position(3,2));
    assertThat("Red unit has actually moved and are not still at original position", game.getUnitAt(new Position(2,0)), is(nullValue()));
    assertThat("Red player should own the archer", game.getUnitAt(new Position(3,2)).getOwner(), is(Player.RED));
    assertThat("Red archer should win and kill blue legion and take it's place", game.getUnitAt(new Position(3,2)).getTypeString() , is(GameConstants.ARCHER));
  }

  @Test
  public void blueLegionShouldDestroyRedArcherWithAttack() {
    game.endOfTurn();
    game.moveUnit(new Position(3,2), new Position(3,1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(3,1), new Position(2,0));
    assertThat("Blue unit has actually moved and are not still at original position", game.getUnitAt(new Position(3,2)), is(nullValue()));
    assertThat("Blue player should own the Legion", game.getUnitAt(new Position(2,0)).getOwner(), is(Player.BLUE));
    assertThat("Blue legion should win and kill red archer and take it's place", game.getUnitAt(new Position(2,0)).getTypeString() , is(GameConstants.LEGION));
  }

  @Test
  public void blueShouldTakeRedCityWhenAttacking() {
    game.endOfTurn();
    game.moveUnit(new Position(3,2), new Position(2,1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(2,1), new Position(1,1));
    assertThat("Blue owns legion on top of city", game.getUnitAt(new Position(1, 1)).getOwner() , is(Player.BLUE));
    assertThat("legion is on top of city", game.getUnitAt(new Position(1,1)).getTypeString(), is(GameConstants.LEGION));
    assertThat("Blue legion should conquer red city when attacking", game.getCityAt(new Position(1,1 )).getOwner(), is(Player.BLUE));
  }

  @Test
  public void cityShouldProduceArcherAfter2Rounds() {
    for(int i=0; i<4; i++) {
      game.endOfTurn();
    }
    assertThat("Red city at 1,1 produce an archer after two rounds", game.getUnitAt(new Position(1,1)).getTypeString(), is(GameConstants.ARCHER));
    assertThat("only one unit is produced", game.getUnitAt(new Position(0, 0)), is(nullValue()));
  }

  @Test
  public void cityShouldProduceLegionAfter3Rounds() {
    ((CityImpl)game.getCityAt(new Position(1,1))).setUnitProduction(GameConstants.LEGION);
    for(int i=0; i<6; i++) {
      game.endOfTurn();
    }
    assertThat("Red city at 1,1 produce a legion after three rounds", game.getUnitAt(new Position(1,1)).getTypeString(), is(GameConstants.LEGION));
  }

  @Test
  public void treasuryShouldDecreaseBy10WhenProducingAnArcher() {
    for(int i=0; i<4; i++) {
      game.endOfTurn();
    }
    assertThat("Red city's treasure is 2 after producing an archer after two rounds", game.getCityAt(new Position(1,1)).getTreasury(), is(2));
  }

  @Test
  public void treasuryShouldDecreaseBy15WhenProducingALegion() {
    ((CityImpl)game.getCityAt(new Position(1,1))).setUnitProduction(GameConstants.LEGION);
    for(int i=0; i<6; i++) {
      game.endOfTurn();
    }
    assertThat("Red city's treasure is 3 after producing a legion after three rounds", game.getCityAt(new Position(1,1)).getTreasury(), is(3));
  }

  @Test
  public void shouldOnlyAllowOneUnitAtATile() {
    game.moveUnit(new Position(2,0), new Position(3,1));
    game.moveUnit(new Position(4,3), new Position(4,2));
    game.endOfTurn();
    game.endOfTurn();
    assertThat("Red archer is not allow to move to 4,2 where red settler is", game.moveUnit(new Position(3,1), new Position(4,2)), is(false));
  }

  @Test
  public void redShouldNotBeAbleToChangeProductionOfBlue() {
   game.changeProductionInCityAt(new Position(4,1),GameConstants.LEGION);
    assertThat("Red player can't change production in Blue cities", game.getCityAt(new Position(4,1)).getProduction() , is(not(GameConstants.LEGION)));
  }

  @Test
  public void redArcherShouldNotBeAbleToMove2TilesInATurn() {
    assertThat(game.getUnitAt(new Position(2,0)), is(notNullValue()));
    game.moveUnit(new Position(2,0), new Position(4,0));
    assertThat("There should not be a unit on 4,0", game.getUnitAt(new Position(4,0)), is(nullValue()));
  }

  @Test
  public void shouldNotBeAbleToMoveTwiceInATurn() {
    game.moveUnit(new Position(2,0), new Position(3,0));
    assertThat("Units should not be able to move more than once in a turn", game.moveUnit(new Position(3,0), new Position(4,0)) , is(false));
  }

  @Test
  public void shouldPlaceUnitsCorrectlyWhenProducingTwoUnits() {
    for(int i=0; i<8; i++) {
      game.endOfTurn();
    }
    assertThat("When we produce two units in red city second unit should be at 0,2", game.getUnitAt(new Position(0,1)).getTypeString() , is(GameConstants.ARCHER));
  }

  @Test
  public void shouldNotPutNewUnitOnMountainAfterProducing6Units() {
    for(int i=0; i<20; i++) {
      game.endOfTurn();
    }
    assertThat("Producing units should not place them on an illigal mountain tile at 2,2",game.getUnitAt(new Position(2,2)), is(nullValue()));
    assertThat("The archer should be on 2,1 instead of the illigal mountain tile", game.getUnitAt(new Position(2,1)).getTypeString(), is(GameConstants.ARCHER));
  }



  }




