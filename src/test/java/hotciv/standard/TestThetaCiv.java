package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.AlphaCivFactory;
import hotciv.standard.Factories.ThetaCivFactory;
import hotciv.standard.Factories.ThetaCivTestFactory;
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
public class TestThetaCiv {
    private Game game;

    /**
     * Fixture for ThetaCiv testing.
     */
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        game = new GameImpl(new ThetaCivTestFactory());

    }
    @Test
    public void shouldBeDesertTileAt5_6() {
       // CfgSetupStrategy thetaWorld = new CfgSetupStrategy("cfg/thetaciv.cfg");
        assertThat("There should be a desert tile at position 5,6", game.getTileAt(new Position(5,6)).getTypeString(), is(GameConstants.DESERT));
    }

    @Test
    public void shouldHaveSandwormAt9_6() {
        assertThat("There should be a Sandworm unit at position 9,6", game.getUnitAt(new Position(9,6)).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void cityShouldProduceAnSandwormAfter5Rounds() {
        game.endOfTurn();
        game.changeProductionInCityAt(new Position(4,5), GameConstants.SANDWORM);
        for(int i = 0; i < 10; i++) {
            game.endOfTurn();
        }
        assertThat("The city has produced a sandworm", game.getUnitAt(new Position(3,5)).getTypeString(), is(GameConstants.SANDWORM));
    }

    @Test
    public void sandwormHasAnAttackingStrengthOf0() {
        assertThat("Sandworm has an attacking strength of 0", game.getUnitAt(new Position(9,6)).getAttackingStrength(), is(0));
    }

    @Test
    public void sandwormHasAnDefensiveStrengthOf10() {
        assertThat("Sandworm has an defensive strength of 10", game.getUnitAt(new Position(9,6)).getDefensiveStrength(), is(10));
    }

    @Test
    public void sandwormShouldOnlyBeAllowedToMoveOnDesert() {
        game.endOfTurn();
        assertThat("Sandworm can't move to tile with plain at 9,7", game.moveUnit(new Position(9,6), new Position(9,7)), is(false));
    }

    @Test
    public void sandwormShouldDevourAllEnemies() {
        game.endOfTurn();
        game.performUnitActionAt(new Position(9,6));
        assertThat("Red archer at position 9,5 should be dead", game.getUnitAt(new Position(9,5)), is(nullValue()));
        assertThat("Red legion at position 8,6 should be dead", game.getUnitAt(new Position(8,6)), is(nullValue()));
        assertThat("Red settler at position 10,6 should be dead", game.getUnitAt(new Position(10,6)), is(nullValue()));
        assertThat("Red archer at position 8,5 should be dead", game.getUnitAt(new Position(8,5)), is(nullValue()));
        assertThat("Blue archer at position 9,7 should be alive", game.getUnitAt(new Position(9,7)).getTypeString(), is((GameConstants.ARCHER)));
        assertThat("Blue archer at position 9,7 should be blue", game.getUnitAt(new Position(9,7)).getOwner(), is((Player.BLUE)));
    }
}



