package com.example.ltp50.cardgame;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    ImageView one, two, three;
    List<String> cards = new ArrayList<>();
    Button btnNewGame;


    List<Card> deck = new ArrayList<>();

    boolean oneBackShow=true, twoBackShow=true, threeBackShow=true;

    Animation toMiddle;

    int flagCard = 0;

/*    private View.OnClickListener newGameClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dealCards();
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       /*one = (ImageView)findViewById(R.id.one);
        two = (ImageView)findViewById(R.id.two);
        three = (ImageView)findViewById(R.id.three);



         //What is getApplicationContext
        fromMiddle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_middle);


        fromMiddle.setAnimationListener(this);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);*/
        toMiddle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_middle);
        toMiddle.setAnimationListener(this);

        btnNewGame = (Button)findViewById(R.id.btnNew);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Button Clicked");
                dealCards();
            }
        });


        //setUp();

    }

    private void dealCards(){
        System.out.println("Making new Deck");
        newDeck();
        Collections.shuffle(deck);
        final LinearLayout layoutNew = (LinearLayout)findViewById(R.id.german);
        layoutNew.removeAllViews();
        for(int i = 0; i < 5; i++){
            //System.out.println(deck.get(i).getSuit());
            final ImageView image = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            image.setMaxWidth(300);
            image.setMaxHeight(300);
            image.setImageResource(deck.get(i).getDrawable());
            //System.out.println(deck.get(i).getDrawable());
            image.setLayoutParams(lp);
            image.setAdjustViewBounds(true);
            image.setScaleType(ImageView.ScaleType.FIT_END);
            image.setId(i);
            System.out.print("Card id : "+image.getId());
//            /image.setVisibility(View.VISIBLE);
            layoutNew.addView(image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playCard(image.getId(),layoutNew);
                }
            });
        }
    }

    private void playCard(int id, LinearLayout l1){
        l1 = (LinearLayout)findViewById(R.id.german);
        ImageView img = (ImageView)findViewById(id);
        //l1.removeViewInLayout((ImageView)findViewById(id));

        Animation anim_one = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.newmiddle);
        //Animation anim_two = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_two);

        img.startAnimation(anim_one);
    }

    private void setUp(){
        cards.clear();
        cards.add("a");
        cards.add("k");
        cards.add("k");

        //Collections.shuffle(cards);
    }

    private void newDeck(){

        //ImageView image = (ImageView)findViewById(R.id.one);
        //Resources resource = context.getResources();

        //int resID = getResources().getIdentifier("twohearts","string",getPackageName());
        //ImageView image = new ImageView(this);

        int draw = 0;
        deck.clear();
        for(int i = 1; i <= 4; i++){
            for(int j = 2; j <= 14; j++){
                String rank = Card.rankToString(j);
                String suit = Card.suitToString(i);
                String cardName = rank+suit;

                //System.out.print(cardName+" : ");


                draw = getResources().getIdentifier(cardName,"drawable", getPackageName());
                //System.out.println(draw);
                Card obj = new Card(j,i,draw);
                deck.add(obj);
            }
        }

        //System.out.println("Printing drawable id: ");
        //System.out.println(R.drawable.aceclubs);
        //System.out.println(getResources().getIdentifier("aceclubs","drawable", getPackageName()));



    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        /*if(flagCard == 0){
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
        }*/





    }

    private void showCard(boolean isBackShow, ImageView imgView, int index){

            /*if(isBackShow){
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

            }*/

    }

    //@Override
    public void onAnimationRepeat(Animation animation) {

    }

    //@Override
    public void onClick(View view) {

  /*      if (view.getId() == R.id.btnNew) {
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
*/
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
