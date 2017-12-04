package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by RyanWalt on 11/27/17.
 */
public class GoFishAI {
    //Amount of cards the AI can remember.
    ArrayList<Pair> memory;
    //An AI is a player with cards, so we need a player in the class to reference.
    Player bot = new Player();
    //Level of difficulty the AI will be.
    int level = 0;
    //Index for where the AI sits on the player turn list.
    int index = 0;

    public GoFishAI(int lev, int ind){
        level = lev;
        index = ind;
        memory = new ArrayList<>();
        System.out.println("Bot created with index: " + index);
    }

    public int getIndex(){
        return index;
    }

    public void removeMemory(int playerIndex, int cardVal){
        for (Pair p : memory){
            if (p.getIndex() == playerIndex && p.getVal() == cardVal){
                memory.remove(p);
                System.out.println("Memory removed from :" + index);
            }
        }
    }

    public void addMemory(int playerIndex, int cardVal){
        if (playerIndex == index){
            return;
        }

        if (memory.size() > level){
            memory.remove(0);
            System.out.println("Memory lost.");
        }
        memory.add(new Pair(playerIndex, cardVal));
        System.out.println("Memory added to: " + index + " -- Player: " + playerIndex
                + " Card: " + cardVal);
    }

    public Player getPlayer(){
        return bot;
    }

    /**
     * Go through the players cards and weight them by how close they are
     * to getting four of a kind.
     * @return ArrayList of weighted cards.
     */
    public ArrayList<Integer> weightCards(){
        ArrayList<Card> tempCards = bot.getCards();
        HashMap<Integer,Integer> map =new HashMap<>();
        ArrayList<Integer> finalVals = new ArrayList<>();

        for (Card c : tempCards){
            boolean found = false;
            for (int val : map.keySet()){
                if (val == c.getValue()){
                    map.put(val, map.get(val)+ 1);
                    found = true;
                }
            }
            //Put a new value on the hashmap
            if (!found){
                map.put(c.getValue(), 1);
            }
        }

        //Pull each value from the map according to their weights.
        boolean empty = false;
        while (!empty) {
            Iterator it = map.entrySet().iterator();
            int max = -1;
            int cardVal = -1;
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if ((int)pair.getValue() > max) {
                    max = (int) pair.getValue();
                    cardVal = (int) pair.getKey();
                }
            }
            if (max < 4)
                finalVals.add(cardVal);

            map.remove(cardVal);

            if (map.size() < 1){
                empty = true;
            }
        }

        System.out.println(finalVals);
        return finalVals;
    }


    public ArrayList<Pair> getMemory(){
        return memory;
    }

    /**
     * Testing for the thing (temp)
     * @param args
     */
    public static void main(String[] args){
        GoFishAI x = new GoFishAI(5, 2);
        x.getPlayer().giveCard(new Card(1,1));
        x.getPlayer().giveCard(new Card(1,2));
        x.getPlayer().giveCard(new Card(4,1));
        x.getPlayer().giveCard(new Card(4,2));
        x.getPlayer().giveCard(new Card(8,1));
        x.getPlayer().giveCard(new Card(9,1));
        x.getPlayer().giveCard(new Card(9,1));
        x.getPlayer().giveCard(new Card(9,1));
        x.getPlayer().giveCard(new Card(9,1));
        x.weightCards();
    }
}

/**
 * Temp class object used to hold a card and an index
 * (There is probably a library that does this better)
 */
class Pair{
    int index;
    int val;
    public Pair(int i, int v){
        index = i;
        val = v;
    }

    public int getIndex(){
        return index;
    }

    public int getVal(){
        return val;
    }
}

