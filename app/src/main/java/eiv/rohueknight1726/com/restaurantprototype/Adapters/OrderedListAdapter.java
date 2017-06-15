package eiv.rohueknight1726.com.restaurantprototype.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;
import eiv.rohueknight1726.com.restaurantprototype.Models.Featured;
import eiv.rohueknight1726.com.restaurantprototype.R;

/**
 * Created by swathy on 05/06/17.
 */

public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListAdapter.MyViewHolder>{
    private List<Featured> featuredList;
    private Context context;
    public OrderedListAdapter(List<Featured> newsList,Context context) {
        this.featuredList = newsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView featuredText,orderedListSubText,orderedItemPrice;
        public ImageView featuredImg;
        //        public NetworkImageView Thumbnail;
        public MyViewHolder(View view) {
            super(view);
            featuredText = (TextView)view.findViewById(R.id.orderedText);
            featuredImg = (ImageView)view.findViewById(R.id.orderedImg);
            orderedListSubText = (TextView)view.findViewById(R.id.orderedListSubText);
            orderedItemPrice = (TextView)view.findViewById(R.id.orderedItemPrice);



        }
    }

    @Override
    public OrderedListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_ordered_list, parent, false);


        return new OrderedListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderedListAdapter.MyViewHolder holder, int position) {

        holder.featuredImg.setImageBitmap(Commons.decodeSampledBitmapFromResource(context.getResources(),R.drawable.cheesecake,100,100));
        Featured featuredItem = featuredList.get(position);
        holder.featuredText.setText(featuredItem.getName());
        holder.orderedListSubText.setText(featuredItem.getDescription());
        holder.orderedItemPrice.setText("$"+featuredItem.getPrice());
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

    }

    @Override
    public int getItemCount() {
        return featuredList.size();
    }
}
