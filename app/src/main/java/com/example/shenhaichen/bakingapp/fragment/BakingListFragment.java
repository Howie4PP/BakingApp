package com.example.shenhaichen.bakingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shenhaichen.bakingapp.BakingDetailActivity;
import com.example.shenhaichen.bakingapp.R;
import com.example.shenhaichen.bakingapp.StepsDetailActivity;
import com.example.shenhaichen.bakingapp.adapter.BakingListAdapter;
import com.example.shenhaichen.bakingapp.bean.Steps;
import com.example.shenhaichen.bakingapp.db.DataBaseConstract;
import com.example.shenhaichen.bakingapp.db.SPUtils;
import com.example.shenhaichen.bakingapp.db.TaskContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhaichen on 08/11/2017.
 */

public class BakingListFragment extends Fragment implements BakingListAdapter.OnItemClickListener {

    List<String> mlist = new ArrayList<>();
    List<Integer> stepsID_list = new ArrayList<>();
    private int imageNum = 0;

    public static final String TAG = BakingListFragment.class.getSimpleName();

    private Steps steps;

    private OnIDSelectedListener idSelectedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 确保主activity已经实现这个接口，不然将会报错
        try {
            idSelectedListener = (OnIDSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public BakingListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.baking_list_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.baking_list_recyclerview);
        imageNum = (int) SPUtils.get(getContext(), TaskContract.IMGAENUM, 1);

        StringBuilder sb = new StringBuilder();

        getIngredients(sb);
        getSteps();

        BakingListAdapter adapter = new BakingListAdapter(imageNum, mlist);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    /**
     * 获得所需的ingredients信息
     *
     * @param sb
     */
    private void getIngredients(StringBuilder sb) {
        //根据烘焙ID获得相应的产品
        Cursor ingredient_cursor = getContext().getContentResolver().query(TaskContract.TaskEntry.INGREDIENTS_CONTENT_URI,
                null, "baking_id = ?",
                new String[]{imageNum + 1 + ""}, null);
        int number = 1;
        for (ingredient_cursor.moveToFirst(); !ingredient_cursor.isAfterLast(); ingredient_cursor.moveToNext()) {

            String ingredient = ingredient_cursor.getString(ingredient_cursor.getColumnIndex(DataBaseConstract.INGREDIENTS_INGREDIENT));
            float quantity = ingredient_cursor.getFloat(ingredient_cursor.getColumnIndex(DataBaseConstract.INGREDIENTS_QUANTITY));
            String measure = ingredient_cursor.getString(ingredient_cursor.getColumnIndex(DataBaseConstract.INGREDIENTS_MEASURE));
            sb.append("Ingredient " + number + ": " + ingredient + "\n");
            sb.append("Quantity: " + quantity + "\n");
            sb.append("Measure: " + measure + "\n");
            if (!ingredient_cursor.isLast()) {
                sb.append("---------------------------------\n");
            }
            number += 1;
        }
        mlist.add(sb.toString());
    }

    /**
     * 获得所需的steps 信息
     */
    private void getSteps() {
        //查询所需的URL以及Description
        Cursor steps_cursor = getContext().getContentResolver().query(TaskContract.TaskEntry.STEPS_CONTENT_URI,
                null, "baking_id = ?",
                new String[]{imageNum + 1 + ""}, null);

        // 取得steps的简短描述信息
        for (steps_cursor.moveToFirst(); !steps_cursor.isAfterLast(); steps_cursor.moveToNext()) {
            String short_description = steps_cursor.getString(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_SHORT_DESCRIPTION));
            mlist.add(short_description);
            stepsID_list.add(steps_cursor.getInt(steps_cursor.getColumnIndex(DataBaseConstract.STEPS_ID)));
        }

    }


    @Override
    public void click(int position) {

        // position 减去1，是因为在recycleview中，steps的所有信息是从position 1 开始显示的
        if (position != 0) {
            position -= 1;

            int id = stepsID_list.get(position);
            int firstStepsId = stepsID_list.get(0);
            int lastStepsId = stepsID_list.get(stepsID_list.size() - 1);

            if (BakingDetailActivity.TWOPANE) {
                idSelectedListener.onIdSelected(id, firstStepsId, lastStepsId);
            } else {
                toNextActivity(id, firstStepsId, lastStepsId);
            }
        }
    }


    private void toNextActivity(int id, int firstStepsId, int lastStepsId) {

        Intent intent = new Intent(getContext(), StepsDetailActivity.class);
        intent.putExtra(TaskContract.STEPS_ID, id);
        intent.putExtra(TaskContract.LAST_STEPS_ID, lastStepsId);
        intent.putExtra(TaskContract.FIRST_STEPS_ID, firstStepsId);
        startActivity(intent);
    }

    public interface OnIDSelectedListener {
        void onIdSelected(int id, int firstId, int lastId);
    }
}
