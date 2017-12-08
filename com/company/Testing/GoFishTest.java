package com.company.Testing;
import com.company.GoFish;
import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

/**
 * Test Cases for a game of go fish.
 */
public class GoFishTest {
    /**
     * test to make sure that the initial values of the new game
     * are made correctly.
     */
    @Test
    public void testNewGameValues(){
        GoFish game = new GoFish(12);

        //Make sure we have the right amount of players and cards.
        assertEquals(game.getPlayers().size(), 4);
        assertEquals(game.getPlayers().get(0).getCards().size(), 5);

        GoFish game1 = new GoFish(2);
        assertEquals(game1.getPlayers().size(), 4);
        assertEquals(game1.getPlayers().get(0).getCards().size(), 5);
    }

    /**
     * Test to make sure that the single turn of the game
     * works correctly.
     */
    @Test
    public void testGameTurn(){
        GoFish game = new GoFish(3);

        //Test an invalid move...
        game.takeTurn(-10, -10);
        assertEquals(game.getTurn(), 0);

        //Test a valid turn and make sure the turn has changed
        int valid = game.getPlayers().get(0).getCards().get(0).getValue();
        game.takeTurn(2, valid);
        assertEquals(game.getTurn(), 1);

        //Continue and take the turn for the game
        valid = game.getPlayers().get(1).getCards().get(1).getValue();
        game.takeTurn(4, valid);
        assertEquals(game.getTurn(), 3);
    }

    /**
     * Test to make sure that the game is able to be reset correctly.
     */
    @Test
    public void testRest(){
        GoFish game = new GoFish(2);
        //Take a turn then reset the game.
        int valid = game.getPlayers().get(0).getCards().get(0).getValue();
        game.takeTurn(2, valid);
        game.resetGame(2);
        //Check to make sure the player cards are set back to 5.
        assertEquals(game.getPlayers().get(1).getCards().size(), 5);
    }


    /**
     * Random tests for a whole bunch of getters
     */
    @Test
    public void testGetters(){
        GoFish game = new GoFish(2);
        assertEquals(game.getGameDeckSize(), 32);
        assertEquals(game.getPlayers().size(), 4);
        assertEquals(game.getPlayer(), game.getPlayers().get(0));
        assertEquals(game.getTurn(), 0);
        game.takeTurn(1, 5);
        assertEquals(game.getPlayer(), game.getPlayers().get(game.getTurn()));
    }

    /**
     * Switches between turns between bot and player.
     * Becuase a lot of this is random card values, we have to
     * do a few to get more code coverage.
     */
    @Test
    public void testInGameAI(){
        GoFish game = new GoFish(1);
        assertEquals(game.getCurrentAI(), null);
        game.takeTurn(2, 3);
        if (game.getTurn() >= 1)
            assertNotNull(game.getCurrentAI());
        game.takeBotTurn();
        assertEquals(game.getCurrentAI(), null);
        game.takeTurn(3, 6);
    }

}
