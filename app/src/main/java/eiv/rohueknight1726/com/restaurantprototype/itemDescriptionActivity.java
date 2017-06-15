package eiv.rohueknight1726.com.restaurantprototype;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;

public class itemDescriptionActivity extends AppCompatActivity {

    int count = 1;
    ImageView itemEnlargeImg;
    TextView featuredItemCountMain;
    FrameLayout noteSmoke;
    CardView noteCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        itemEnlargeImg = (ImageView)findViewById(R.id.itemEnlargeImg);
        itemEnlargeImg.setImageBitmap(Commons.decodeSampledBitmapFromResource(getResources(),R.drawable.pop,200,200));
        featuredItemCountMain = (TextView)findViewById(R.id.featuredItemCountMain);
        noteSmoke = (FrameLayout)findViewById(R.id.noteSmoke);
        noteCard = (CardView)findViewById(R.id.noteCard);
    }
    public void remove(View v){

        if(count == 0){

        }
        else{
            count--;
            featuredItemCountMain.setText(""+count);
        }
    }
    public void Add(View v){
        count++;
        featuredItemCountMain.setText(""+count);
        FrameLayout changeConfirmFrame = (FrameLayout)findViewById(R.id.changeConfirmFrame);
        int offSet = Commons.convertDpToPixels(60,this);
        changeConfirmFrame.animate().translationY(-offSet).start();
    }

    public void addNote(View v){
        int x = noteCard.getWidth()/2;
        int y = noteCard.getHeight()/2;

        float finalRadius = (float) Math.hypot(x, y);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(noteCard, x, y, 0, finalRadius);
        noteCard.setVisibility(View.VISIBLE);
        anim.setDuration(300);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                noteCard.setVisibility(View.VISIBLE);
                noteSmoke.animate().alpha(1).setDuration(300).start();

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

    public void dismissNow(View v){
        int x = noteCard.getWidth()/2;
        int y = noteCard.getHeight()/2;

        float finalRadius = (float) Math.hypot(x, y);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(noteCard, x, y, finalRadius, 0);
        noteCard.setVisibility(View.VISIBLE);
        anim.setDuration(300);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {

                noteSmoke.animate().alpha(0).setDuration(300).start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation){

                noteCard.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void dismissFull(View v){
        onBackPressed();
    }
}
