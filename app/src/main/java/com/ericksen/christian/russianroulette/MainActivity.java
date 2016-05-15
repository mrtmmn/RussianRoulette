package com.ericksen.christian.russianroulette;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;
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

    boolean isChamberLoaded;

    int ranIntToDetermineRotation;
    int temp = 0;

    int [] iVCCOnScreen = new int [2];
    int [] iVCCInWindow = new int [2];

    int [] rLCOnScreen = new int [2];
    int [] rLCInWindow = new int [2];

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
        buttonForSpinning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                randomIntForRotation();
                Log.d("rotation-onclick", "ranIntToDetermineRotation: " + ranIntToDetermineRotation);

                ObjectAnimator rotateBarrel =
                        ObjectAnimator.ofFloat(relativeLayoutCylinder, View.ROTATION, ranIntToDetermineRotation);
                rotateBarrel.start();
                // Spin the recyclerview 

                Log.d("ranInt_rotation", "ObjectAnimator: " + ranIntToDetermineRotation);

                //Now I need to add an onAnimationListener so to be able to add a crescendo in the beginning
                //and a decrescendo at the end in terms of speed so to mimick the force of gravity

                Log.d("temp_variable", "ObjectAnimator: " + temp);

                temp = ranIntToDetermineRotation;
                //keep track of the relativelayout's positioning

                Log.d("temp_variable_switch", "ObjectAnimator: " + temp);
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

                randomIntForLoading();
                Log.d("isChamberLoaded-onclick", "randomIntForLoading: " + isChamberLoaded);

                Resources res = getResources();

                if (!isChamberLoaded) {
                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toastTwo.show();
                    //determine what the current position of the animated object is so that I can incorporate another if statement
                    //which will determine which imageview is at the top and if there is a need to change the drawable from full to empty

                } else if (isChamberLoaded) {
                    switch (temp) {

                        case 1: temp = 360;
                                if (imageViewChamber1.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
                                    imageViewChamber1.setImageResource(R.drawable.chamber_empty);
                                } else {
                                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toastTwo.show();
                                } break;

                        case 2: temp = 300;
                                if (imageViewChamber6.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
                                    imageViewChamber6.setImageResource(R.drawable.chamber_empty);
                                } else {
                                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toastTwo.show();
                                } break;

                        case 3: temp = 240;
                                if (imageViewChamber5.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
                                    imageViewChamber5.setImageResource(R.drawable.chamber_empty);
                                } else {
                                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toastTwo.show();
                                } break;

                        case 4: temp = 180;
                                if (imageViewChamber4.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
                                    imageViewChamber4.setImageResource(R.drawable.chamber_empty);
                                } else {
                                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toastTwo.show();
                                } break;

                        case 5: temp = 120;
                                if (imageViewChamber3.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
                                    imageViewChamber3.setImageResource(R.drawable.chamber_empty);
                                } else {
                                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toastTwo.show();
                                } break;

                        case 6: temp = 60;
                                if (imageViewChamber2.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
                                    imageViewChamber2.setImageResource(R.drawable.chamber_empty);
                                } else {
                                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toastTwo.show();
                                } break;

                        case 7: temp = 0;
                                if (imageViewChamber1.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
                                    imageViewChamber1.setImageResource(R.drawable.chamber_empty);
                                } else {
                                    Toast toastTwo = Toast.makeText(MainActivity.this, "You're safe this time. Pass it to the next person.", Toast.LENGTH_SHORT);
                                    toastTwo.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    toastTwo.show();
                                } break;

                        default: break;
                    }

                }

            }
        });

    }

    public int randomIntForRotation () {

        Random randomRotation = new Random();
        int intForRotation = randomRotation.nextInt((6-1)+1)+1;
        //from 0 to 5 since (5+1) or ((6-1)+1) with an additional + 1 outside of the parentheses
        // so [0...5] becomes [1...6]

        ranIntToDetermineRotation = (intForRotation * 60);
        //ranIntToDetermineRotation will be either 60, 120, 180, 240, 300, or 360
        Log.d("jiggery-pokery", "ranIntToDetermineRotation: " + ranIntToDetermineRotation);

        return ranIntToDetermineRotation;

    }

    public boolean randomIntForLoading () {

        Random randomRotation = new Random();
        int intForLoading = randomRotation.nextInt(100);

        Log.d("intForLoading", "randomIntForLoading: " + intForLoading);

        if (intForLoading >= 0 && intForLoading <= 25) {
            isChamberLoaded = true;
        } else if (intForLoading >= 26 && intForLoading <= 100) {
            isChamberLoaded = false;
        } else {
            randomIntForLoading();
        }

        Log.d("isChamberLoaded", "randomIntForLoading: " + isChamberLoaded);

        return isChamberLoaded;

    }

}













































