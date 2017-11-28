package com.company;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RyanWalt on 11/27/17.
 */
public class GoFishAI {
    ArrayList<Pair> memory;
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


}

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

