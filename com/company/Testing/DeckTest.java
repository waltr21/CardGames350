package com.company.Testing;
import com.company.Card;
import com.company.Deck;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by RyanWalt on 10/26/17.
 * Very basic test cases of the Deck class. Mainly just
 * tests that the functionality of making the deck works,
 * along with other things like removing cards and such.
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

    @Test
    public void testShuffle(){
        Deck d = new Deck(true);
        d.shuffle();
        ArrayList<Card> temp = new ArrayList<>();
        temp = d.getDeck();

        //Shuffle the deck and look through the values next to each other
        //if the difference between these values is greater than 2, than
        //we can assume the deck is shuffled.
        int prev = temp.get(0).getValue();
        boolean changed = false;
        for (int i = 1; i < temp.size(); i++){
            if (Math.abs(temp.get(i).getValue() - prev) > 2) {
                changed = true;
                break;
            }
            prev = temp.get(i).getValue();
        }

        assertTrue(changed);
    }


}
