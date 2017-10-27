package com.company.Testing;
import com.company.GoFish;
import org.junit.Test;
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
        GoFish game = new GoFish(3);

        //Make sure we have the right amount of players and cards.
        assertEquals(game.getPlayers().size(), 3);
        assertEquals(game.getPlayers().get(0).getCards().size(), 5);

        GoFish game1 = new GoFish(2);
        assertEquals(game1.getPlayers().size(), 2);
        assertEquals(game1.getPlayers().get(0).getCards().size(), 7);
    }

    /**
     * Test to make sure that the single turn of the game
     * works correctly.
     */
    @Test
    public void testGameTurn(){
        GoFish game = new GoFish(4);

        //Test an invalid move...
        game.takeTurn(game.getPlayer(), -10, -10);
        assertEquals(game.getTurn(), 0);

        //Test a valid turn and make sure the turn has changed
        int valid = game.getPlayers().get(0).getCards().get(0).getValue();
        game.takeTurn(game.getPlayer(), 2, valid);
        assertEquals(game.getTurn(), 1);

        //Continue and take the turn for the game
        valid = game.getPlayers().get(1).getCards().get(1).getValue();
        game.takeTurn(game.getPlayer(), 4, valid);
        assertEquals(game.getTurn(), 3);
    }

    /**
     * Test to make sure that the game is able to be reset correctly.
     */
    @Test
    public void testRest(){
        GoFish game = new GoFish(4);
        //Take a turn then reset the game.
        int valid = game.getPlayers().get(0).getCards().get(0).getValue();
        game.takeTurn(game.getPlayer(), 2, valid);
        game.resetGame();
        //Check to make sure the player cards are set back to 5.
        assertEquals(game.getPlayers().get(1).getCards().size(), 5);
    }
}
