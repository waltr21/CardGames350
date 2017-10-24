package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class HoldEm
{
    private Deck deck;
    private ArrayList<Player> players;
    private ArrayList<Card> board;
    private int _player_num;
    private int chip = 0;
    private int pot;
    private int highbet;
    private int smallBlind;
    private int bigBlind;
    private int stage;
    private boolean over;
    private boolean betting;
    private int i;

    private int[] scores;
    // Represents the bets the player places on the table.
    private int[] bets;

    private Scanner in = new Scanner(System.in);

    public HoldEm(int player_num) {
        _player_num = player_num;
        deck = new Deck(false);
        players = new ArrayList<Player>();
        bets = new int[player_num];
        // The main pot
        pot = 0;
        // The current high bet
        highbet = 0;
        // Small / big blind constants
        smallBlind = 1;
        bigBlind = 2;
        // Stage, not being used maybe later
        stage = 1;
        // Initialize the players, add them to player ArrayList
        for (int i = 0; i < player_num; i++) {
            Player p = new Player();
            p.active = true;
            p.setScore(100); // Start with 100 money

            players.add(p);
            // Initialize the bets amount to 0.
            bets[i] = 0;
        }
        // Boolean to control game finish
        over = false;
        while (!over) {
            // Start the hand.
            deck.shuffle();

            // First  phase of holdem, big blind & small blind
            place_bet(players.get((chip + 1) % player_num), smallBlind);
            place_bet(players.get((chip + 2) % player_num), bigBlind);

            // Next phase of holdem, deal 2 cards to every player, one at a time, starting w/ small blind position.
            for (int j = 0; j < player_num * 2; j++) {
                // Give each player a card. With respect to the dealer chip.
                players.get((chip + j) % player_num).giveCard(deck.removeTop());
            }

            //  Betting starts, this is where user input begins.
            betting = true;
            for (i = 0; betting; i++) {
                // Play the turn. i is a class variable, should persist without passing to play turn function
                play_turn(players.get((chip + i) % player_num));
            }
        }
    }

    private void place_bet(Player p, int amount) {
        // Take a player bet, put it on the board.
        highbet = Math.max(highbet, amount);
        if (p.getScore() < amount) {
            bets[(chip + i)%_player_num] += p.getScore();
            p.setScore(0);
        } else {
            bets[(chip + i)%_player_num] += amount;
            p.setScore(p.getScore() - amount);
        }
    }

    private void show_hole_cards(Player p) {
        System.out.println("Your hole cards: ");
        for (int j = 0; j < 2; j++) {
            System.out.println(p.getCards().get(j));
        }
    }

    private void play_turn(Player p) {
        // If they are folded then they don't play
        if (!p.active) return;

        // If they're all in they can't do anything anyway.
        if (p.getScore() == 0) return;

        show_hole_cards(p);

        // Player options: check, bet(amount), fold
        System.out.println(String.format("You have %d chips. It's your turn.", p.getScore()));

        // Player can only check if their bet is greater than or equal to the high bet.
        boolean canCheck = false;

        if (highbet <= bets[(chip + i)%_player_num]) {
            canCheck = true;
        }

        // Time to take input
        boolean valid = false;
        while (!valid) {


            String input = in.nextLine().toLowerCase();

            // F is for fold
            if (input.equals("f")) {
                pot += bets[(chip + i) % _player_num];
                p.active = false;
                System.out.println(String.format("Player %d folded!", (i%_player_num)+1));
                return;
            }

            // C is for check
            else if (input.equals("c")) {
                if (!canCheck) {
                    System.out.println("You can't check!");
                    continue;
                }
                System.out.println(String.format("Player %d checked!", (i%_player_num)+1));
                return;
            }

            // B is for bet, this is where the magic happens
            else if (input.equals("b")) {
                System.out.println("How much to bet?");
                boolean validbet = false;
                int bet = 0;
                while (!validbet) {
                    try {
                        bet = Integer.parseInt(in.nextLine());
                        if (bet <= p.getScore()) {
                            validbet = true;
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid bet. Try again!");
                        continue;
                    }
                }
                // Now we've got a valid bet that is less than or equal to the player's amount.
                System.out.println(String.format("Player %d bet $%d!", (i%_player_num)+1, bet));
                place_bet(p, bet);
            }
        }

    }

    public static void main(String args[]) {
        HoldEm game = new HoldEm(4);
    }

}
