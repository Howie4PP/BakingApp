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
    public static final String DESCRIPTION_INDEX = "des_index";
    private String description = null;

    public DescriptionFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            description = savedInstanceState.getString(DESCRIPTION_INDEX);
        }

        View rootView = inflater.inflate(R.layout.description_layout, container, false );
        textView = rootView.findViewById(R.id.description_text_view);
        textView.setText(description);
        return rootView;
    }

    public void setTextForView(String description){

        this.description = description;

    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString(DESCRIPTION_INDEX, description);
    }

}
