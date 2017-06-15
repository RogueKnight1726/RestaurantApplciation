package eiv.rohueknight1726.com.restaurantprototype;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import eiv.rohueknight1726.com.restaurantprototype.Adapters.FeaturedAdapter;
import eiv.rohueknight1726.com.restaurantprototype.Adapters.OrderedListAdapter;
import eiv.rohueknight1726.com.restaurantprototype.Adapters.PendingAdapter;
import eiv.rohueknight1726.com.restaurantprototype.Adapters.TableAdapter;
import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;
import eiv.rohueknight1726.com.restaurantprototype.Helpers.SimpleGestureFilter;
import eiv.rohueknight1726.com.restaurantprototype.Models.Featured;
import eiv.rohueknight1726.com.restaurantprototype.Models.Table;

import static eiv.rohueknight1726.com.restaurantprototype.R.id.home;
import static eiv.rohueknight1726.com.restaurantprototype.R.id.homeAsUp;
import static eiv.rohueknight1726.com.restaurantprototype.R.id.homeOrderContainerFrame;

public class homeActivity extends AppCompatActivity{

    Boolean resumeFlag = false;
    private SimpleGestureFilter detector;
    FloatingActionButton floatingButton;
    FrameLayout smokeScreen;
    int menuFlag = 0;
    CardView billCard;
    int tableFlag = 0;
    ImageView checkButton;
    CardView confirmCard;
    FrameLayout iconContainer,frameContainer,TableListContainerFrame,homeOrderContainerFrame,BillPayButtonFrame;
    ImageView buttonImage;
    TextView buttonText;
    LinearLayout currentOrderContainer,pendingOrderContainer;
    RecyclerView tableRecycler,tableOrderListRecycler,pendingListRecycler;
    private TableAdapter tAdapter;

    ImageView curentListImage;

    OrderedListAdapter fAdapter;
    PendingAdapter pAdapter;
    List<Table> list= new ArrayList<>();
    List<Featured>orderList = new ArrayList<>();
    List<Featured>pendingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkButton = (ImageView)findViewById(R.id.checkButton);
        smokeScreen = (FrameLayout)findViewById(R.id.smokeScreen);
        floatingButton = (FloatingActionButton)findViewById(R.id.floatingButton);
        iconContainer = (FrameLayout)findViewById(R.id.iconContainer);
        buttonImage = (ImageView)findViewById(R.id.buttonImage);
        buttonText = (TextView)findViewById(R.id.buttonText);
        frameContainer = (FrameLayout)findViewById(R.id.frameContainer);
        TableListContainerFrame = (FrameLayout)findViewById(R.id.TableListContainerFrame);
        homeOrderContainerFrame = (FrameLayout)findViewById(R.id.homeOrderContainerFrame);
        confirmCard = (CardView)findViewById(R.id.confirmCard);
        currentOrderContainer = (LinearLayout)findViewById(R.id.currentOrderContainer);
        pendingOrderContainer = (LinearLayout)findViewById(R.id.pendingOrderContainer);
        BillPayButtonFrame = (FrameLayout)findViewById(R.id.BillPayButtonFrame);
        billCard = (CardView)findViewById(R.id.billCard);
//        curentListImage = (ImageView)findViewById(R.id.curentListImage);
//        curentListImage.setImageBitmap(Commons.decodeSampledBitmapFromResource(getResources(),R.drawable.cheesecake,500,500));
        initiateTableRecyclerView();
        initiateOrderListRecycler();
        initializePendingOrderRecycler();
    }

    private  void initializePendingOrderRecycler(){
        pendingListRecycler = (RecyclerView)findViewById(R.id.pendingListRecycler);
        pAdapter = new PendingAdapter(orderList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        pendingListRecycler.setLayoutManager(mLayoutManager);
        pendingListRecycler.setItemAnimator(new DefaultItemAnimator());
        pendingListRecycler.setAdapter(pAdapter);

        DatabaseReference databasereference;
        databasereference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference categoryReference = databasereference.child("Appetizer");

        categoryReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    Userlist.add(String.valueOf(dsp.geValue())); //add result into array list


                    String name = dsp.child("Name").getValue(String.class);
                    String id = dsp.child("Id").getValue(String.class);
                    String descirption = dsp.child("Description").getValue(String.class);
                    String preparationTime  = dsp.child("PreparationTime").getValue(String.class);
                    String price = dsp.child("Price").getValue(String.class);
                    Featured featured = new Featured(id,name,descirption,price,preparationTime);
                    pendingList.add(featured);




                }

                fAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        for (int i = 0;i<10;i++){
//            Featured featured = new Featured();
////            featured.setText("New item "+i);
//            pendingList.add(featured);
//        }
//        fAdapter.notifyDataSetChanged();
    }

    public void initiateOrderListRecycler(){
        tableOrderListRecycler = (RecyclerView)findViewById(R.id.tableOrderListRecycler);
        fAdapter = new OrderedListAdapter(orderList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        tableOrderListRecycler.setLayoutManager(mLayoutManager);
        tableOrderListRecycler.setItemAnimator(new DefaultItemAnimator());
        tableOrderListRecycler.setAdapter(fAdapter);


        DatabaseReference databasereference;
        databasereference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference categoryReference = databasereference.child("Appetizer");

        categoryReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    Userlist.add(String.valueOf(dsp.geValue())); //add result into array list


                    String name = dsp.child("Name").getValue(String.class);
                    String id = dsp.child("Id").getValue(String.class);
                    String descirption = dsp.child("Description").getValue(String.class);
                    String preparationTime  = dsp.child("PreparationTime").getValue(String.class);
                    String price = dsp.child("Price").getValue(String.class);
                    Featured featured = new Featured(id,name,descirption,price,preparationTime);
                    orderList.add(featured);




                }

                fAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tableOrderListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("State",""+newState);
            }
        });
