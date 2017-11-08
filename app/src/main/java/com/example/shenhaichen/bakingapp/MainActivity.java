package com.example.shenhaichen.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.shenhaichen.bakingapp.adapter.MainGridViewAdapter;
import com.example.shenhaichen.bakingapp.data.ImageData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.baking_main_grid)
    GridView gridView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainGridViewAdapter adapter = new MainGridViewAdapter(this, ImageData.getMain_pics());
        gridView.setAdapter(adapter);



    }
}
