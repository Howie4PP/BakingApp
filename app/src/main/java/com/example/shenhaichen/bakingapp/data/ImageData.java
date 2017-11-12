package com.example.shenhaichen.bakingapp.data;

import com.example.shenhaichen.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhaichen on 09/11/2017.
 */

public class ImageData {

    private static final List<Integer> main_pics = new ArrayList<Integer>() {
        {
            add(R.drawable.cake);
            add(R.drawable.brownies);
            add(R.drawable.cheesecake);
            add(R.drawable.pie);
        }
    };

    private static final List<Integer> small_widget_pics = new ArrayList<Integer>() {
        {
            add(R.drawable.small_baking);
            add(R.drawable.small_brownies);
            add(R.drawable.small_cheesecake);
            add(R.drawable.small_pie);
        }
    };

    public static List<Integer> getMain_pics() {return main_pics;}
    public static List<Integer> getSmall_widget_pics_pics() {return small_widget_pics;}
}
