package com.example.shenhaichen.bakingapp;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.shenhaichen.bakingapp.bean.Steps;
import com.example.shenhaichen.bakingapp.db.DataBaseConstract;
import com.example.shenhaichen.bakingapp.db.TaskContract;
import com.example.shenhaichen.bakingapp.fragment.ButtonsFragment;
import com.example.shenhaichen.bakingapp.fragment.DescriptionFragment;
import com.example.shenhaichen.bakingapp.fragment.MediaPlayerFragment;

public class StepsDetailActivity extends AppCompatActivity {

    private String stepsId = null;
    public static final String TAG = StepsDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stepsId = getIntent().getStringExtra(TaskContract.STEPS_ID);

        Log.d(TAG, "Steps id" + stepsId);

        Steps steps = getStepsValues(stepsId);

        if (savedInstanceState == null) {

            MediaPlayerFragment playerFragment = new MediaPlayerFragment();
            DescriptionFragment descriptionFragment = new DescriptionFragment();
            ButtonsFragment buttonsFragment = new ButtonsFragment();

            // 设置uri 和 description 到控件中
            String videoURL = steps.getVideoURL();
            String thumbURL = steps.getThumbnailURL();
            String description = steps.getDescription();
            Uri mediaUri = null;

            if (!videoURL.isEmpty()) {
                mediaUri = Uri.parse(videoURL);
            } else {
                mediaUri = Uri.parse(thumbURL);
            }
           String ffffff = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
            //设置到相应的fragment中去
            playerFragment.setUri(ffffff);
//            descriptionFragment.setTextForView(description);

            // 使用fragmentManager 和 Transaction 添加相应的fragment到容器（FrameLayout）中
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.player_container, playerFragment)
                    .commit();

            fragmentManager.beginTransaction()
                    .add(R.id.description_container, descriptionFragment)
                    .commit();

            fragmentManager.beginTransaction()
                    .add(R.id.button_container, buttonsFragment)
                    .commit();

        }
    }

    /**
     * 准备一个steps对象，准备通过intent传送
     */
    public Steps getStepsValues(String id) {

        Steps steps = new Steps();
        Cursor steps_cursor = this.getContentResolver().query(TaskContract.TaskEntry.STEPS_CONTENT_URI,
                null, "steps_id = ?",
                new String[]{id}, null);
        if (steps_cursor.moveToFirst()) {
            steps.setDescription(steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_DESCRIPTION)));
            steps.setVideoURL(steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_VIDEOURL)));
            steps.setThumbnailURL(steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_THUMBNAILURL)));
        }
        return steps;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
