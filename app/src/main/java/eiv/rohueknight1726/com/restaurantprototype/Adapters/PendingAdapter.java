package eiv.rohueknight1726.com.restaurantprototype.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;
import eiv.rohueknight1726.com.restaurantprototype.Models.Featured;
import eiv.rohueknight1726.com.restaurantprototype.R;

/**
 * Created by swathy on 08/06/17.
 */

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder>{
    private List<Featured> featuredList;
    private Context context;
    public PendingAdapter(List<Featured> newsList,Context context) {
        this.featuredList = newsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView itemNamePendingList,itemDescriptionPendingList;
        public ImageView featuredImg;
        //        public NetworkImageView Thumbnail;
        public MyViewHolder(View view) {
            super(view);
            itemNamePendingList = (TextView)view.findViewById(R.id.itemNamePendingList);
            itemDescriptionPendingList = (TextView)view.findViewById(R.id.itemDescriptionPendingList);


        }
    }

    @Override
    public PendingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_pending_list, parent, false);


        return new PendingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PendingAdapter.MyViewHolder holder, int position) {

        Featured featuredItem = featuredList.get(position);
        holder.itemNamePendingList.setText(featuredItem.getName());
        holder.itemDescriptionPendingList.setText(featuredItem.getDescription());



    }

    @Override
    public int getItemCount() {
        return featuredList.size();
    }
}
