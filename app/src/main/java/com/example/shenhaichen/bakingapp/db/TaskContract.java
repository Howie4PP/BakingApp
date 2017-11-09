package com.example.shenhaichen.bakingapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by shenhaichen on 28/10/2017.
 */

public class TaskContract {

    public static final String IMGAENUM = "image_number";
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.shenhaichen.bakingapp";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class TaskEntry implements BaseColumns {


        public static final String BAKING_TABLE = "baking";
        public static final String INGREDIENTS_TABLE = "ingredients";
        public static final String STEPS_TABLE = "steps";

        // TaskEntry content URI = base content URI + path
        public static final Uri BAKING_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(BAKING_TABLE).build();

        public static final Uri INGREDIENTS_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(INGREDIENTS_TABLE).build();

        public static final Uri STEPS_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(STEPS_TABLE).build();

    }
}