//                PropertyValuesHolder rotationX = PropertyValuesHolder.ofFloat(View.ROTATION, temp, ranIntToDetermineRotation);
//                PropertyValuesHolder rotationY = PropertyValuesHolder.ofFloat(View.ROTATION, temp, ranIntToDetermineRotation);
//
//                ObjectAnimator rotateBarrel =
//                        ObjectAnimator.ofPropertyValuesHolder(relativeLayoutCylinder, rotationX, rotationY);
//                rotateBarrel.setDuration(1000);
//                rotateBarrel.start();


//                ObjectAnimator rotateBarrel =
//                        ObjectAnimator.ofFloat(relativeLayoutCylinder, View.ROTATION, Math.abs(temp), Math.abs(ranIntToDetermineRotation));
//                rotateBarrel.start();





//    boolean chamber1Loaded = false;
//    boolean chamber2Full;
//    boolean chamber2Loaded = false;
//    boolean chamber3Full;
//    boolean chamber3Loaded = false;
//    boolean chamber4Full;
//    boolean chamber4Loaded = false;
//    boolean chamber5Full;
//    boolean chamber5Loaded = false;
//    boolean chamber6Full;
//    boolean chamber6Loaded = false;
//    boolean isLoaded =false;
//    boolean isFull;
//    String Loaded;

//    static Random rand = new Random();

////            Random  rand = new Random();
//            int randomBarrel = rand.nextInt(7 - 1) + 1;


//            if (randomBarrel == 1) {chamber1Loaded = true;}
//
//            //chamber2Loaded = false; chamber3Loaded = false; chamber4Loaded = false; chamber5Loaded = false; chamber6Loaded = false;
//
//            if (randomBarrel == 2) {chamber2Loaded = true;}
//
//            if (randomBarrel == 3) {chamber3Loaded = true;}
//
//            if (randomBarrel == 4) {chamber4Loaded = true;}
//
//            if (randomBarrel == 5) {chamber5Loaded = true;}
//
//            if (randomBarrel == 6) {chamber6Loaded = true;} }




