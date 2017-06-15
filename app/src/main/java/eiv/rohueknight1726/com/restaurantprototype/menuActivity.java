package eiv.rohueknight1726.com.restaurantprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import eiv.rohueknight1726.com.restaurantprototype.Adapters.FeaturedAdapter;
import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;
import eiv.rohueknight1726.com.restaurantprototype.Models.Featured;

public class menuActivity extends AppCompatActivity {

    Boolean proceedFlag = false;
    private int overallXScroll = 0;
    RecyclerView featuredRecycler;
    private FeaturedAdapter fAdapter;
    ImageView categoryBanner;
    FrameLayout proceedFrame;
    List<Featured> list= new ArrayList<>();
    DatabaseReference databasereference;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        category = getIntent().getExtras().getString("Category");

        proceedFrame = (FrameLayout)findViewById(R.id.proceedFrame);
        featuredRecycler = (RecyclerView)findViewById(R.id.featuredRecycler);
        categoryBanner = (ImageView)findViewById(R.id.categoryBanner);

//        categoryBanner.setImageBitmap(Commons.decodeSampledBitmapFromResource(getResources(),R.drawable.mojito,100,100));
        fAdapter = new FeaturedAdapter(list,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        featuredRecycler.setLayoutManager(mLayoutManager);
        featuredRecycler.setItemAnimator(new DefaultItemAnimator());
        featuredRecycler.setAdapter(fAdapter);
        databasereference= FirebaseDatabase.getInstance().getReference();
        DatabaseReference categoryReference = databasereference.child(category);

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
                    list.add(featured);




                }

                fAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        featuredRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                overallXScroll = overallXScroll + dy;
                Log.i("check","overall X  = " + overallXScroll);

            }
        });
    }
    public void goBack(View v){
        onBackPressed();
    }
    public void showProceed(){
        if(!proceedFlag){
            proceedFlag=true;
            int offset = Commons.convertDpToPixels(60,this);
            proceedFrame.animate().translationYBy(-offset).setDuration(200).start();

        }

    }
}
