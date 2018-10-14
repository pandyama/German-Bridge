package com.example.ltp50.cardgame;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //public ArrayList deck;
    public int score;
    public List<Card> deck = new ArrayList<>();

    public Player(List<Card> d1, int res){
        this.score = res;
        this.deck = d1;
    }
}
