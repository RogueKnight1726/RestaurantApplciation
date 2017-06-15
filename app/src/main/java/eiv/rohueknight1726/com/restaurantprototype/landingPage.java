package eiv.rohueknight1726.com.restaurantprototype;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;


public class landingPage extends AppCompatActivity {
    private String[] tableNameArray = {"Table A","Tabel B","Table C"};
    private String[] Orders = {"Cheese cake with Blueberries, Almond coffee...","Strawberry Truffles with Pink ...","Vacant"};
    private String[] priceArray = {"$19.99","$12.99",""};
    int i = 0;
    FrameLayout animationContainer;
    ImageView leadingEdge;
    TextView carousselOrders,carousselPrice,carousselTableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        carousselOrders = (TextView)findViewById(R.id.carousselOrders);
        carousselPrice = (TextView)findViewById(R.id.carousselPrice);
        carousselTableName = (TextView)findViewById(R.id.carousselTableName);

        animationContainer = (FrameLayout)findViewById(R.id.animationContainer);

        carousselOrders.setText(""+Orders[i]);
        carousselPrice.setText(""+priceArray[i]);
        carousselTableName.setText(""+tableNameArray[i]);
        leadingEdge = (ImageView)findViewById(R.id.leadingEdge);
        callAnimation();
        callCaroussalAnimation();
    }

    private void callAnimation(){
        int offSet = Commons.convertDpToPixels(420,this);
        leadingEdge.animate().translationYBy(-offSet).setDuration(0).start();
    }

    private void callCaroussalAnimation(){
        if(i==2){
            i=-1;
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(animationContainer,"translationX",0,-120);
        animator.setDuration(120);
        animator.setStartDelay(1200);
        ObjectAnimator animatorTwo = ObjectAnimator.ofFloat(animationContainer,"Alpha",1,0);
        animatorTwo.setDuration(120);
        animatorTwo.setStartDelay(1200);
        AnimatorSet s = new AnimatorSet();
        s.playTogether(animator,animatorTwo);
        s.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                i++;
                callCaroussalRetraceAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        s.start();
    }
    private void callCaroussalRetraceAnimation(){
        carousselOrders.setText(Orders[i]);
        carousselPrice.setText(priceArray[i]);
        carousselTableName.setText(tableNameArray[i]);

        ObjectAnimator animator = ObjectAnimator.ofFloat(animationContainer,"translationX",100,0);
        animator.setDuration(120);
        ObjectAnimator animatorTwo = ObjectAnimator.ofFloat(animationContainer,"Alpha",0,1);
        animatorTwo.setDuration(120);
        AnimatorSet s = new AnimatorSet();
        s.playTogether(animator,animatorTwo);
        s.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                callCaroussalAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        s.start();
    }

    public void selectTable(View v){
        Intent in = new Intent(landingPage.this,firstActivity.class);
        startActivity(in);
    }
}
