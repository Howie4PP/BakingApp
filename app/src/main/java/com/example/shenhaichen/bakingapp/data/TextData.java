package com.example.shenhaichen.bakingapp.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenhaichen on 13/11/2017.
 */

public class TextData {
    private static final List<String> small_summary_food = new ArrayList<String>() {
        {
            add("This pie need 9 ingredients and 6 steps to finish!");
            add("This Brownies need 10 ingredients and 9 steps to finish!");
            add("This Yellow Cake need 10 ingredients and 12 steps to finish!");
            add("This Cheesecake need 9 ingredients and 12 steps to finish!");
        }
    };

    public static List<String> getSmall_summary_food() {return small_summary_food;}

}
