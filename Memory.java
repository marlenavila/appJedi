package com.example.marlen.appjedi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

import java.util.Random;

public class Memory extends AppCompatActivity {

    //TODO toolbar al container amb opcio de reset on haure de cridar la funcio resetImage() i posar els attempts i tal a 0

    static CoolImageFlipper coolImageFlipper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.memory_options, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MemoryGameFragment())
                    .commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_memory);
        setSupportActionBar(toolbar);
        coolImageFlipper = new CoolImageFlipper(getApplicationContext());
    }

    public class MemoryGameFragment extends Fragment {
        private static final int NUM_IMAGES = 16;
        //private static final String LOG_TAG = MemoryGameFragment.class.getSimpleName();

        private boolean busy;
        private Handler handler = new Handler();
        private Integer attempts = 0;
        private TextView p;

        private ImageView[] images_list = new ImageView[NUM_IMAGES];
        private int[] images = new int[]{
                R.drawable.beemo,
                R.drawable.earl,
                R.drawable.marceline,
                R.drawable.ice_king,
                R.drawable.jake,
                R.drawable.finn,
                R.drawable.princess_bubblegum,
                R.drawable.gunter};
        private int[] ImageIndex = new int[NUM_IMAGES];

        //private Chronometer timer;
        private boolean firstPairRevealed;
        private boolean[] imagePermanentlyRevealed = new boolean[NUM_IMAGES];
        private int lastIndexClicked;
        private static final int NONE = -1;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_memory, container, false);

            // Get a reference to each button, store in our buttons array
            for(int i = 0; i < NUM_IMAGES; i++){
                images_list[i] = (ImageView) rootView.findViewById(getImageId("imageView" + i)); //agafar imatge per id
            }

            // Each image behaves the same; on click, call showPicture with a reference to the clicked button
            for(int i = 0; i < images_list.length; i++){
                final int index = i;
                images_list[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onImageClicked(index, images_list[index]);
                    }
                });
            }

            p = (TextView)rootView.findViewById(R.id.tv_punts);

            busy = false;
            lastIndexClicked = NONE;
            resetImages();

            return rootView;
        }

        /** A handy (but not recommended) way to retrieve a resource id by name in code. */
        private int getImageId(final String imageIdName){
            final int imageId = getResources().getIdentifier(imageIdName, "id", getActivity().getPackageName());
            /*if(0 == imageId){
                Log.e(LOG_TAG, "Could not find button with id " + imageIdName);
                throw new RuntimeException("Cannot find necessary button..");
            }*/
            return imageId;
        }

        /** Reset the image of each button to the default back. */
        private void resetImages(){
            for(int i = 0; i < imagePermanentlyRevealed.length; i++){
                imagePermanentlyRevealed[i] = false;
            }
            firstPairRevealed = false;

            for(ImageView i : images_list){
                i.setImageResource(R.drawable.adventuretime_logo);
            }

            randomizeImages();
        }

        private void randomizeImages(){
            // Randomize button images
            Random random = new Random();
            int[] count = new int[images.length];
            for(int i = 0; i < images_list.length; i++){
                // Ensure max 2 of any given image are chosen
                // Dirty solution, may not scale nicely depending on number of buttons / images
                int candidateImageIndex = random.nextInt(images.length);
                while(count[candidateImageIndex] >= 2){
                    candidateImageIndex = random.nextInt(images.length);
                }

                count[candidateImageIndex]++;
                ImageIndex[i] = candidateImageIndex;
            }
        }

        private void hideUnrevealedImages(){
            for(int i = 0; i < images_list.length; i++){
                if(imagePermanentlyRevealed[i]){
                    images_list[i].setImageResource(images[ImageIndex[i]]);
                } else {
                    images_list[i].setImageResource(R.drawable.adventuretime_logo);
                }
            }
        }

        private void onImageClicked(int indexClicked, ImageView i){
            if(busy) return;
            if(imagePermanentlyRevealed[indexClicked]) return; // Nothing to do here..

            // Set the image of the button to its corresponding picture.
           // i.setImageResource(images[ImageIndex[indexClicked]]);
            coolImageFlipper.flipImage(getDrawable(images[ImageIndex[indexClicked]]), i);
            if(NONE == lastIndexClicked){
                // This is the first button clicked.
                lastIndexClicked = indexClicked;

            } else if(lastIndexClicked != indexClicked){
                // A unique second button was clicked!
                if(!firstPairRevealed)
                    firstPairRevealed = true;

                if(match(lastIndexClicked, indexClicked)){
                    imagePermanentlyRevealed[lastIndexClicked] = true;
                    imagePermanentlyRevealed[indexClicked] = true;
                    lastIndexClicked = NONE;

                    ++attempts;
                    p.setText(attempts.toString()); //actualitzo punts
                    checkVictoryState();
                } else {
                    lastIndexClicked = NONE;
                    ++attempts;
                    p.setText(attempts.toString()); //actualitzo punts
                    // Do not let the user perform clicks while we are waiting
                    busy = true;

                    // Call the hideUnrevealedButtons method after a delay
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideUnrevealedImages();
                            busy = false;
                        }
                    }, 2000); //2 segons que han d'estar girades
                }
            }
        }

        /** @return true if buttons at given indices have an identical picture. */
        private boolean match(int firstImageIndex, int secondImageIndex){
            return ImageIndex[firstImageIndex] == ImageIndex[secondImageIndex];
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case R.id.resetDataMemory:
                    //TODO el reset este no funca, don't know why
                    resetImages();
                    attempts = 0;
                    p.setText("0");
                    return true;
                case R.id.logOutFromMemory:
                    SharedPreferences culo = getSharedPreferences("culo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = culo.edit();
                    editor.putString("userName", null); //buido el sharepreferences
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish(); //para q cuando tire atrás no vuelva al user_profile
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        }

        /** Checks if the player has won the game. If so, resets the game.*/
        private void checkVictoryState(){
            for(boolean revealed : imagePermanentlyRevealed){
                if(!revealed) return; // if any button has not been revealed, the game is on!
            }

            SharedPreferences sp = getSharedPreferences("culo", Context.MODE_PRIVATE);
            String nom = sp.getString("userName", null);
            DbHelper baseDades = new DbHelper(getApplicationContext());
            if(nom != null){
                Cursor c = baseDades.getUser(nom);
                if(c.moveToFirst()){
                    baseDades.updatePoints(nom, attempts);
                }
            }

            //TODO me peta siempre este dialog cuando acabo la partida, nose porqué, así que lo
            //TODO he puesto con un toast..el Dialog iba a tener dos botones para quedarse o para ir al ranking

           /* AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("You WIN!!!");
            builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resetImages();
                }
            });
            builder.setNegativeButton("Ranking", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(),Ranking.class);
                    startActivity(intent);
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();*/

            Toast.makeText(getApplicationContext(), "You WIN!!!!" , Toast.LENGTH_SHORT).show();

            p.setText("0");
            attempts = 0;

            // Reset the game after a short delay
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetImages();
                }
            }, 1400);
        }
    }
}
