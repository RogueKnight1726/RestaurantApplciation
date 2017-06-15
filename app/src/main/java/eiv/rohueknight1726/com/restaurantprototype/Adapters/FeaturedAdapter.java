package eiv.rohueknight1726.com.restaurantprototype.Adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;
import eiv.rohueknight1726.com.restaurantprototype.Models.Featured;
import eiv.rohueknight1726.com.restaurantprototype.R;
import eiv.rohueknight1726.com.restaurantprototype.menuActivity;
import eiv.rohueknight1726.com.restaurantprototype.menuExpansion;

import static eiv.rohueknight1726.com.restaurantprototype.R.id.pickerImage;

/**
 * Created by swathysudarsanan on 03/06/17.
 */

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.MyViewHolder>{
    private List<Featured> featuredList;
    private Context context;

    public FeaturedAdapter(List<Featured> newsList,Context context) {
        this.featuredList = newsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView featuredName,featuredItemDescription;
        public ImageView featuredImg,negativeButtonCell;
        public TextView featuredItemCount;
        public CardView addButtonCard;
        //        public NetworkImageView Thumbnail;
        public MyViewHolder(View view) {
            super(view);

            negativeButtonCell = (ImageView)view.findViewById(R.id.negativeButtonCell);
            featuredItemCount = (TextView)view.findViewById(R.id.featuredItemCount);
            featuredName = (TextView)view.findViewById(R.id.featuredName);
            featuredImg = (ImageView)view.findViewById(R.id.featuredImg);
            addButtonCard = (CardView)view.findViewById(R.id.addButtonCard);
            featuredItemDescription = (TextView)view.findViewById(R.id.featuredItemDescription);
            addButtonCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Clicked","Hello");
                    featuredItemCount.setVisibility(View.VISIBLE);
                    negativeButtonCell.setVisibility(View.VISIBLE);

                    ((menuActivity)context).showProceed();
                }
            });


        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_featured, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FeaturedAdapter.MyViewHolder holder, int position) {

        holder.featuredImg.setImageBitmap(Commons.decodeSampledBitmapFromResource(context.getResources(),R.drawable.cheesecake,100,100));
        Featured featuredItem = featuredList.get(position);
        holder.featuredName.setText(featuredItem.getName());
        holder.featuredItemDescription.setText(featuredItem.getDescription());
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        storage.getReference().child(featuredItem.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Picasso.with(menuExpansion.this).load(uri).fit().centerCrop().into(pickerImage);
//                Picasso.with(context).load(uri).fit().centerCrop().into(holder.featuredImg);
                Glide.with(context)
                        .load(uri)
                        .into(holder.featuredImg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        StorageReference pathReference = storageRef.child(featuredItem.getId());


    }

    @Override
    public int getItemCount() {
        return featuredList.size();
    }
}
