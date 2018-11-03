package com.example.ltp50.cardgame;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener{

    //VISUAL VARIABLES
    ImageView one, two, three;
    Button btnNewGame, btnWifi, btnDiscover;
    Animation toMiddle;
    TextView ts;
    ListView ls;
    FrameLayout layoutNew;


    //STORAGE VARIABLES
    int score = 0;
    boolean oneBackShow=true, twoBackShow=true, threeBackShow=true;
    boolean flagPlayer = true;
    boolean flagAi = true;
    boolean run = true;
    int j = 0;
    int i = 0;
    int m = 0;
    int t = 0;
    int k = 0;

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

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    WifiManager wifiManager;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    IntentFilter intentFilter = new IntentFilter();
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

        btnWifi = findViewById(R.id.wifi);
        btnDiscover = findViewById(R.id.btnDiscover);
        btnNewGame = (Button)findViewById(R.id.btnNew);

        //ts = findViewById(R.id.tView);
        //ls = findViewById(R.id.lView);

       /* wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this,getMainLooper(),null);
        mReceiver = new WifiDirectBroadcastReceiver(mManager,mChannel,this);
        mIntentFilter =new IntentFilter();

        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        exqListener();*/

        final dealCard d1 = new dealCard();

        toMiddle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_middle);
        toMiddle.setAnimationListener(this);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flagPlayer){
                    layoutNew.removeAllViews();
                }
                System.out.println("Button Clicked");

                System.out.println(p1Deck.size());
                newDeck();
                Collections.shuffle(deck);

                trumpCard();
                //p1Deck.clear();
                //p2Deck.clear();
                dealCards(p1);
                dealCardsAi(p2);
                flagPlayer = false;
            }
        });

    }
