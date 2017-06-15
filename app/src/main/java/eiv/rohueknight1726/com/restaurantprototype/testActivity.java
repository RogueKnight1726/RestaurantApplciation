package eiv.rohueknight1726.com.restaurantprototype;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;

public class testActivity extends AppCompatActivity {

    ImageView bannerImageOne,bannerImageTwo,bannerImageThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_new);

        bannerImageOne = (ImageView)findViewById(R.id.bannerImageOne);
        bannerImageTwo = (ImageView)findViewById(R.id.bannerImageTwo);
        bannerImageThree = (ImageView)findViewById(R.id.bannerImageThree);

        bannerImageThree.setImageBitmap(Commons.decodeSampledBitmapFromResource(getResources(),R.drawable.cheesecake,700,200));
        bannerImageTwo.setImageBitmap(Commons.decodeSampledBitmapFromResource(getResources(),R.drawable.pancakes,700,200));
        bannerImageOne.setImageBitmap(Commons.decodeSampledBitmapFromResource(getResources(),R.drawable.mojito,700,200));
    }
}
