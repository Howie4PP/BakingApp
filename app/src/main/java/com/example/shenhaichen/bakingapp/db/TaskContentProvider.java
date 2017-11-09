package com.example.shenhaichen.bakingapp.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.example.shenhaichen.bakingapp.db.TaskContract.TaskEntry.BAKING_TABLE;
import static com.example.shenhaichen.bakingapp.db.TaskContract.TaskEntry.INGREDIENTS_TABLE;
import static com.example.shenhaichen.bakingapp.db.TaskContract.TaskEntry.STEPS_TABLE;


/**
 * Created by shenhaichen on 28/10/2017.
 */

public class TaskContentProvider extends ContentProvider {

    //all task
    public static final int BAKING_TASKS = 100;
    public static final int INGREDIENTS_TASKS = 200;
    public static final int STEPS_TASKS = 300;
    // single task
    public static final int BAKING_TASKS_WITH_ID = 101;
    public static final int INGREDIENTS_TASKS_WITH_ID = 201;
    public static final int STEPS_TASKS_WITH_ID = 301;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private BakingDBHelper mhelper;

    /**
     * 根据不同Uri进行任务区分
     *
     * @return
     */
    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        //添加不同电影的uri
        uriMatcher.addURI(TaskContract.AUTHORITY, BAKING_TABLE, BAKING_TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, BAKING_TABLE + "/#", BAKING_TASKS_WITH_ID);

        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.TaskEntry.INGREDIENTS_TABLE, INGREDIENTS_TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.TaskEntry.INGREDIENTS_TABLE + "/#", INGREDIENTS_TASKS_WITH_ID);

        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.TaskEntry.STEPS_TABLE, STEPS_TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.TaskEntry.STEPS_TABLE + "/#", STEPS_TASKS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        mhelper = new BakingDBHelper(getContext());

        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mhelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        String table = null;
        switch (match) {
            case BAKING_TASKS:
                table = BAKING_TABLE;
                break;
            case INGREDIENTS_TASKS:
                table = INGREDIENTS_TABLE;
                break;
            case STEPS_TASKS:
                table = STEPS_TABLE;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        db.beginTransaction();
        int rowsInserted = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(table, null, value);
                if (_id != -1) {
                    rowsInserted++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if (rowsInserted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsInserted;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Get access to the task database (to write new data to)
//        final SQLiteDatabase db = mhelper.getWritableDatabase();
//
//        // Write URI matching code to identify the match for the tasks directory
//        int match = sUriMatcher.match(uri);
//        Uri returnUri = null; // URI to be returned
//        String table = null;
//
//
//        switch (match) {
//            case BAKING_TASKS:
//                table = POP_MOVIE_TABLE;
//                break;
//            case INGREDIENTS_TASKS:
//                table = TOP_MOVIE_TABLE;
//                break;
//            case STEPS_TASKS:
//                table = FAV_MOVIE_TABLE;
//                break;
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        long id = db.insert(table, null, values);
//        if (id > 0) {
//            returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.FAV_MOVIE_CONTENT_URI, id);
//        } else {
//            throw new android.database.SQLException("Failed to insert row into " + uri);
//        }
//        // Notify the resolver if the uri has been changed, and return the newly inserted URI
//        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return null;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mhelper.getReadableDatabase();

        // Write URI match code and set a variable to return a Cursor
        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        String table = null;
        // Query for the tasks directory and write a default case
        switch (match) {
            case BAKING_TASKS:
                table = BAKING_TABLE;
                break;
            case INGREDIENTS_TASKS:
                table = INGREDIENTS_TABLE;
                break;
            case STEPS_TASKS:
                table = STEPS_TABLE;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor = db.query(table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // Get access to the database and write URI matching code to recognize a single item
//        final SQLiteDatabase db = mhelper.getWritableDatabase();
//
//        int match = sUriMatcher.match(uri);
//        // Keep track of the number of deleted tasks
//        int tasksDeleted; // starts as 0
//
//        // Write the code to delete a single row of data
//        // [Hint] Use selections to delete an item by its row ID
//        switch (match) {
//            // Handle the single item case, recognized by the ID included in the URI path
//            case FAV_TASK_WITH_ID:
//                // Get the task ID from the URI path
//                String id = uri.getPathSegments().get(1);
//                // Use selections/selectionArgs to filter for this ID
//                tasksDeleted = db.delete(FAV_MOVIE_TABLE, "movie_id=?", new String[]{id});
//                break;
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        // Notify the resolver of a change and return the number of items deleted
//        if (tasksDeleted != 0) {
//            // A task was deleted, set notification
//            getContext().getContentResolver().notifyChange(uri, null);
//        }

        // Return the number of tasks deleted
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
