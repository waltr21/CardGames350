package com.company.Testing;
import com.company.Card;
import com.company.Deck;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by RyanWalt on 10/26/17.
 */
public class DeckTest{


    @Test
    public void testDeckCreation(){
        Deck d = new Deck(false);
        Deck d1 = new Deck(true);


        assertEquals(d.getSize(), 52);
        assertEquals(d1.getSize(), 54);

    }

    @Test
    public void testRemoveCard(){
        Deck d = new Deck(false);
        for (int i = 0; i < 10; i++){
            d.removeTop();
        }
        //Check the size of the deck after we remove.
        assertEquals(d.getSize(), 42);

        //Make sure we are getting a card from the deck.
        Card c = d.removeTop();
        assertTrue(c.getValue() >= -1);
    }

    

}
