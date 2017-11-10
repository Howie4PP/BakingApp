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

    public ButtonsFragment() {
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.button_layout, container, false);
        btn_next = (Button) rootView.findViewById(R.id.next_button);
        btn_previous = (Button) rootView.findViewById(R.id.previous_button);

//        btn_next.setOnClickListener(this);
//        btn_previous.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

    private void nextOrPreviousMedia() {
//        mExoPlayer.stop();
//        Intent nextQuestionIntent = new Intent(QuizActivity.this, QuizActivity.class);
//        nextQuestionIntent.putExtra(REMAINING_SONGS_KEY, mRemainingSampleIDs);
//        finish();
//        startActivity(nextQuestionIntent);
    }
}
