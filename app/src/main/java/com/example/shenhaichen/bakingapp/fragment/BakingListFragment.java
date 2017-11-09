package com.example.shenhaichen.bakingapp.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shenhaichen.bakingapp.R;
import com.example.shenhaichen.bakingapp.adapter.BakingListAdapter;
import com.example.shenhaichen.bakingapp.db.DataBaseConstract;
import com.example.shenhaichen.bakingapp.db.SPUtils;
import com.example.shenhaichen.bakingapp.db.TaskContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhaichen on 08/11/2017.
 */

public class BakingListFragment extends Fragment {

    List<String> mlist = new ArrayList<>();
    private int imageNum = 0;

    public static final String TAG = BakingListFragment.class.getSimpleName();


    public BakingListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在fragment 中运用 menu的先决条件
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.baking_list_fragment, container,false );

        RecyclerView recyclerView = rootView.findViewById(R.id.baking_list_recyclerview);
        imageNum = (int) SPUtils.get(getContext(), TaskContract.IMGAENUM, 1);

        StringBuilder sb = new StringBuilder();

        //根据烘焙ID获得相应的产品
        Cursor ingredient_cursor = getContext().getContentResolver().query(TaskContract.TaskEntry.INGREDIENTS_CONTENT_URI,
                null,"baking_id = ?",
                new String[]{imageNum+1+""},null);
        int number = 1;
        for (ingredient_cursor.moveToFirst(); !ingredient_cursor.isAfterLast(); ingredient_cursor.moveToNext()) {

            String ingredient = ingredient_cursor.getString(ingredient_cursor.getColumnIndex(DataBaseConstract.INGREDIENTS_INGREDIENT));
            float quantity = ingredient_cursor.getFloat(ingredient_cursor.getColumnIndex(DataBaseConstract.INGREDIENTS_QUANTITY));
            String measure = ingredient_cursor.getString(ingredient_cursor.getColumnIndex(DataBaseConstract.INGREDIENTS_MEASURE));
            sb.append("Ingredient "+number+": "+ingredient+"\n");
            sb.append("Quantity: "+quantity+"\n");
            sb.append("Measure: "+measure+"\n");
            if (!ingredient_cursor.isLast()) {
                sb.append("---------------------------------\n");
            }
            number += 1;
        }
        mlist.add(sb.toString());


        Cursor steps_cursor = getContext().getContentResolver().query(TaskContract.TaskEntry.STEPS_CONTENT_URI,
                null,"baking_id = ?",
                new String[]{"1"},null);

        // 取得steps的简短描述
        for (steps_cursor.moveToFirst(); !steps_cursor.isAfterLast(); steps_cursor.moveToNext()) {

            String short_description = steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_SHORT_DESCRIPTION));
            mlist.add(short_description);
        }

        BakingListAdapter adapter = new BakingListAdapter(imageNum,mlist);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }


}
