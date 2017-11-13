package com.example.shenhaichen.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.shenhaichen.bakingapp.R;
import com.example.shenhaichen.bakingapp.data.TextData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetConfigurationActivity extends AppCompatActivity implements View.OnClickListener {

//    由于不知道有显示哪些信息，所以在小部件上只显示了需要多少材料和多少步骤
    List<String> summary_food = TextData.getSmall_summary_food();

    int mAppWidgetId = 0;
    @BindView(R.id.widget_small_baking)
    ImageView bakingView;

    @BindView(R.id.widget_small_brownies)
    ImageView browniesView;

    @BindView(R.id.widget_small_pie)
    ImageView pieView;

    @BindView(R.id.widget_small_cheesecake)
    ImageView cakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_configuration);

        ButterKnife.bind(this);

        pieView.setOnClickListener(this);
        cakeView.setOnClickListener(this);
        browniesView.setOnClickListener(this);
        bakingView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int imageId = 0;
        String content = null;
        switch (v.getId()){
            case R.id.widget_small_baking:
                imageId = R.drawable.small_baking;
                content = summary_food.get(3);
                break;
            case R.id.widget_small_brownies:
                imageId = R.drawable.small_brownies;
                content = summary_food.get(2);
                break;
            case R.id.widget_small_cheesecake:
                imageId = R.drawable.small_cheesecake;
                content = summary_food.get(4);
                break;
            case R.id.widget_small_pie:
                imageId = R.drawable.small_pie;
                content = summary_food.get(1);
                break;
        }
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(this.getPackageName(),
                R.layout.baking_widget_provider);
        views.setImageViewResource(R.id.widget_baking_image, imageId);
        views.setTextViewText(R.id.widget_baking_text, content);
        appWidgetManager.updateAppWidget(mAppWidgetId, views);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}
