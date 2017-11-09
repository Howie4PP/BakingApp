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

/**
 * Created by shenhaichen on 08/11/2017.
 */

public class BakingListAdapter extends RecyclerView.Adapter<BakingListAdapter.ViewHolder> {

    private int imageNum = 0;
    private List<Integer> image_list = ImageData.getMain_pics();
    private List<String> mList;

    public BakingListAdapter(int imageNum, List<String> mList) {
      this.imageNum = imageNum;
      this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baking_list_detail,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

         if (position == 0) {
             holder.imageView.setBackgroundResource(image_list.get(imageNum));
             holder.title.setText("Ingredient");
             holder.ingredient.setText(mList.get(0));

         } else {
             holder.imageView.setVisibility(View.GONE);
             holder.title.setText("Step "+position);
             holder.ingredient.setText(mList.get(position));

         }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView ingredient;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_image);
            title = itemView.findViewById(R.id.list_title);
            ingredient = itemView.findViewById(R.id.list_ingredient);
        }
    }


}
