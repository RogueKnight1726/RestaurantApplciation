package eiv.rohueknight1726.com.restaurantprototype;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;

public class firstActivity extends AppCompatActivity {
    Boolean resumeFlag = false;
    Boolean upFlag = false;
    FrameLayout confirmTableFrame,selectionMask;
    TextView TableAText;
    CardView translateCardForFAB;
    TextView selectYourTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        selectionMask = (FrameLayout)findViewById(R.id.selectionMask);
        TableAText = (TextView)findViewById(R.id.TableAText);
        translateCardForFAB = (CardView)findViewById(R.id.translateCardForFAB);
        selectYourTable = (TextView) findViewById(R.id.selectYourTable);
    }

    public void selectTable(View v){
        if(!upFlag){
            animateUp();
            upFlag = true;
        }
        int x = v.getWidth()/2;
        int y = v.getHeight()/2;

        float finalRadius = (float) Math.hypot(x, y);
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(selectionMask, x, y, 0, finalRadius);
        selectionMask.setVisibility(View.VISIBLE);
            anim.setDuration(300);
            anim.start();
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    selectionMask.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation){

                }
            });
    }

    private void animateUp(){
        confirmTableFrame = (FrameLayout)findViewById(R.id.confirmTableFrame);
        int offset = Commons.convertDpToPixels(60,this);
        confirmTableFrame.animate().setDuration(200).translationYBy(-offset).start();
    }

    public void confirmTable(View v){
        selectYourTable.setVisibility(View.INVISIBLE);
        int offset = Commons.convertDpToPixels(24,this);
        confirmTableFrame = (FrameLayout)findViewById(R.id.confirmTableFrame);
        int x = confirmTableFrame.getWidth()/2;
        int y = confirmTableFrame.getHeight()/2;

        float finalRadius = (float) Math.hypot(x, y);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(confirmTableFrame, x, y, finalRadius, offset);
        anim.setDuration(300);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation){
                confirmTableFrame.setVisibility(View.INVISIBLE);
                translateCardForFAB.setVisibility(View.VISIBLE);
                Intent intent = new Intent(firstActivity.this, homeActivity.class);
                Pair<View, String> pair1 = Pair.create((View)translateCardForFAB,getString(R.string.transitionFAB));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(firstActivity.this,pair1);
                startActivity(intent, options.toBundle());
            }
        });


    }
    @Override
    public void onResume(){
        super.onResume();

    }
}
