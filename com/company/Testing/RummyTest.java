package com.company.Testing;

import com.company.Card;
import com.company.Rummy;
import org.junit.*;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class RummyTest {

  @Test
  public void testNumPlayers(){

        Rummy rummy = new Rummy(4);
        assertTrue(rummy.getNumPlayers() == 4);
        rummy = new Rummy(2);
        assertTrue(rummy.getNumPlayers() == 2);
        rummy = new Rummy(-2);
        assertTrue(rummy.getNumPlayers() == 2);
        rummy = new Rummy(5);
        assertTrue(rummy.getNumPlayers() == 4);


  }

    @Test
    public void testCards(){

        Rummy rummy = new Rummy(4);
        assertTrue(rummy.getNumCards() == 7);
        rummy = new Rummy(2);
        assertTrue(rummy.getNumCards() == 10);


    }

    @Test
    public void testInitialized(){

        Rummy rummy = new Rummy(4);
        assertNotNull(rummy.getMelds());
        assertNotNull(rummy.getDiscard());
        assertNotNull(rummy.getMelds());
        assertTrue(rummy.getTurn() == 1);
        assertTrue(rummy.getScores().length == rummy.getNumPlayers());
        assertEquals("Game initialized.",rummy.getMessage());
        assertEquals(rummy.getDiscard().size(), 1);

    }

    @Test
    public void takeDiscardTest(){

        Rummy rummy = new Rummy(4);
        rummy.takeDiscard(1);
        assertEquals(rummy.getCurrentPlayer().cardCount(),8);
        assertEquals(rummy.getDiscard().size(),0);
        rummy.takeDiscard(4);
        assertEquals(rummy.getDiscard().size(),0);

    }

    @Test
    public void testDiscard(){

        Rummy rummy = new Rummy(4);
        rummy.discard(new Card(6,1));
        assertEquals(rummy.getDiscard().size(),2);
        rummy.getPlayers().get(0).getCards().clear();

    }

    @Test
    public void testValidMeld(){

        Rummy rummy = new Rummy(4);
        ArrayList<Card> meld = new ArrayList<>();
        meld.add(new Card(6,1));
        meld.add(new Card(7,1));
        meld.add(new Card(8,1));
        rummy.playMeld(meld);
        assertEquals("Meld played!",rummy.getMessage());
        meld = new ArrayList<>();
        meld.add(new Card(7,1));
        meld.add(new Card(7,2));
        meld.add(new Card(7,3));
        rummy.playMeld(meld);
        assertEquals("Meld played!",rummy.getMessage());
        meld = new ArrayList<>();
        meld.add(new Card(6,1));
        meld.add(new Card(7,2));
        meld.add(new Card(8,1));
        rummy.playMeld(meld);
        assertEquals("Invalid meld.",rummy.getMessage());
        meld = new ArrayList<>();
        meld.add(new Card(6,1));
        meld.add(new Card(7,1));
        rummy.playMeld(meld);
        assertEquals("Invalid meld.",rummy.getMessage());

    }

    @Test
    public void testTopDeck(){

        Rummy rummy = new Rummy(4);
        rummy.removeTopDeck();
        assertEquals(rummy.getDeck().getSize(),22);

    }

    @Test
    public void testToString(){

        Rummy rummy = new Rummy(4);
        String test = rummy.toString(rummy.getPlayers().get(0).getCards());
        assertFalse(test.equals(""));

    }

}
