package com.example.ltp50.cardgame;

import android.animation.ObjectAnimator;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class dealCard extends AppCompatActivity {


    ImageView one, two, three;
    Button btnNewGame, btnWifi, btnDiscover;
    Animation toMiddle;
    TextView ts;
    ListView ls;
    LinearLayout layoutNew;


    //STORAGE VARIABLES
    int score = 0;
    boolean oneBackShow=true, twoBackShow=true, threeBackShow=true;
    boolean flagPlayer = true;
    boolean run = true;
    int j = 0;
    int i = 0;

    //ARRAY VARIABLES
    List<String> cards = new ArrayList<>();
    List<Card> deck = new ArrayList<>();
    List<Card> playedCards = new ArrayList<>();

    List<Card> p1Deck = new ArrayList<>();
    List<Card> p2Deck = new ArrayList<>();

    List<ImageView> AiView = new ArrayList<>();

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;


    //OBJECT VARIABLES
    Card trumpCard;
    Player p1 = new Player(p1Deck, score);
    Player p2 = new Player(p2Deck, score);


    public void dealCards(Player p){
        System.out.println("Making new Deck");

        System.out.println("Dealing P1, main deck size : "+deck.size());


        //newDeck();
        //Collections.shuffle(deck);
        layoutNew = (LinearLayout)findViewById(R.id.german);
        //layoutNew.removeAllViews()
        if(!flagPlayer) {
            for(int i = 0; i < 5; i++){
                ImageView imageNew = (ImageView)findViewById(i+100);
                //image.setImageDrawable(null);
                //imageNew.setImageBitmap(null);
                //imageNew.destroyDrawingCache();
                imageNew.setImageResource(0);
                //layoutNew.addView(imageNew);
                layoutNew.removeView(imageNew);
            }
            //flagPlayer = true;
        }
        for(i = 0; i < 5; i++){
            //System.out.println(deck.get(i).getSuit());
            final ImageView image = new ImageView(getApplicationContext());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,1000,0,0);
            image.setMaxWidth(200);
            image.setMaxHeight(200);
            image.setImageResource(deck.get(i).getDrawable());
            //System.out.println(deck.get(i).getDrawable());
            image.setX(-100);
            image.setY(0);

            image.setLayoutParams(lp);
            image.setAdjustViewBounds(true);
            image.setScaleType(ImageView.ScaleType.FIT_END);
            image.setId(i+100);
            System.out.println("p1 image id : "+image.getId());

            System.out.print("Card id : "+image.getId());
//            /image.setVisibility(View.VISIBLE);
            layoutNew.addView(image);

            final Card rest = deck.get(i);

            p.deck.add(rest);

            deck.remove(i);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //playCard(rest, image, image.getId(),layoutNew);
                    Toast.makeText(getApplicationContext(),"AI Turn",Toast.LENGTH_SHORT).show();
                    /*try {
                    Thread.sleep(2000);
                    }
                    catch (InterruptedException e) {
                    e.printStackTrace();
                    }*/

                    Card play = p2Deck.get(i);
                    playCardAi(play, AiView.get(i), AiView.get(i).getId(),layoutNew);
                    //callAi();

                }
            });
        }
    }

    private void dealCardsAi(Player p){
        System.out.println("Making new Deck");
        System.out.println("Dealing p2, main deck : "+deck.size());

        //newDeck();
        //Collections.shuffle(deck);
        for(int k = 0; k < 5; k++){
            final ImageView image = new ImageView(getApplicationContext());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,1000,0,0);
            image.setMaxWidth(200);
            image.setMaxHeight(200);
            image.setImageResource(deck.get(k).getDrawable());
            //System.out.println(deck.get(i).getDrawable());
            image.setX(-100);
            image.setY(-20);

            image.setLayoutParams(lp);
            image.setAdjustViewBounds(true);
            image.setScaleType(ImageView.ScaleType.FIT_END);
            image.setId(k+200);

            System.out.println("p2 image id : "+image.getId());

            AiView.add(image);

            Card rest = deck.get(k);

            p.deck.add(rest);

            deck.remove(k);
        }
        Toast.makeText(getApplicationContext(),"Cards dealt to both of you",Toast.LENGTH_SHORT).show();
    }

    private void playCardAi(Card res, ImageView image, int id, LinearLayout l1){
        //l1 = (LinearLayout)findViewById(R.id.german);
        layoutNew = l1;
       /* //l1.removeViewInLayout((ImageView)findViewById(id));
            //final ImageView image = new ImageView( MainActivity.this );

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
            lp.setMargins( 0, 1000, 0, 0 );
            image.setMaxWidth( 200 );
            image.setMaxHeight( 200 );
            image.setImageResource( deck.get(id).getDrawable() );
            //System.out.println(deck.get(i).getDrawable());
            image.setX( -100 );
            image.setY( 0 );

            image.setLayoutParams( lp );
            image.setAdjustViewBounds( true );
            image.setScaleType( ImageView.ScaleType.FIT_END );
            //image.setId( i + 5 );*/




        if(image.getParent() == null) {
            layoutNew.addView(image);
        }
        else{
            layoutNew.removeView(image);
        }

        Toast.makeText(getApplicationContext(),"Waiting for AI Turn",Toast.LENGTH_SHORT).show();

        Animation anim_one = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aimiddle);
        //Animation anim_two = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_two);
        ObjectAnimator obj = ObjectAnimator.ofFloat(image,"x",400);
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(image, "y",8);
        obj.start();
        obj1.start();
        //img.startAnimation(anim_one);
        playedCards.add(res);
        //p1.deck.remove(res);
        /*if(p1.deck.size() == 0){
            getScore();
        }
*/
        //Toast.makeText(getApplicationContext(),"Your Turn, Go",Toast.LENGTH_SHORT).show();
    }


}
