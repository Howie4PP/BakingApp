package com.example.shenhaichen.bakingapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.shenhaichen.bakingapp.R;

import java.util.List;

/**
 * Created by shenhaichen on 07/11/2017.
 */

public class MainGridViewAdapter extends ArrayAdapter<Integer> {

    public static final String TAG = MainGridViewAdapter.class.getSimpleName();

    public MainGridViewAdapter(Activity context, List<Integer> pics) {
        super(context, 0,pics);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_image,
                    parent,false);
        }
        ViewHolder holder = new ViewHolder();

        int imageResource = getItem(position);

        holder.imageView = convertView.findViewById(R.id.baking_main_image);
        holder.imageView.setBackgroundResource(imageResource);

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(getContext(), BakingDetailActivity.class);
//                intent.putExtra("image",position);
//                getContext().startActivity(intent);
//            }
//        });

        return convertView;
    }

    static class ViewHolder{

        ImageView imageView;

    }


}



