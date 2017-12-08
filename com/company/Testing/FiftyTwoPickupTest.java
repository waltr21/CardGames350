package com.company.Testing;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import com.company.FiftyTwoPickup;

public class FiftyTwoPickupTest {
    // Test that we start out with the right number of cards picked up

    public void testStartValues() {
        FiftyTwoPickup game = new FiftyTwoPickup();
        assertEquals(game.getPickedUp(), 0);
    }
}
