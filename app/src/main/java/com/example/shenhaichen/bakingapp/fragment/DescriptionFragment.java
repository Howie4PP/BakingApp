package com.example.shenhaichen.bakingapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shenhaichen.bakingapp.R;

/**
 * Created by shenhaichen on 10/11/2017.
 */

public class DescriptionFragment extends Fragment {


    public TextView textView;

    public DescriptionFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.description_layout, container, false );
        textView = (TextView)rootView.findViewById(R.id.description_text_view);
         textView.setText("dfsdfsfadffffffffffffffffff");
        return rootView;
    }

    public void setTextForView(String description){

        textView.setText("fsdffffffffffffffffffffffff");

    }

}
