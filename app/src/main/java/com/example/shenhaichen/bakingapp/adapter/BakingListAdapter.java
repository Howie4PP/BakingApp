package com.example.shenhaichen.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenhaichen.bakingapp.R;
import com.example.shenhaichen.bakingapp.data.ImageData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shenhaichen on 08/11/2017.
 */

public class BakingListAdapter extends RecyclerView.Adapter<BakingListAdapter.ViewHolder> {

    private int imageNum = 0;
    private List<Integer> image_list = ImageData.getMain_pics();

    public BakingListAdapter(int imageNum) {
      this.imageNum = imageNum;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baking_list_detail,parent,false);

        ButterKnife.bind(view);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

         if (position == 0) {
             holder.imageView.setImageResource(image_list.get(imageNum));
             holder.title.setText("Ingredient");
             holder.ingredient.setText("No. ");
             holder.numOrDescription.setText("Quantity:");
             holder.measure.setText("Measure");

         } else {

             holder.imageView.setVisibility(View.GONE);
             holder.title.setVisibility(View.GONE);
             holder.ingredient.setText("Step "+position);
             holder.numOrDescription.setText("Description:");
             holder.measure.setVisibility(View.GONE);

         }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.list_image)
        ImageView imageView;
        @BindView(R.id.list_title)
        TextView title;
        @BindView(R.id.list_num_description)
        TextView numOrDescription;
        @BindView(R.id.list_measure)
        TextView measure;
        @BindView(R.id.list_ingredient)
        TextView ingredient;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