//        if (chamber1Loaded) {
//            chamber1.equals(isLoaded);
//
//        } else if (chamber2Loaded) {
//            chamber2.equals(isLoaded);
//
//        } else if (chamber3Loaded) {
//            chamber3.equals(isLoaded);
//
//        } else if (chamber4Loaded) {
//            chamber4.equals(isLoaded);
//
//        } else if (chamber5Loaded) {
//            chamber5.equals(isLoaded);
//
//        } else if (chamber6Loaded) {
//            chamber6.equals(isLoaded);
//        }
//
//        randomSpinDegrees();
//
//        final RelativeLayout relativeLayoutCylinder = (RelativeLayout) findViewById(R.id.relative_layout_cylinder);
//
//
////        final RotateAnimation rotate1 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
////        //RotateAnimation rotate = new RotateAnimation(0, 360, 90, 90);
////        rotate1.setDuration(1000);
////        rotate1.setInterpolator(new LinearInterpolator());
////
////       final RotateAnimation rotate2 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
////        rotate2.setDuration(3000);
////        rotate2.setInterpolator(new DecelerateInterpolator());
//
//
//
//        //randomSpinDegrees degreesClass = new randomSpinDegrees();
//
//
//
//        spin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
////
////                    int randomDegrees = (int)(Math.random()*6 + 1);
////                    String degrees = "";
////
////                    float n;
////
////
////                    // Random randomSpin = new Random();
////                    //int randomDegrees = randomSpin.nextInt(7 - 1) + 1;
////
////                    public void main(String[] args) {
////
////                        for (int i = 0; i <= 6; i++){
////
////                            switch (randomDegrees) {
////                                case 0:
////                                    degrees = "420";
////                                    break;
////                                case 1:
////                                    degrees = "480";
////                                    break;
////                                case 2:
////                                    degrees = "540";
////                                    break;
////                                case 3:
////                                    degrees = "600";
////                                    break;
////                                case 4:
////                                    degrees = "660";
////                                    break;
////                                case 5:
////                                    degrees = "720";
////                                    break;
////                                default:
////                                    degrees = "0";
////                            }
////                        }
////                        float n = Float.parseFloat(degrees);
////                        System.out.print(n);
//
//
//                final RotateAnimation rotate1 = new RotateAnimation(0, n, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                        //RotateAnimation rotate = new RotateAnimation(0, 360, 90, 90);
//                rotate1.setDuration(1000);
//                rotate1.setInterpolator(new LinearInterpolator());
//
//                final RotateAnimation rotate2 = new RotateAnimation(0, 420, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                rotate2.setDuration(3000);
//                rotate2.setInterpolator(new DecelerateInterpolator());
//
//
//                setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        assert relativeLayoutCylinder != null;
//                        relativeLayoutCylinder.startAnimation(rotate1);
//                        relativeLayoutCylinder.startAnimation(rotate2);
//                        relativeLayoutCylinder.setPivotX(relativeLayoutCylinder.getWidth() / 2);
//                        relativeLayoutCylinder.setPivotY(relativeLayoutCylinder.getHeight() / 2);
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//
//
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation rotate) {
//
//                    }
//                });
//
//
//
////                        assert relativeLayoutCylinder != null;
////                        relativeLayoutCylinder.startAnimation(rotate1);
////                        relativeLayoutCylinder.startAnimation(rotate2);
////                        relativeLayoutCylinder.setPivotX(relativeLayoutCylinder.getWidth() / 2);
////                        relativeLayoutCylinder.setPivotY(relativeLayoutCylinder.getHeight() / 2);
////                        relativeLayoutCylinder.setSaveEnabled(true);
//                    }

//switch (temp) {
//case 1: temp / 360 == 1 || temp - 0 == 0
//        if (imageViewChamber1.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber1.setImageResource(R.drawable.chamber_empty);
//        }
//        else if (temp / 300 == 1) {
//        if (imageViewChamber6.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber6.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 240 == 1) {
//        if (imageViewChamber5.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber5.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 180 == 1) {
//        if (imageViewChamber4.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber4.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 120 == 1) {
//        if (imageViewChamber3.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber3.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 60 == 1) {
//        if (imageViewChamber2.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber2.setImageResource(R.drawable.chamber_empty);
//        }
//        }
//        }

//        if (temp / 360 == 1 || temp - 0 == 0) {
//        if (imageViewChamber1.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber1.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 300 == 1) {
//        if (imageViewChamber6.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber6.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 240 == 1) {
//        if (imageViewChamber5.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber5.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 180 == 1) {
//        if (imageViewChamber4.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber4.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 120 == 1) {
//        if (imageViewChamber3.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber3.setImageResource(R.drawable.chamber_empty);
//        }
//        } else if (temp / 60 == 1) {
//        if (imageViewChamber2.getBackground() == res.getDrawable(R.drawable.chamber_filled)) {
//        imageViewChamber2.setImageResource(R.drawable.chamber_empty);
//        }
//        }
//
//
//
//                });
//
//
//                //chambe
////                chamber2.startAnimation(rotate);
////                chamber3.startAnimation(rotate);
////                chamber4.startAnimation(rotate);
////                chamber5.startAnimation(rotate);
////                chamber6.startAnimation(rotate);
//
//
//
//        shoot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                   if (isFull && (isLoaded=true)){
//                       Toast.makeText(MainActivity.this, "BAM! Take A Shot!", Toast.LENGTH_SHORT).show();
//                   }
//                if (isFull && (isLoaded=false)){
//
//                }
//
//                if (isFull=false){
//
//                }
//
//
//
//            }
//        });
//    }
//
//    private void setAnimationListener(Animation.AnimationListener animationListener) {
//    }
//
//    public float randomSpinDegrees(){
//
//        int mDegree = rand.nextInt(7-1) + 1;
//
//        if(mDegree == 1) {
//            n = 360;
//        }else if(mDegree == 2){
//            n = 420;
//
//        }else if(mDegree == 3)
//        {
//            n = 480;
//        }else if(mDegree == 4)
//        {
//            n = 540;
//        }else if(mDegree==5)
//
//        {
//            n = 600;
//        }else if(mDegree==6)
//
//        {
//            n = 660;
//        }
//
//        return n;
//
//    }
//}
//
//
//
////        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
////        chamber1.startAnimation(animRotate);
////        chamber1.setPivotX(0);
////        chamber1.setPivotY(0);
////
////        animRotate.setAnimationListener(new Animation.AnimationListener() {
////            @Override
////            public void onAnimationStart(Animation animation) {
////                chamber1.getPivotX();
////                chamber1.getPivotY();
////
////            }
////
////            @Override
////            public void onAnimationEnd(Animation animation) {
////
////            }
////
////            @Override
////            public void onAnimationRepeat(Animation rotate) {
////
////            }
////        });