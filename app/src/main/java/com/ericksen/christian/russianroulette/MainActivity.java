package com.ericksen.christian.russianroulette;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewChamber1;
    ImageView imageViewChamber2;
    ImageView imageViewChamber3;
    ImageView imageViewChamber4;
    ImageView imageViewChamber5;
    ImageView imageViewChamber6;
    ImageView imageViewCenter;

    Button buttonForShooting;
    Button buttonForSpinning;

    RelativeLayout relativeLayoutCylinder;
    //contains all of the imageViewChambers

    boolean isChamberLoaded;
    //algorithm - method: generateIntForLoading() - determined whether the chamber is loaded or not as opposed to filled with a blank
    //this also generated a 25% (loaded) v 75% chance (blank) but the next step is to elaborate
    //and provide users the choice regarding the difficulty of the odds they face

    int intForRotation;
    int numberOfRotations = 0;
    int ranIntToDetermineRotation;
    int temp = 0;
    int count = 0;
    //attempt at determining the position of the ImageViews via calculating the relationship bw the ImageViews'
    //displacement through rotation (in degrees) and the ImageView positioned at the top after every rotation
    //but I found a better way through the getLocationOnScreen method which gave me the estimated y-axis of each
    //ImageView for comparison

    int[] iVCCOnScreen = new int[2];
    int[] iVCCInWindow = new int[2];
    //determine centerX and centerY for relative_layout_cylinder so not to have to depend on animate the
    //entire relative_layout as opposed to just rotating all of the imageviews along a pivot

    int[] rLCOnScreen = new int[2];
    int[] rLCInWindow = new int[2];
    //exact coordinates for ImageViews so to incorporate more graphics in addition to rotation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayoutCylinder = (RelativeLayout) findViewById(R.id.relative_layout_cylinder);

        imageViewChamber1 = (ImageView) findViewById(R.id.chamber1);
        imageViewChamber2 = (ImageView) findViewById(R.id.chamber2);
        imageViewChamber3 = (ImageView) findViewById(R.id.chamber3);
        imageViewChamber4 = (ImageView) findViewById(R.id.chamber4);
        imageViewChamber5 = (ImageView) findViewById(R.id.chamber5);
        imageViewChamber6 = (ImageView) findViewById(R.id.chamber6);
        imageViewCenter = (ImageView) findViewById(R.id.center);

        buttonForSpinning = (Button) findViewById(R.id.spin);
        //Declare this onClickListener so that I can use its variable name to ensure that
        //users can't shoot without spinning the barrel
        buttonForSpinning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfRotations++;

                generateIntForRotation();
                Log.d("rotation-onclick", "ranIntToDetermineRotation: " + ranIntToDetermineRotation);

                ObjectAnimator rotateBarrel =
                        ObjectAnimator.ofFloat(relativeLayoutCylinder, View.ROTATION, ranIntToDetermineRotation);
                rotateBarrel.start();
                // Spin the recyclerview

                Log.d("ranInt_rotation", "ObjectAnimator: " + ranIntToDetermineRotation);

                //Now I need to add an onAnimationListener so to be able to add a crescendo in the beginning
                //and a decrescendo at the end in terms of speed so to mimick the force of gravity

                Log.d("pre-dIVP", "ObjectAnimator: " + temp);
                Log.d("count_variable", "ObjectAnimator: " + count);
                //add animation that takes ranIntToDetermineRotation as a value for transformation

                if (imageViewCenter != null) {

                    imageViewCenter.getLocationOnScreen(iVCCOnScreen);
                    Log.d("iVCCOnScreen-X", "iVCCOnScreen-X: " + iVCCOnScreen[0]);
                    Log.d("iVCCOnScreen-Y", "iVCCOnScreen-Y: " + iVCCOnScreen[1]);
                    //should have a (1/2x, 1/2y) values in comparison to the relative layout
                    //window vs. screen => I think screen would be more constant and somehow related to the pixel dimensions

                    imageViewCenter.getLocationInWindow(iVCCInWindow);
                    Log.d("iVCCInWindow-X", "iVCCInWindow: " + iVCCInWindow[0]);
                    Log.d("iVCCInWindow-Y", "iVCCInWindow: " + iVCCInWindow[1]);

                }

                if (relativeLayoutCylinder != null) {
                    relativeLayoutCylinder.getLocationOnScreen(rLCOnScreen);
                    //imageViewCenter should have the following coordinates (.5x, .5y) if relativeLayoutCylinder is (x, y)
                    //so whichever imageViewChamber is on top after the animation stops will be (~.7x, .5y) ->
                    //so I just have to look for whichever imageview has the exact same y value but a greater x value
                    Log.d("rLCOnScreen-X", "rLCOnScreen-X: " + rLCOnScreen[0]);
                    Log.d("rLCOnScreen-Y", "rLCOnScreen-Y: " + rLCOnScreen[1]);

                    relativeLayoutCylinder.getLocationInWindow(rLCInWindow);
                    Log.d("rLCInWindow-X", "rLCInWindow: " + rLCInWindow[0]);
                    Log.d("rLCInWindow-Y", "rLCInWindow: " + rLCInWindow[1]);

                }

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {

                    if (relativeLayoutCylinder != null) {

                        int left = relativeLayoutCylinder.getBackground().getBounds().left;
                        int right = relativeLayoutCylinder.getBackground().getBounds().right;
                        int bottom = relativeLayoutCylinder.getBackground().getBounds().bottom;
                        int top = relativeLayoutCylinder.getBackground().getBounds().top;

                        try {
                            Log.d("left", "left: " + left);
                            Log.d("right", "right: " + right);
                            Log.d("bottom", "bottom: " + bottom);
                            Log.d("top", "top: " + top);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

        buttonForShooting = (Button) findViewById(R.id.shoot);
        buttonForShooting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //keep track of the imageviews positioning within the relative_layout_cylinder

                int [] arrayChamber1 = new int [2];
                int [] arrayChamber2 = new int [2];
                int [] arrayChamber3 = new int [2];
                int [] arrayChamber4 = new int [2];
                int [] arrayChamber5 = new int [2];
                int [] arrayChamber6 = new int [2];

                imageViewChamber1.getLocationOnScreen(arrayChamber1);
                imageViewChamber2.getLocationOnScreen(arrayChamber2);
                imageViewChamber3.getLocationOnScreen(arrayChamber3);
                imageViewChamber4.getLocationOnScreen(arrayChamber4);
                imageViewChamber5.getLocationOnScreen(arrayChamber5);
                imageViewChamber6.getLocationOnScreen(arrayChamber6);

                int heightOfChamber1 = arrayChamber1[1];
                int heightOfChamber2 = arrayChamber2[1];
                int heightOfChamber3 = arrayChamber3[1];
                int heightOfChamber4 = arrayChamber4[1];
                int heightOfChamber5 = arrayChamber5[1];
                int heightOfChamber6 = arrayChamber6[1];

                generateIntForLoading();
                Log.d("isChamberLoaded-onclick", "generateIntForLoading: " + isChamberLoaded);

                if (!isChamberLoaded) {

                    Toast toastOne = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                    toastOne.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toastOne.show();
                    //determine what the current position of the animated object is so that I can incorporate another if statement
                    //which will determine which imageview is at the top and if there is a need to change the drawable from full to empty

                } else {

                    Toast toastTwo = Toast.makeText(MainActivity.this, "You've been shot so you have to take a shot.", Toast.LENGTH_SHORT);
                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toastTwo.show();

                    Log.d("intForRotation-switch", "switch: " + intForRotation);

                    if (heightOfChamber1 < heightOfChamber2 && heightOfChamber1 < heightOfChamber3 &&
                            heightOfChamber1 < heightOfChamber4 && heightOfChamber1 < heightOfChamber5
                            && heightOfChamber1 < heightOfChamber6) {

                        imageViewChamber1.setBackgroundResource(R.drawable.chamber_empty);

                    } else if (heightOfChamber2 < heightOfChamber1 && heightOfChamber2 < heightOfChamber3 &&
                            heightOfChamber2 < heightOfChamber4 && heightOfChamber2 < heightOfChamber5
                            && heightOfChamber2 < heightOfChamber6){

                        imageViewChamber2.setBackgroundResource(R.drawable.chamber_empty);

                    } else if (heightOfChamber3 < heightOfChamber2 && heightOfChamber3 < heightOfChamber1 &&
                            heightOfChamber3 < heightOfChamber4 && heightOfChamber3 < heightOfChamber5
                            && heightOfChamber3 < heightOfChamber6) {

                        imageViewChamber3.setBackgroundResource(R.drawable.chamber_empty);

                    } else if (heightOfChamber4 < heightOfChamber1 && heightOfChamber4 < heightOfChamber3 &&
                            heightOfChamber4 < heightOfChamber2 && heightOfChamber4 < heightOfChamber5
                            && heightOfChamber4 < heightOfChamber6) {

                        imageViewChamber4.setBackgroundResource(R.drawable.chamber_empty);

                    } else if (heightOfChamber5 < heightOfChamber2 && heightOfChamber5 < heightOfChamber3 &&
                            heightOfChamber5 < heightOfChamber4 && heightOfChamber1 > heightOfChamber5
                            && heightOfChamber5 < heightOfChamber6) {

                        imageViewChamber5.setBackgroundResource(R.drawable.chamber_empty);

                    } else if (heightOfChamber6 < heightOfChamber2 && heightOfChamber6 < heightOfChamber3 &&
                            heightOfChamber6 < heightOfChamber4 && heightOfChamber6 < heightOfChamber5
                            && heightOfChamber6 < heightOfChamber1) {

                        imageViewChamber6.setBackgroundResource(R.drawable.chamber_empty);

                    }
                }

            }
        });

    }

    public int generateIntForRotation() {

        Random randomRotation = new Random();
        intForRotation = randomRotation.nextInt((6 - 1) + 1) + 1;
        //from 0 to 5 since (5+1) or ((6-1)+1) with an additional + 1 outside of the parentheses
        // so [0...5] becomes [1...6]
        Log.d("intForRotation", "intForRotation: " + intForRotation);

        ranIntToDetermineRotation = (intForRotation * 60);
        //ranIntToDetermineRotation will be either 60, 120, 180, 240, 300, or 360
        Log.d("jiggery-pokery", "ranIntToDetermineRotation: " + ranIntToDetermineRotation);

        return ranIntToDetermineRotation;

    }

    public boolean generateIntForLoading() {

        Random randomRotation = new Random();
        int intForLoading = randomRotation.nextInt(100);

        Log.d("intForLoading", "generateIntForLoading: " + intForLoading);

        //Need to fix the probability that determines whether a fire will be shot or not
        //so that it's more fun to actually play
        if (intForLoading >= 0 && intForLoading <= 25) {
            isChamberLoaded = true;
        } else if (intForLoading >= 26 && intForLoading <= 100) {
            isChamberLoaded = true;
        } else {
            generateIntForLoading();
        }

        Log.d("isChamberLoaded", "booleanForLoading: " + isChamberLoaded);

        return isChamberLoaded;

    }
}