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

    public static List<Integer> getMain_pics() {return main_pics;}
}
