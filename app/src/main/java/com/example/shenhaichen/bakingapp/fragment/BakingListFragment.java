package com.example.shenhaichen.bakingapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shenhaichen.bakingapp.R;

/**
 * Created by shenhaichen on 08/11/2017.
 */

public class BakingListFragment extends Fragment{


    public BakingListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.baking_list_fragment, container,false );

        RecyclerView recyclerView = rootView.findViewById(R.id.baking_list_recyclerview);


        return rootView;
    }
}
