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

    public GoFishAI(int l){
        level = l;
        memory = new ArrayList<>();
    }

    public void addMemory(int index, Card c){
        if (memory.size() > level){
            memory.remove(0);
        }
        memory.add(new Pair(index, c));
    }

    public Player getPlayer(){
        return bot;
    }

    /**
     * Go through the players cards and weight them by how close they are
     * to getting four of a kind.
     * @return
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
                it.remove();
            }
            finalVals.add(cardVal);
            map.remove(cardVal);

            if (map.size() < 1){
                empty = false;
            }
        }
        
        return finalVals;
    }

    public void takeTurn(){
        for (Pair p : memory){
            break;
        }
    }
}

/**
 * Temp class object used to hold a card and an index
 * (There is probably a library that does this better)
 */
class Pair{
    int index;
    Card card;
    public Pair(int i, Card c){
        index = i;
        card = c;
    }

    public int getIndex(){
        return index;
    }

    public Card getCard(){
        return card;
    }
}

