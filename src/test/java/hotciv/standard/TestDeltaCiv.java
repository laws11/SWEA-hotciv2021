package hotciv.standard;

import hotciv.framework.*;

import hotciv.standard.Factories.DeltaCivFactory;
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
public class TestDeltaCiv {
    private Game game;


    /**
     * Fixture for alphaciv testing.
     */
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        game = new GameImpl(new DeltaCivFactory());
    }

    @Test
    public void ShouldBeOceanTileAt10() {
        assertThat(game.getTileAt(new Position(1,0)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void ShouldBeHillTileAt511() {
        assertThat(game.getTileAt(new Position(5,11)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void ShouldBeMountainTileAt34() {
        assertThat(game.getTileAt(new Position(3,4)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void ShouldBeForestTileAt110() {
        assertThat(game.getTileAt(new Position(1,10)).getTypeString(), is(GameConstants.FOREST));
    }

    @Test
    public void ShouldBeOceanTileAt66() {
        assertThat(game.getTileAt(new Position(6,6)).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void ShouldBeHillTileAt145() {
        assertThat(game.getTileAt(new Position(14,5)).getTypeString(), is(GameConstants.HILLS));
    }

    @Test
    public void ShouldBeMountainTileAt113() {
        assertThat(game.getTileAt(new Position(11,3)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void ShouldBeForestTileAt128() {
        assertThat(game.getTileAt(new Position(12,8)).getTypeString(), is(GameConstants.FOREST));
    }

    @Test
    public void ShouldBeMountainTileAt713() {
        assertThat(game.getTileAt(new Position(7,13)).getTypeString(), is(GameConstants.MOUNTAINS));
    }

    @Test
    public void ShouldBeForestTileAt813() {
        assertThat(game.getTileAt(new Position(8,13)).getTypeString(), is(GameConstants.FOREST));
    }

    @Test
    public void shouldBeBlueCityAt45(){
        assertThat(game.getCityAt(new Position(4,5)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void shouldBeRedSettlerAt55(){
        assertThat(game.getUnitAt(new Position(5,5)).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(new Position(5,5)).getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void shouldBeBlueLegionAt44(){
        assertThat(game.getUnitAt(new Position(4,4)).getOwner(), is(Player.BLUE));
        assertThat(game.getUnitAt(new Position(4,4)).getTypeString(), is(GameConstants.LEGION));
    }

}