/*    WifiP2pManager.PeerListListener peerListListener = new PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                int index = 0;

                for(WifiP2pDevice device : peerList.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    //ts.setText(deviceNameArray[index]);
                    //ts.setText("Meet");
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,deviceNameArray);
                ls.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Devices Found",Toast.LENGTH_SHORT).show();
                //ts.setText("Meet");
            }
        }
    };*/

    @SuppressLint("ResourceType")
    private void trumpCard(){
            /*if(!flagPlayer) {
                    ImageView imageNew = (ImageView)findViewById(trumpCard.getImageId());
                    System.out.println("Trump card id is: "+trumpCard.getImageId());
                    imageNew.setImageResource(0);
                    layoutNew.removeView(imageNew);
            }*/
            trumpCard = deck.get(1);
            layoutNew = (FrameLayout) findViewById(R.id.german);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,500,0,0);

            ImageView image = new ImageView(MainActivity.this);
            image.setMaxWidth(300);
            image.setMaxHeight(300);
            image.setX(500);
            image.setY(0);
            image.setImageResource(deck.get(1).getDrawable());
            image.setLayoutParams(lp);
            image.setAdjustViewBounds(true);
            image.setId(deck.get(1).getImageId());
            layoutNew.addView(image);

            deck.remove(1);
    }

    private void dealCards(Player p){
        System.out.println("Making new Deck");
        layoutNew = (FrameLayout) findViewById(R.id.german);
        if(!flagPlayer) {
            /*for(int h = 0; h < 5; h++){
                ImageView imageNew = (ImageView)findViewById(p.deck.get(h).getImageId());

                imageNew.setImageResource(0);
                layoutNew.removeView(imageNew);
            }*/
            p1Deck.clear();
        }

        for(i = 0; i < 5; i++){
            int nobj = i;
            int obj = (nobj*60)+20;
            System.out.println("in for loop");
            final ImageView image = new ImageView(MainActivity.this);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,1000,0,0);
            image.setMaxWidth(200);
            image.setMaxHeight(200);
            image.setImageResource(deck.get(i).getDrawable());
            image.setX(300+obj);
            image.setY(0);

            image.setLayoutParams(lp);
            image.setAdjustViewBounds(true);
            image.setScaleType(ImageView.ScaleType.FIT_END);
            image.setId(deck.get(i).getImageId());

            System.out.print("Card id : "+image.getId());
            System.out.print("Card id : "+deck.get(i).getImageId());
            //final Card rest = p.deck.get(i);
            layoutNew.addView(image);

            p.deck.add(deck.get(i));

            deck.remove(i);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(getApplicationContext(),"AI Turn",Toast.LENGTH_SHORT).show();
                    playCard(p1Deck.get(i), image, layoutNew);
                    System.out.println("The card you selected is "+view.getId());
                    //Card play = p2Deck.get(j);
                    playCardAi(layoutNew, t);
                    t++;
                    flagAi = false;

                }
            });


        }
        i = 0;
        t = 0;
    }

    private void callAi(){
        if(j == p2Deck.size()){
            j = 0;
        }
        else {
            Card play = p2Deck.get(j);
            //playCardAi(play, AiView.get(j), AiView.get(j).getId(),layoutNew);
            j++;
        }
    }

    private void dealCardsAi(Player p){

        if(!flagPlayer) {
            //AiView.clear();
            int large = AiView.size();
            for(int b = 0; b < large; b++){
                //ImageView imageNew = (ImageView)findViewById(p.deck.get(b).getImageId());
                //imageNew.setImageResource(0);
                layoutNew.removeView(AiView.get(b));

                //AiView.remove(b);
            }
            p2Deck.clear();
        }
            for (k = 0; k < 5; k++) {
                int nobj = k;
                int obj = (nobj*10)+20;
                ImageView image = new ImageView( MainActivity.this );

                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT );
                lp.setMargins( 0, 1000, 0, 0 );

                image.setMaxWidth(200);
                image.setMaxHeight(200);
                image.setImageResource( deck.get( k ).getDrawable() );
                image.setX(100);
                image.setY(0);

                image.setLayoutParams( lp );
                image.setAdjustViewBounds( true );
                image.setScaleType( ImageView.ScaleType.FIT_END );
                image.setId(deck.get(k).getImageId() );


                AiView.add(image);

                Card rest = deck.get( k );

                p.deck.add( deck.get( k ) );

                deck.remove(k);
            }
            k = 0;
        //}
        Toast.makeText(getApplicationContext(),"Cards dealt to both of you",Toast.LENGTH_SHORT).show();
    }

    private void playCard(Card res, ImageView img, FrameLayout l1){

        Toast.makeText(getApplicationContext(),"Waiting for AI Turn",Toast.LENGTH_SHORT).show();

        Animation anim_one = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.newmiddle);

        ObjectAnimator obj = ObjectAnimator.ofFloat(img,"x",400);
        ObjectAnimator obj1 = ObjectAnimator.ofFloat(img, "y",200);
        obj.start();
        obj1.start();

        playedCards.add(res);

        //Toast.makeText(getApplicationContext(),"Your Turn, Go",Toast.LENGTH_SHORT).show();
    }

    private void playCardAi(FrameLayout l1, int d){
        //l1 = (LinearLayout)findViewById(R.id.german);
        System.out.println(d+"ai card");
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




        if(AiView.get(d).getParent() == null) {
            if(!flagAi){
                layoutNew.removeView(AiView.get(d));
                AiView.remove(d);
                //p2Deck.clear();
            }
            layoutNew.addView(AiView.get(d));
            //p2Deck.remove(m);
            //AiView.remove(m);
        }
        //else{

          //  layoutNew.removeView(AiView.get(m));
            //layoutNew.removeView(AiView.get(1));
            //layoutNew.removeView(AiView.get(2));
            //layoutNew.removeView(AiView.get(3));
            //layoutNew.removeView(AiView.get(4));
            //p2Deck.remove(m);
          //  AiView.remove(m);
        //}

        Toast.makeText(getApplicationContext(),"Waiting for AI Turn",Toast.LENGTH_SHORT).show();


        //Animation anim_one = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aimiddle);
        //Animation anim_two = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_two);
        //ObjectAnimator obj = ObjectAnimator.ofFloat(image,"x",400);
        //ObjectAnimator obj1 = ObjectAnimator.ofFloat(image, "y",8);
        //obj.start();
        //obj1.start();
        //img.startAnimation(anim_one);
        //playedCards.add(res);
        //p1.deck.remove(res);
        /*if(p1.deck.size() == 0){
            getScore();
        }

*/
        //m++;
        //AiView.remove(m);
        //Toast.makeText(getApplicationContext(),"Your Turn, Go",Toast.LENGTH_SHORT).show();
    }



    private void getScore(){
        for(int i = 0; i < playedCards.size(); i++){

        }
    }

    private void newDeck(){
        int draw = 0;
        deck.clear();


        int idCounter = 0;

        for(int i = 1; i <= 4; i++){
            for(int j = 2; j <= 14; j++){
                String rank = Card.rankToString(j);
                String suit = Card.suitToString(i);
                String cardName = rank+suit;
                //System.out.print(cardName+" : ");
                draw = getResources().getIdentifier(cardName,"drawable", getPackageName());
                //System.out.println(draw);
                Card obj = new Card(j,i,draw,idCounter);
                deck.add(obj);
                idCounter++;
            }
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        //final ImageView image = new ImageView(MainActivity.this);



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


    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mReceiver);
    }

    private void exqListener() {

        btnWifi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(false);
                }
                else{
                    wifiManager.setWifiEnabled(true);
                }
            }
        } );

        btnDiscover.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManager.discoverPeers( mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        //Toast.makeText(context,"Wifi is ON",Toast.LENGTH_SHORT).show();
                        btnWifi.setTextColor(4);
                    }

                    @Override
                    public void onFailure(int i) {
                        btnWifi.setTextColor(9);
                    }
                } );
            }
        } );

    }


}
