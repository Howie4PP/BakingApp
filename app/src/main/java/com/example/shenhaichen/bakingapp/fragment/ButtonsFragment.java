package com.example.shenhaichen.bakingapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shenhaichen.bakingapp.R;

/**
 * Created by shenhaichen on 10/11/2017.
 */

public class ButtonsFragment extends Fragment implements View.OnClickListener {

    public Button btn_previous;

    public Button btn_next;
    private OnButtonClickListener onButtonClickListner;
//    private String steps_id = null;
//    public static final String STEPS_ID = "steps_id";

    public ButtonsFragment() {
    }

    public void setOnButtonClickListner(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListner = onButtonClickListener;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        if(savedInstanceState != null) {
//            steps_id = savedInstanceState.getString(STEPS_ID);
//        }

        View rootView = inflater.inflate(R.layout.button_layout, container, false);
        btn_next = rootView.findViewById(R.id.next_button);
        btn_previous = rootView.findViewById(R.id.previous_button);

        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
          if (onButtonClickListner != null){
              onButtonClickListner.buttonClick(v);
          }
    }


//    @Override
//    public void onSaveInstanceState(Bundle currentState) {
//        currentState.putString(STEPS_ID, steps_id);
//    }

    public interface OnButtonClickListener {
        void buttonClick(View v);
    }

}
