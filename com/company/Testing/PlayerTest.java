package com.company.Testing;
import com.company.Card;
import com.company.Deck;
import com.company.Player;
import org.junit.Test;

import java.util.ArrayList;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by RyanWalt on 10/26/17.
 * Test cases for specific actions that can be taken
 * on the player objects.
 */
public class PlayerTest {


    @Test
    public void testGiveCards(){
        Player p = new Player();
        Deck d = new Deck(false);

        for (int i = 0; i < 10; i++){
            p.giveCard(d.removeTop());
        }
        assertEquals(p.cardCount(), 10);
        for (int i = 0; i < 20; i++){
            p.giveCard(d.removeTop());
        }
        assertEquals(p.cardCount(), 30);
    }

    @Test
    public void testSort(){
        Player p = new Player();
        Deck d = new Deck(false);
        d.shuffle();

        //Give the player 15 random cards
        for (int i = 0; i < 15; i++){
            p.giveCard(d.removeTop());
        }

        p.sortCards();
        boolean sorted = true;
        ArrayList<Card> tempCards = p.getCards();

        //If any card is out of order, we know the cards were not sorted properly.
        for (int i = 0; i < tempCards.size()-1; i++){
            if (tempCards.get(i).getValue() > tempCards.get(i+1).getValue())
                    sorted = false;
        }
        assertTrue(sorted);
    }

    @Test
    public void testTakeTop(){
        Player p = new Player();
        Player p2 = new Player();
        Deck testDeck = new Deck(false);
        testDeck.shuffle();

        for (int i = 0; i < 15; i++){
            p.giveCard(testDeck.removeTop());
        }

        for (int i = 0; i < 5; i++){
            p2.giveCard(p.takeTopCard());
        }

        assertEquals(p.cardCount(), 10);
        assertEquals(p2.cardCount(), 5);
    }
}
