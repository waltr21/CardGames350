package com.company.Testing;
import com.company.GoFishAI;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class is mainly used to test some of the functions that were
 * missing in the GoFish test class. Most of teh AI functions are tested
 * there through takeBotTurn().
 */
public class GoFishAITest {
    @Test
    public void testBotBasics(){
        GoFishAI bot = new GoFishAI(7, 1);
        Assert.assertEquals(bot.getIndex(), 1);
        Assert.assertEquals(bot.getMemory().size(), 0);
    }

    @Test
    public void testMemory(){
        GoFishAI bot = new GoFishAI(8, 3);
        bot.addMemory(2, 4);
        bot.addMemory(1, 6);
        bot.addMemory(1, 4);
        Assert.assertEquals(bot.getMemory().size(), 3);
        bot.addMemory(0, 3);
        bot.addMemory(1, 4);
        bot.addMemory(2, 8);
        bot.addMemory(1, 10);
        bot.addMemory(0, 11);
        bot.addMemory(1, 2);
        bot.addMemory(2, 4);

        Assert.assertEquals(bot.getMemory().size(), 7);
        bot.addMemory(2, 4);
        bot.addMemory(2, 4);
        bot.addMemory(2, 4);
        Assert.assertEquals(bot.getMemory().size(), 7);
    }
}
