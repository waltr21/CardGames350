package com.company.Testing;
import com.company.War;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

/**
 * Test cases for War.
 */
public class WarTest {
    /**
     * Test to check the initial values of the game.
     */
    @Test
    public void testCardDealValues() {
        War game = new War();
        game.setup();
        game.deal();

        //Make sure we have the right amount of cards for each player.
        assertEquals(game.getPlayer1CardCount(), 26);
        assertEquals(game.getPlayer2CardCount(), 26);
    }


}