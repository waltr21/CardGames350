package com.company.Testing;

import com.company.Rummy;
import org.junit.*;
import static junit.framework.TestCase.*;

public class RummyTest {


  @Test
  public void testValidPlayerAmount(){

        Rummy game1 = new Rummy(-2);
        assertTrue(game1.getNumPlayers() == 2);
        assertTrue(game1.getNumCards() == 10);
        Rummy game2 = new Rummy(5);
        assertTrue(game2.getNumPlayers() == 4);
        assertTrue(game2.getNumCards() == 7);

  }
  @Ignore
  public void nullCheck(){




  }
}
