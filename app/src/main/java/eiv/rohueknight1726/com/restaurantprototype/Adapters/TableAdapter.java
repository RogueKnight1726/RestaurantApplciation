package eiv.rohueknight1726.com.restaurantprototype.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eiv.rohueknight1726.com.restaurantprototype.Helpers.Commons;
import eiv.rohueknight1726.com.restaurantprototype.Models.Featured;
import eiv.rohueknight1726.com.restaurantprototype.Models.Table;
import eiv.rohueknight1726.com.restaurantprototype.R;

/**
 * Created by swathysudarsanan on 04/06/17.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder>{
    private List<Table> featuredList;
    private Context context;
    public TableAdapter(List<Table> newsList,Context context) {
        this.featuredList = newsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tableNumber;
        //        public NetworkImageView Thumbnail;
        public MyViewHolder(View view) {
            super(view);
            tableNumber = (TextView)view.findViewById(R.id.tableNumberText);



        }
    }

    @Override
    public TableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_table_list, parent, false);


        return new TableAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TableAdapter.MyViewHolder holder, int position) {

        holder.tableNumber.setText(""+(position+1));
        Table featuredItem = featuredList.get(position);

    }

    @Override
    public int getItemCount() {
        return featuredList.size();
    }
}
