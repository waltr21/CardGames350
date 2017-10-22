package com.company;

import java.util.ArrayList;

public class HoldEm
{
    private Deck deck;
    private ArrayList<Player> players;
    private ArrayList<Card> board;
    private int chip = 0;
    private int pot;
    private int bet;
    private int smallBlind;
    private int bigBlind;
    private int stage;
    private boolean over;

    public HoldEm(int player_num) {
        deck = new Deck(false);
        pot = 0;
        bet = 0;
        smallBlind = 1;
        bigBlind = 2;
        stage = 1;
        for (int i = 0; i < player_num; i++) {
            players.add(new Player());
        }

        over = false;
        while(!over) {
            // First  phase of holdem, big blind & small blind
            take_bet(players.get((chip + 1) % player_num), smallBlind);
            take_bet(players.get((chip + 2) % player_num), bigBlind);

            // Next phase of holdem, deal 2 cards to every player, one at a time, starting w/ small blind position.
            for (int i = 0; i < player_num * 2; i++) {
                players.get((chip + i) % player_num).giveCard(deck.removeTop());
            }

            //  Betting starts, this is where user input begins.

            for (int i = 0; i < player_num; i++) {
                play_turn(players.get(i));
            }
        }

    private void take_bet(Player p, int amount) {
        bet = Math.max(bet, amount);
        if (p.getScore() < amount) {
            pot += p.getScore();
            p.setScore(0);
        } else {
            pot += amount;
            p.setScore(p.getScore() - amount);
        }
    }

    private void play_turn(Player p) {

    }

}
