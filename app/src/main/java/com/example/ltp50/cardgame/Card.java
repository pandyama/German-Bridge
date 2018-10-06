package com.example.ltp50.cardgame;

import android.widget.ImageView;

public class Card {
    private final int rank;
    private final int suit;
    private final int drawable;


    public final static int DIAMONDS = 1;
    public final static int CLUBS = 2;
    public final static int HEARTS = 3;
    public final static int SPADES = 4;


    public final static int TWO = 2;
    public final static int THREE = 3;
    public final static int FOUR  = 4;
    public final static int FIVE  = 5;
    public final static int SIX   = 6;
    public final static int SEVEN = 7;
    public final static int EIGHT = 8;
    public final static int NINE  = 9;
    public final static int TEN   = 10;
    public final static int JACK  = 11;
    public final static int QUEEN = 12;
    public final static int KING  = 13;
    public final static int ACE   = 14;


    public Card(int rank, int suit, int drawable){
        this.rank = rank;
        this.suit = suit;
        this.drawable = drawable;
    }

    public int getRank(){
        return rank;
    }

    public int getSuit(){
        return suit;
    }

    public int getDrawable(){
        return drawable;
    }


    public static String rankToString(int rank) {
        switch (rank) {
            case ACE:
                return "ace";
            case TWO:
                return "two";
            case THREE:
                return "three";
            case FOUR:
                return "four";
            case FIVE:
                return "five";
            case SIX:
                return "six";
            case SEVEN:
                return "seven";
            case EIGHT:
                return "eight";
            case NINE:
                return "nine";
            case TEN:
                return "ten";
            case JACK:
                return "jack";
            case QUEEN:
                return "queen";
            case KING:
                return "king";
            default:
                //Handle an illegal argument.  There are generally two
                //ways to handle invalid arguments, throwing an exception
                //(see the section on Handling Exceptions) or return null
                return null;
        }
    }


    public static String suitToString(int suit) {
        switch (suit) {
            case DIAMONDS:
                return "diamonds";
            case CLUBS:
                return "clubs";
            case HEARTS:
                return "hearts";
            case SPADES:
                return "spades";
            default:
                return null;
        }
    }


}
