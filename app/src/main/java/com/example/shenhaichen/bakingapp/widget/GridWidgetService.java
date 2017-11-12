package com.example.shenhaichen.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.shenhaichen.bakingapp.R;
import com.example.shenhaichen.bakingapp.data.ImageData;
import com.example.shenhaichen.bakingapp.db.SPUtils;
import com.example.shenhaichen.bakingapp.db.TaskContract;

import java.util.List;

/**
 * Created by shenhaichen on 12/11/2017.
 */
public class GridWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}


class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {


    List<Integer> my_list = ImageData.getSmall_widget_pics_pics();

    Context context;

    public GridRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return my_list.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        int imageResource = my_list.get(position);

        views.setImageViewResource(R.id.widget_baking_image, imageResource);

        Intent fillInIntent = new Intent();
        SPUtils.put(context, TaskContract.IMGAENUM,position);
        views.setOnClickFillInIntent(R.id.widget_baking_image, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
