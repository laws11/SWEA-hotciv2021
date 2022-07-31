package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.AlphaCivFactory;
import hotciv.stub.StubObserver;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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


public class TestObserver {
    private Game game;
    private GameObserver stubObserver;

    /** Fixture for alphaciv testing. */
    @BeforeEach
    public void setUp() throws FileNotFoundException { game = new GameImpl(new AlphaCivFactory()); stubObserver = new StubObserver();
    }

    @Test
    public void ShouldHaveAnObserverInArraListAfterAdding() {
        game.addObserver(stubObserver);
        assertThat("gameObserver should have a length of 1 after adding an observer",
                ((GameImpl)game).getGameObservers().size(), is(1));
    }

    @Test
    public void ShouldNotifyGameObservsersOnMoveUnit() {
        game.addObserver(stubObserver);
        game.moveUnit(new Position(2,0), new Position(2,1));
        assertThat("stubObserver should be notified when moving unit",
                ((StubObserver)stubObserver).getPositionNotification().size(), is(2));
    }

    @Test
    public void ShouldNotifyGameObserversOnEndOfTurn() {
        game.addObserver(stubObserver);
        game.endOfTurn();
        assertThat("stubObserver should be notified when ending turn",
                ((StubObserver)stubObserver).getPlayerNotification().size(), is(1));
    }
}
