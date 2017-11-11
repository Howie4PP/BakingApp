package com.example.shenhaichen.bakingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shenhaichen.bakingapp.bean.Steps;
import com.example.shenhaichen.bakingapp.db.DataBaseConstract;
import com.example.shenhaichen.bakingapp.db.TaskContract;
import com.example.shenhaichen.bakingapp.fragment.BakingListFragment;
import com.example.shenhaichen.bakingapp.fragment.DescriptionFragment;
import com.example.shenhaichen.bakingapp.fragment.MediaPlayerFragment;

public class BakingDetailActivity extends AppCompatActivity implements BakingListFragment.OnIDSelectedListener {

    public static final String TAG = BakingDetailActivity.class.getSimpleName();

    public static boolean TWOPANE = false;

    private int stepsId = 0;
    private int last_stepsId = 0;
    private int first_stepsId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_detail);

        // 决定是两个画面，还是一个画面在一个屏幕上
        if (findViewById(R.id.linear_activity_steps_detail) != null) {
            TWOPANE = true;

            if (savedInstanceState == null) {
                // 在双画面模式下，需要初始化fragment并加入到屏幕中去
                FragmentManager fragmentManager = getSupportFragmentManager();

                MediaPlayerFragment playerFragment = new MediaPlayerFragment();
                DescriptionFragment descriptionFragment = new DescriptionFragment();

                String description = "Please select a step to show video and description.";
                String mediaURL = null;

                //设置到相应的fragment中去
                playerFragment.setUri(mediaURL);
                descriptionFragment.setTextForView(description);


                fragmentManager.beginTransaction()
                        .add(R.id.player_container, playerFragment)
                        .commit();

                fragmentManager.beginTransaction()
                        .add(R.id.description_container, descriptionFragment)
                        .commit();

            }
        } else {
            TWOPANE = false;
        }

        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    @Override
    public void onIdSelected(int id, int firstId, int lastId) {
        stepsId = id;
        first_stepsId = firstId;
        last_stepsId = lastId;

        if (TWOPANE) {
            Steps steps = getStepsValues();

            String videoURL = steps.getVideoURL();
            String thumbURL = steps.getThumbnailURL();
            String description = steps.getDescription();
            String mediaURL = null;

            if (!videoURL.isEmpty()) {
                mediaURL = videoURL;
            } else {
                mediaURL = thumbURL;
            }

            MediaPlayerFragment newPlayerFragment = new MediaPlayerFragment();
            DescriptionFragment newDescriptionFragment = new DescriptionFragment();

            newPlayerFragment.setUri(mediaURL);
            newDescriptionFragment.setTextForView(description);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.player_container, newPlayerFragment)
                    .commit();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.description_container, newDescriptionFragment)
                    .commit();
        }


    }


    /**
     * 准备一个steps对象，准备通过intent传送
     */
    public Steps getStepsValues() {

        String current_id = null;

        //检查所需的ID是否在数据库之中，如果超出当前ID的边界，则使用边界ID
        if (stepsId > last_stepsId) {
            stepsId = last_stepsId;
            current_id = String.valueOf(last_stepsId);
            Toast.makeText(this, "No more step, this is last Steps", Toast.LENGTH_SHORT).show();
        } else if (stepsId < first_stepsId) {
            stepsId = first_stepsId;
            current_id = String.valueOf(first_stepsId);
            Toast.makeText(this, "No more step, this is first Steps", Toast.LENGTH_SHORT).show();
        } else {
            current_id = String.valueOf(stepsId);
        }

        Steps steps = new Steps();
        Cursor steps_cursor = this.getContentResolver().query(TaskContract.TaskEntry.STEPS_CONTENT_URI,
                null, "steps_id = ?",
                new String[]{current_id}, null);
        if (steps_cursor.moveToFirst()) {
            steps.setDescription(steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_DESCRIPTION)));
            steps.setVideoURL(steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_VIDEOURL)));
            steps.setThumbnailURL(steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_THUMBNAILURL)));
        }
        return steps;
    }

}
