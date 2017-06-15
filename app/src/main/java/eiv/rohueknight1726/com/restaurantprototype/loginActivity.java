package eiv.rohueknight1726.com.restaurantprototype;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;

public class loginActivity extends AppCompatActivity {
    ImageView backgroundImage,closeButton;
    FrameLayout backgroundFrame,bottomFrame,grayBackgroundCover;
    LinearLayout loginLinearLayout;
    EditText emailLogin,passwordText;
    CardView loginCard;
    TextView signUpText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(loginActivity.this, landingPage.class);
            startActivity(intent);
        }

        backgroundImage = (ImageView)findViewById(R.id.backgroundImage);
        backgroundImage.setImageBitmap(Commons.decodeSampledBitmapFromResource(getResources(),R.drawable.pancakes,500,500));
        backgroundFrame = (FrameLayout)findViewById(R.id.backgroundFrame);
        bottomFrame = (FrameLayout)findViewById(R.id.bottomFrame);
        loginLinearLayout = (LinearLayout)findViewById(R.id.loginLinearLayout);
        emailLogin = (EditText) findViewById(R.id.emailLogin);
        passwordText = (EditText) findViewById(R.id.passwordLogin);
        closeButton = (ImageView)findViewById(R.id.closeButton);
        closeButton.setVisibility(View.GONE);
        grayBackgroundCover = (FrameLayout)findViewById(R.id.grayBackgroundCover);
        loginCard = (CardView)findViewById(R.id.loginCard);
        signUpText = (TextView)findViewById(R.id.signUpText);


    }

    public void AnimateLayout(View v){
        backgroundFrame.animate().translationY(-600).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        bottomFrame.animate().scaleY(600).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                grayBackgroundCover.animate().setDuration(200).alpha(1.0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                loginLinearLayout.setVisibility(View.VISIBLE);
                emailLogin.setFocusable(true);
                emailLogin.requestFocus();
                closeButton.setVisibility(View.VISIBLE);
                signUpText.setVisibility(View.VISIBLE);
                Reveal();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }
    public void AnimateRevers(View v){
        ReverseReveal();
        backgroundFrame.animate().translationYBy(600).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        bottomFrame.animate().scaleYBy(-600).setDuration(200).setInterpolator(new AccelerateDecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                grayBackgroundCover.animate().setDuration(200).alpha(0).start();
                loginLinearLayout.setVisibility(View.GONE);
                closeButton.setVisibility(View.GONE);
                signUpText.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    public void ReverseReveal(){
        int cx = loginCard.getWidth()/2;
        int cy =loginCard.getHeight()/2;
        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(loginCard, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                loginCard.setVisibility(View.INVISIBLE);
            }
        });
        anim.start();
    }
    public void Reveal(){
        // get the center for the clipping circle
        int cx = loginCard.getWidth()/2;
        int cy = loginCard.getHeight()/2;

// get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(loginCard, cx, cy, 0, finalRadius);

// make the view visible and start the animation
        loginCard.setVisibility(View.VISIBLE);
        anim.setDuration(200);
        anim.start();
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation){
            }
        });
    }
    public void loginToUser(View v){


        String username = emailLogin.getText().toString();
        String password = passwordText.getText().toString();
        Log.e("Username",""+username);
        Log.e("Password",""+password);

        if(username==""||password==""){
            Log.e("Username/Password"," Are Empty");
        }
        else {
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.e("Success", "" + task.isSuccessful());
                    if (task.isSuccessful()) {
                        Log.e("Email", auth.getCurrentUser().getEmail());
                        Intent intent = new Intent(loginActivity.this, landingPage.class);
                        startActivity(intent);
                    }

                }
            });
        }
    }
}
