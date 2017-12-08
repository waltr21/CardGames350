package com.company.Testing;

import com.company.Rummy;
import org.junit.*;
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


  @Ignore
  public void nullCheck(){




  }
}
