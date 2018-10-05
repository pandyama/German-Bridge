package com.example.ltp50.cardgame;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener{

    ImageView one, two, three;
    List<String> cards = new ArrayList<>();
    Button btnNewGame;

    boolean oneBackShow=true, twoBackShow=true, threeBackShow=true;

    Animation toMiddle, fromMiddle;

    int flagCard = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        one = (ImageView)findViewById(R.id.one);
        two = (ImageView)findViewById(R.id.two);
        three = (ImageView)findViewById(R.id.three);


        btnNewGame = (Button)findViewById(R.id.btnNew);
        toMiddle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_middle); //What is getApplicationContext
        fromMiddle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_middle);

        toMiddle.setAnimationListener(this);
        fromMiddle.setAnimationListener(this);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);

        btnNewGame.setOnClickListener(this);


        setUp();

    }

    private void setUp(){
        cards.clear();
        cards.add("a");
        cards.add("k");
        cards.add("k");

        Collections.shuffle(cards);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if(flagCard == 0){
            if(animation == toMiddle){
                if(oneBackShow){
                    showCard(oneBackShow,((ImageView)findViewById(R.id.one)),flagCard);
                }
                else{
                    ((ImageView)findViewById(R.id.one)).setImageResource(R.drawable.back);

                    ((ImageView)findViewById(R.id.one)).clearAnimation();
                    ((ImageView)findViewById(R.id.one)).setAnimation(fromMiddle);
                    ((ImageView)findViewById(R.id.one)).startAnimation(fromMiddle);
                }
            }
            else{
                oneBackShow = !oneBackShow;
            }
        }
        else if(flagCard == 1) {
            if (animation == toMiddle) {
                if (twoBackShow) {
                    showCard(twoBackShow, ((ImageView) findViewById(R.id.two)), flagCard);
                } else {
                    ((ImageView) findViewById(R.id.two)).setImageResource(R.drawable.back);

                    ((ImageView) findViewById(R.id.two)).clearAnimation();
                    ((ImageView) findViewById(R.id.two)).setAnimation(fromMiddle);
                    ((ImageView) findViewById(R.id.two)).startAnimation(fromMiddle);
                }
            } else {
                twoBackShow = !twoBackShow;
            }
        }

        else if(flagCard == 2) {
            if (animation == toMiddle) {
                if (threeBackShow) {
                    showCard(threeBackShow, ((ImageView) findViewById(R.id.three)), flagCard);
                } else {
                    ((ImageView) findViewById(R.id.three)).setImageResource(R.drawable.back);

                    ((ImageView) findViewById(R.id.three)).clearAnimation();
                    ((ImageView) findViewById(R.id.three)).setAnimation(fromMiddle);
                    ((ImageView) findViewById(R.id.three)).startAnimation(fromMiddle);
                }
            } else {
                threeBackShow = !threeBackShow;
            }
        }





    }

    private void showCard(boolean isBackShow, ImageView imgView, int index){

            if(isBackShow){
                if(index == 0){ //First Card
                    if(cards.get(0).equals("a")){
                        imgView.setImageResource(R.drawable.two_of_clubs);
                    }
                    else if(cards.get(0).equals("k")){
                        imgView.setImageResource(R.drawable.two_of_diamonds);
                    }

                }
                else if(index == 1){ //Second Card
                    if(cards.get(1).equals("a")){
                        imgView.setImageResource(R.drawable.two_of_hearts);
                    }
                    else if(cards.get(1 ).equals("k")){
                        imgView.setImageResource(R.drawable.two_of_diamonds);
                    }

                }

                else if(index == 2){ //Third Card
                    if(cards.get(2).equals("a")){
                        imgView.setImageResource(R.drawable.two_of_spades);
                    }
                    else if(cards.get(2).equals("k")){
                        imgView.setImageResource(R.drawable.two_of_clubs);
                    }

                }

            }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnNew) {
            newGame();
        }
        else {

            view.clearAnimation();
            view.setAnimation(toMiddle);
            view.startAnimation(toMiddle);

            if (view.getId() == R.id.one) {
                flagCard = 0;
            } else if (view.getId() == R.id.two) {
                flagCard = 1;
            } else if (view.getId() == R.id.three) {
                flagCard = 2;
            }
        }

    }

    private void newGame(){
        Collections.shuffle(cards);

        Animation anim_one = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_one);
        Animation anim_two = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_two);

        one.startAnimation(anim_one);
        two.startAnimation(anim_two);
        three.startAnimation(anim_two);


        one.setImageResource(R.drawable.back);
        two.setImageResource(R.drawable.back);
        three.setImageResource(R.drawable.back);

        oneBackShow = twoBackShow = threeBackShow = true;



    }
}
