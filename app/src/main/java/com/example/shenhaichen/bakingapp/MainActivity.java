package com.example.shenhaichen.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.shenhaichen.bakingapp.adapter.MainGridViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.baking_main_grid)
    GridView gridView;


    Integer[] pic_array = new Integer[]{
            R.drawable.cake,
            R.drawable.brownies,
            R.drawable.cheesecake,
            R.drawable.pie};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainGridViewAdapter adapter = new MainGridViewAdapter(this,pic_array);
        gridView.setAdapter(adapter);



    }
}