//        for (int i = 0;i<10;i++){
//            Featured featured = new Featured();
////            featured.setText("New item "+i);
//            orderList.add(featured);
//        }
        fAdapter.notifyDataSetChanged();

        tableOrderListRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), tableRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent newIntent = new Intent(homeActivity.this,itemDescriptionActivity.class);
                startActivity(newIntent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void initiateTableRecyclerView(){
        tableRecycler = (RecyclerView)findViewById(R.id.tableViewRecycler);
        tAdapter = new TableAdapter(list,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tableRecycler.setLayoutManager(mLayoutManager);
        tableRecycler.setItemAnimator(new DefaultItemAnimator());
        tableRecycler.setAdapter(tAdapter);

        for (int i = 0;i<10;i++){
            Table table = new Table();
            table.setTableNumber("New item "+i);
            list.add(table);
        }
        tAdapter.notifyDataSetChanged();

        tableRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), tableRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerToggle(view);
                revealOrderList();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void showMenuCategory(View v){

        rotateFab();
        final View myView = findViewById(R.id.frameContainer);
        int marginWidth = Commons.convertDpToPixels(16,this);
        int cx = myView.getWidth()-(marginWidth+(floatingButton.getWidth()/2));
        int cy = myView.getHeight()-(marginWidth+(floatingButton.getWidth()/2));
        float finalRadius = (float) Math.hypot(cx, cy);
        if(menuFlag==0){
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            myView.setVisibility(View.VISIBLE);
            anim.setDuration(300);
            anim.start();
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    myView.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation){

                }
            });
            menuFlag=1;
        }
        else{
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx, cy, finalRadius, 0);
            myView.setVisibility(View.VISIBLE);
            anim.setDuration(300);
            anim.start();
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);

                }

                @Override
                public void onAnimationEnd(Animator animation){
                    myView.setVisibility(View.INVISIBLE);
                }
            });
            menuFlag=0;
        }

    }

    private void rotateFab(){
        final OvershootInterpolator interpolator = new OvershootInterpolator();
        Log.e("Overshoot","Yeay");
        if (menuFlag==0) {
            ViewCompat.animate(floatingButton).
                    rotation(135f).
                    withLayer().
                    setDuration(300).
                    setInterpolator(interpolator).
                    start();
        }
        else{
            ViewCompat.animate(floatingButton).
                    rotation(0f).
                    withLayer().
                    setDuration(300).
                    setInterpolator(interpolator).
                    start();
        }
    }

    public void showMenu(View v){
        Intent intent = new Intent(this, menuActivity.class);
        intent.putExtra("Category","Appetizer");
        Log.e("iconContainer",""+iconContainer);
        Pair<View, String> pair1 = Pair.create((View)iconContainer,getString(R.string.iconContainer));
        Pair<View, String> pair2 = Pair.create((View)buttonImage,getString(R.string.iconImage));
        Pair<View, String> pair3 = Pair.create((View)buttonText,getString(R.string.iconDescription));
        Pair<View, String> pair4 = Pair.create((View)frameContainer,getString(R.string.frameContainer));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this,  pair2, pair3,pair4);
        startActivity(intent, options.toBundle());
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private homeActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final homeActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public void drawerToggle(View v){
        final int offset = Commons.convertDpToPixels(60,this);
        if (tableFlag == 0){
            TableListContainerFrame.animate().setDuration(200).translationXBy(offset).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
            tableFlag = 1;
        }
        else{
            TableListContainerFrame.animate().setDuration(200).translationXBy(-offset).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
            tableFlag = 0;
        }
    }

    private void revealOrderList(){
    }

    @Override
    public void onResume(){
        super.onResume();
        if(!resumeFlag){
            checkButton.setVisibility(View.INVISIBLE);
            resumeFlag = true;
        }
        else{
            homeOrderContainerFrame.setVisibility(View.VISIBLE);
            checkButton.setVisibility(View.VISIBLE);
        }
    }

    public void ConfirmOrder(View v){
        floatingButton.hide();
        smokeScreen.setVisibility(View.VISIBLE);
        int offset = Commons.convertDpToPixels(500,this);
        confirmCard.animate().translationYBy(-offset).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();

    }
    public void sendOrderToFirebase(View v){
        floatingButton.show();
        smokeScreen.setVisibility(View.INVISIBLE);
        int offset = Commons.convertDpToPixels(500,this);
        currentOrderContainer.setVisibility(View.GONE);
        confirmCard.animate().translationYBy(offset).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();

        int payOffset = Commons.convertDpToPixels(60,this);
        BillPayButtonFrame.animate().translationYBy(-payOffset).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();

    }
    public void getBillPayDialog(View v){
        smokeScreen.setVisibility(View.VISIBLE);
        floatingButton.hide();
        int x = billCard.getWidth()/2;
        int y = billCard.getHeight()/2;
        Log.e("X,Y",""+x+"    "+y);

        float finalRadius = (float) Math.hypot(x, y);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(billCard, x, y, 0, finalRadius);
        anim.setDuration(300);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                billCard.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation){

            }
        });
        anim.start();

    }

    public void EmailNow(View v){
        int x = billCard.getWidth()/2;
        int y = billCard.getHeight()/2;

        float finalRadius = (float) Math.hypot(x, y);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(billCard, x, y, finalRadius, 0);
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
                billCard.setVisibility(View.INVISIBLE);
                smokeScreen.setVisibility(View.INVISIBLE);
                floatingButton.show();
                Intent i = new Intent(homeActivity.this, landingPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }

    public void CurrentOrdersList(View v){
        Intent intent = new Intent(this, pendingMenuActivity.class);
        Log.e("iconContainer",""+iconContainer);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.mToolbar);
        FrameLayout currentOrderFrame = (FrameLayout)findViewById(R.id.currentOrderFrame);
        TextView HelloText = (TextView)findViewById(R.id.HelloText);
        Pair<View, String> pair1 = Pair.create((View)currentOrderFrame,getString(R.string.currentOrderFrameTransition));
        Pair<View, String> pair2 = Pair.create((View)HelloText,getString(R.string.HelloTextTransition));
        Pair<View, String> pair3 = Pair.create((View)mToolbar,getString(R.string.mToolbarTransition));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this,pair2,pair3);
        startActivity(intent, options.toBundle());
    }
}
