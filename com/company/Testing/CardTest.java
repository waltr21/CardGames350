package com.company.Testing;
import com.company.Card;
import org.junit.Test;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by RyanWalt on 10/26/17.
 * Super basic test class of the card class.
 * Very small because it holds almost no logic.
 */
public class CardTest {
    /**
     * Test to make sure the Card holds the right values after
     * being assigned.
     */
    @Test
    public void testCorrectAssignValues(){
        Card c1 = new Card(10, 2);
        Card c2 = new Card(-1, -1);

        assertEquals(c1.getValue(), 10);
        assertEquals(c1.getSuit(), 2);
        assertEquals(c2.getSuit(), -1);
        assertEquals(c2.getValue(), -1);
    }

    /**
     * Test to make sure the set methods work correctly.
     */
    @Test
    public void testAssignMethods(){
        Card c1 = new Card(5, 1);
        c1.setSuit(2);
        c1.setValue(10);

        assertEquals(c1.getValue(), 10);
        assertEquals(c1.getSuit(), 2);
    }

    /**
     * Test to make sure the equals method workd correctly.
     */
    @Test
    public void testEqualsMethod(){
        Card c1 = new Card(2,3);
        Card c2 = new Card(2,3);
        Card c3 = new Card(3,3);

        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(c3));
    }

}
