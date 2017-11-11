package com.example.shenhaichen.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.shenhaichen.bakingapp.adapter.MainGridViewAdapter;
import com.example.shenhaichen.bakingapp.bean.BakingFood;
import com.example.shenhaichen.bakingapp.bean.Ingredients;
import com.example.shenhaichen.bakingapp.bean.Steps;
import com.example.shenhaichen.bakingapp.data.ImageData;
import com.example.shenhaichen.bakingapp.db.DataBaseConstract;
import com.example.shenhaichen.bakingapp.db.SPUtils;
import com.example.shenhaichen.bakingapp.db.TaskContract;
import com.example.shenhaichen.bakingapp.http.BaseCallBack;
import com.example.shenhaichen.bakingapp.http.OkHttpUtils;
import com.example.shenhaichen.bakingapp.http.UrlAddress;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.baking_main_grid)
    GridView gridView;

    private OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(findViewById(R.id.baking_main_grid)!= null){
            gridView.setNumColumns(GridView.AUTO_FIT);
        }

        MainGridViewAdapter adapter = new MainGridViewAdapter(this, ImageData.getMain_pics());
        gridView.setAdapter(adapter);

        Cursor cursor = getBaseContext().getContentResolver().query(TaskContract.TaskEntry.BAKING_CONTENT_URI, null, null,
                null, null);

        if (!cursor.moveToFirst()) {
            getData();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, BakingDetailActivity.class);
                    SPUtils.put(getApplicationContext(), TaskContract.IMGAENUM,position);
                    startActivity(intent);

            }
        });
    }

    /**
     * 得到列表，并存入到数据库中
     */
    private void getData() {
        okHttpUtils.get(UrlAddress.BAKING_URL, new BaseCallBack<List<BakingFood>>() {
            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, List<BakingFood> bakingFoods) {

                insertTotalToDB(bakingFoods);

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    public void insertTotalToDB(List<BakingFood> foods) {
        ContentValues[] valuesArray = new ContentValues[foods.size()];
        for (int i = 0; i < foods.size(); i++) {
            BakingFood bakingFood = foods.get(i);
            List<Ingredients> ingredientsList = bakingFood.getIngredients();
            List<Steps> stepsList = bakingFood.getSteps();

            insertIngredientsToDB(ingredientsList, bakingFood.getId());
            insertStepsToDB(stepsList, bakingFood.getId());

            ContentValues contentValues = new ContentValues();

            contentValues.put(DataBaseConstract.BAKING_ID, bakingFood.getId());
            contentValues.put(DataBaseConstract.BAKING_NAME, bakingFood.getName());
            contentValues.put(DataBaseConstract.BAKING_SERVINGS, bakingFood.getServings());
            contentValues.put(DataBaseConstract.BAKING_IMAGE, bakingFood.getImage());
            valuesArray[i] = contentValues;
        }

        getBaseContext().getContentResolver().bulkInsert(TaskContract.TaskEntry.BAKING_CONTENT_URI, valuesArray);

    }

    public void insertIngredientsToDB(List<Ingredients> ingredients, int id) {

        ContentValues[] valuesArray = new ContentValues[ingredients.size()];
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredients ingredient = ingredients.get(i);

            ContentValues contentValues = new ContentValues();

            contentValues.put(DataBaseConstract.BAKING_ID, id);
            contentValues.put(DataBaseConstract.INGREDIENTS_QUANTITY, ingredient.getQuantity());
            contentValues.put(DataBaseConstract.INGREDIENTS_MEASURE, ingredient.getMeasure());
            contentValues.put(DataBaseConstract.INGREDIENTS_INGREDIENT, ingredient.getIngredient());

            valuesArray[i] = contentValues;

        }

        getBaseContext().getContentResolver().bulkInsert(TaskContract.TaskEntry.INGREDIENTS_CONTENT_URI, valuesArray);

    }

    public void insertStepsToDB(List<Steps> steps, int id) {
        ContentValues[] valuesArray = new ContentValues[steps.size()];
        for (int i = 0; i < steps.size(); i++) {
            Steps step = steps.get(i);

            ContentValues contentValues = new ContentValues();

            contentValues.put(DataBaseConstract.BAKING_ID, id);
            contentValues.put(DataBaseConstract.STEPS_SHORT_DESCRIPTION, step.getShortDescription());
            contentValues.put(DataBaseConstract.STEPS_DESCRIPTION, step.getDescription());
            contentValues.put(DataBaseConstract.STEPS_VIDEOURL, step.getVideoURL());
            contentValues.put(DataBaseConstract.STEPS_THUMBNAILURL, step.getThumbnailURL());

            valuesArray[i] = contentValues;

        }

        getBaseContext().getContentResolver().bulkInsert(TaskContract.TaskEntry.STEPS_CONTENT_URI, valuesArray);
    }


}
