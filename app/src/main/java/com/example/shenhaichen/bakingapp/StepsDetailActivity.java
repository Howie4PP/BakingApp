package com.example.shenhaichen.bakingapp;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shenhaichen.bakingapp.bean.Steps;
import com.example.shenhaichen.bakingapp.db.DataBaseConstract;
import com.example.shenhaichen.bakingapp.db.TaskContract;
import com.example.shenhaichen.bakingapp.fragment.ButtonsFragment;
import com.example.shenhaichen.bakingapp.fragment.DescriptionFragment;
import com.example.shenhaichen.bakingapp.fragment.MediaPlayerFragment;

public class StepsDetailActivity extends AppCompatActivity implements ButtonsFragment.OnButtonClickListener {

    private int stepsId = 0;
    private int last_stepsId = 0;
    private int first_stepsId = 0;
    public static final String TAG = StepsDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stepsId = getIntent().getIntExtra(TaskContract.STEPS_ID, 0);
        last_stepsId = getIntent().getIntExtra(TaskContract.LAST_STEPS_ID, 0);
        first_stepsId = getIntent().getIntExtra(TaskContract.FIRST_STEPS_ID, 0);

        Log.d(TAG, "Steps id" + stepsId);

        Steps steps = getStepsValues();

        if (savedInstanceState == null) {

            MediaPlayerFragment playerFragment = new MediaPlayerFragment();
            DescriptionFragment descriptionFragment = new DescriptionFragment();
            ButtonsFragment buttonsFragment = new ButtonsFragment();

            // 设置uri 和 description 到控件中
            String videoURL = steps.getVideoURL();
            String thumbURL = steps.getThumbnailURL();
            String description = steps.getDescription();
            String mediaURL = null;

            if (!videoURL.isEmpty()) {
                mediaURL = videoURL;
            } else {
                mediaURL = thumbURL;
            }
            //设置到相应的fragment中去
            playerFragment.setUri(mediaURL);
            descriptionFragment.setTextForView(description);
            buttonsFragment.setOnButtonClickListner(this);

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
    public void buttonClick(View v) {
       if (!BakingDetailActivity.TWOPANE) {
           switch (v.getId()) {
               case R.id.previous_button:
                   stepsId -= 1;
                   break;
               case R.id.next_button:
                   stepsId += 1;
                   break;
           }

           Intent nextStepsIntent = new Intent(StepsDetailActivity.this, StepsDetailActivity.class);
           nextStepsIntent.putExtra(TaskContract.STEPS_ID, stepsId);
           nextStepsIntent.putExtra(TaskContract.LAST_STEPS_ID, last_stepsId);
           nextStepsIntent.putExtra(TaskContract.FIRST_STEPS_ID, first_stepsId);
           finish();
           startActivity(nextStepsIntent);
       }
    }
}
