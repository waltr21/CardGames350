package com.company;

public class FiftyTwoPickup {

    Deck deck;
    int pickedUp = 0;

    public int getPickedUp() {
        return pickedUp;
    }

    public FiftyTwoPickup() {
        deck = new Deck(false);
        pickedUp = 0;
    }

    public void reset() {
        deck = new Deck(false);
        pickedUp = 0;
    }


}
