package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.ZetaCivFactory;
import hotciv.standard.Factories.ZetaCivTestFactory;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
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
public class TestZetaCiv {
    private Game game;

    /**
     * Fixture for Zetaciv testing.
     */
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        game = new GameImpl(new ZetaCivTestFactory());
    }
    @Test
    public void blueShouldWinGameByConquest() {
        game.endOfTurn();
        game.moveUnit(new Position(1,2), new Position(1, 1));
        assertThat("blue legion wins against acher and conquers city thereby winning the game by owning all cities",
                game.getWinner(), is(Player.BLUE));
    }

    @Test
    public void redShoudWinGameAfterWininng3Attacks() {
        for (int i = 0 ; i < 20 ; i ++) {
            game.endOfTurn();
            game.endOfTurn();
        }
        assertThat("there should not be a winner at 0 succesful attacks", game.getWinner(), is(nullValue()));
        assertThat("Attack returns true if successful", game.moveUnit(new Position(10,14), new Position(11,14)), is(true));
        assertThat("there should not be a winner at 1 succesful attacks", game.getWinner(), is(nullValue()));
        assertThat("Attack returns true if successful", game.moveUnit(new Position(10,13), new Position(11,13)), is(true));
        assertThat("there should not be a winner at 2 succesful attacks", game.getWinner(), is(nullValue()));
        assertThat("Attack returns true if successful", game.moveUnit(new Position(10,12), new Position(11,12)), is(true));
        assertThat("there should now be a winner, player RED", game.getWinner(), is(Player.RED));
    }

    @Test
    public void blueShouldNotWinGameByConquest() {
        for (int i = 0 ; i < 20 ; i ++) {
            game.endOfTurn();
            game.endOfTurn();
        }
        game.endOfTurn();
        game.moveUnit(new Position(1,2), new Position(1, 1));
        assertThat("round 21 winStrategy should be 3 attack so blue should not win game",
                game.getWinner(), is(nullValue()));
    }

    @Test
    public void redShoudNotWinGameAfterWininng3Attacks() {
        assertThat("there should not be a winner at 0 succesful attacks", game.getWinner(), is(nullValue()));
        assertThat("Attack returns true if successful", game.moveUnit(new Position(10,14), new Position(11,14)), is(true));
        assertThat("there should not be a winner at 1 succesful attacks", game.getWinner(), is(nullValue()));
        assertThat("Attack returns true if successful", game.moveUnit(new Position(10,13), new Position(11,13)), is(true));
        assertThat("there should not be a winner at 2 succesful attacks", game.getWinner(), is(nullValue()));
        assertThat("Attack returns true if successful", game.moveUnit(new Position(10,12), new Position(11,12)), is(true));
        assertThat("there should not be a winner", game.getWinner(), is(nullValue()));

    }
}



