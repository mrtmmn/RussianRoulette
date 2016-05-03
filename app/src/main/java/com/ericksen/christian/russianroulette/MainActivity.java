package com.ericksen.christian.russianroulette;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;


//@SuppressWarnings("ResultOfMethodCallIgnored")
public class MainActivity extends AppCompatActivity {

    ImageView chamber1;
    ImageView chamber2;
    ImageView chamber3;
    ImageView chamber4;
    ImageView chamber5;
    ImageView chamber6;
    ImageView center;
    Button shoot;
    Button spin;
    float n;
    //ImageView arrow;


    boolean chamber1Loaded = false;
    boolean chamber2Full;
    boolean chamber2Loaded = false;
    boolean chamber3Full;
    boolean chamber3Loaded = false;
    boolean chamber4Full;
    boolean chamber4Loaded = false;
    boolean chamber5Full;
    boolean chamber5Loaded = false;
    boolean chamber6Full;
    boolean chamber6Loaded = false;
    boolean isLoaded =false;
    boolean isFull;
    String Loaded;

    static Random rand = new Random();

    public class fire {

        public void main(String[] args) {



//            Random  rand = new Random();
            int randomBarrel = rand.nextInt(7 - 1) + 1;


            if (randomBarrel == 1) {chamber1Loaded = true;}

            //chamber2Loaded = false; chamber3Loaded = false; chamber4Loaded = false; chamber5Loaded = false; chamber6Loaded = false;

            if (randomBarrel == 2) {chamber2Loaded = true;}

            if (randomBarrel == 3) {chamber3Loaded = true;}

            if (randomBarrel == 4) {chamber4Loaded = true;}

            if (randomBarrel == 5) {chamber5Loaded = true;}

            if (randomBarrel == 6) {chamber6Loaded = true;}
        }




    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chamber1 = (ImageView) findViewById(R.id.chamber1);
        chamber2 = (ImageView) findViewById(R.id.chamber2);
        chamber3 = (ImageView) findViewById(R.id.chamber3);
        chamber4 = (ImageView) findViewById(R.id.chamber4);
        chamber5 = (ImageView) findViewById(R.id.chamber5);
        chamber6 = (ImageView) findViewById(R.id.chamber6);
        center = (ImageView) findViewById(R.id.center);
        shoot = (Button) findViewById(R.id.shoot);
        spin = (Button) findViewById(R.id.spin);
        //arrow = (ImageView) findViewById(R.id.arrow);

        if (chamber1Loaded) {
            chamber1.equals(isLoaded);

        } else if (chamber2Loaded) {
            chamber2.equals(isLoaded);

        } else if (chamber3Loaded) {
            chamber3.equals(isLoaded);

        } else if (chamber4Loaded) {
            chamber4.equals(isLoaded);

        } else if (chamber5Loaded) {
            chamber5.equals(isLoaded);

        } else if (chamber6Loaded) {
            chamber6.equals(isLoaded);
        }

        randomSpinDegrees();

        final RelativeLayout relativeLayoutCylinder = (RelativeLayout) findViewById(R.id.relative_layout_cylinder);


//        final RotateAnimation rotate1 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        //RotateAnimation rotate = new RotateAnimation(0, 360, 90, 90);
//        rotate1.setDuration(1000);
//        rotate1.setInterpolator(new LinearInterpolator());
//
//       final RotateAnimation rotate2 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotate2.setDuration(3000);
//        rotate2.setInterpolator(new DecelerateInterpolator());



        //randomSpinDegrees degreesClass = new randomSpinDegrees();



        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




//
//                    int randomDegrees = (int)(Math.random()*6 + 1);
//                    String degrees = "";
//
//                    float n;
//
//
//                    // Random randomSpin = new Random();
//                    //int randomDegrees = randomSpin.nextInt(7 - 1) + 1;
//
//                    public void main(String[] args) {
//
//                        for (int i = 0; i <= 6; i++){
//
//                            switch (randomDegrees) {
//                                case 0:
//                                    degrees = "420";
//                                    break;
//                                case 1:
//                                    degrees = "480";
//                                    break;
//                                case 2:
//                                    degrees = "540";
//                                    break;
//                                case 3:
//                                    degrees = "600";
//                                    break;
//                                case 4:
//                                    degrees = "660";
//                                    break;
//                                case 5:
//                                    degrees = "720";
//                                    break;
//                                default:
//                                    degrees = "0";
//                            }
//                        }
//                        float n = Float.parseFloat(degrees);
//                        System.out.print(n);


                final RotateAnimation rotate1 = new RotateAnimation(0, n, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        //RotateAnimation rotate = new RotateAnimation(0, 360, 90, 90);
                rotate1.setDuration(1000);
                rotate1.setInterpolator(new LinearInterpolator());

                final RotateAnimation rotate2 = new RotateAnimation(0, 420, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate2.setDuration(3000);
                rotate2.setInterpolator(new DecelerateInterpolator());


                setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        assert relativeLayoutCylinder != null;
                        relativeLayoutCylinder.startAnimation(rotate1);
                        relativeLayoutCylinder.startAnimation(rotate2);
                        relativeLayoutCylinder.setPivotX(relativeLayoutCylinder.getWidth() / 2);
                        relativeLayoutCylinder.setPivotY(relativeLayoutCylinder.getHeight() / 2);

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {



                    }

                    @Override
                    public void onAnimationRepeat(Animation rotate) {

                    }
                });



//                        assert relativeLayoutCylinder != null;
//                        relativeLayoutCylinder.startAnimation(rotate1);
//                        relativeLayoutCylinder.startAnimation(rotate2);
//                        relativeLayoutCylinder.setPivotX(relativeLayoutCylinder.getWidth() / 2);
//                        relativeLayoutCylinder.setPivotY(relativeLayoutCylinder.getHeight() / 2);
//                        relativeLayoutCylinder.setSaveEnabled(true);
                    }



                });


                //chambe
//                chamber2.startAnimation(rotate);
//                chamber3.startAnimation(rotate);
//                chamber4.startAnimation(rotate);
//                chamber5.startAnimation(rotate);
//                chamber6.startAnimation(rotate);



        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                   if (isFull && (isLoaded=true)){
                       Toast.makeText(MainActivity.this, "BAM! Take A Shot!", Toast.LENGTH_SHORT).show();
                   }
                if (isFull && (isLoaded=false)){

                }

                if (isFull=false){

                }


                
            }
        });
    }

    private void setAnimationListener(Animation.AnimationListener animationListener) {
    }

    public float randomSpinDegrees(){

        int mDegree = rand.nextInt(7-1) + 1;

        if(mDegree == 1) {
            n = 360;
        }else if(mDegree == 2){
            n = 420;

        }else if(mDegree == 3)
        {
            n = 480;
        }else if(mDegree == 4)
        {
            n = 540;
        }else if(mDegree==5)

        {
            n = 600;
        }else if(mDegree==6)

        {
            n = 660;
        }

        return n;

    }
}



//        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
//        chamber1.startAnimation(animRotate);
//        chamber1.setPivotX(0);
//        chamber1.setPivotY(0);
//
//        animRotate.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                chamber1.getPivotX();
//                chamber1.getPivotY();
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation rotate) {
//
//            }
//        });
//
//    }
//}