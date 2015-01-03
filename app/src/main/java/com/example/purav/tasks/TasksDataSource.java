package com.example.purav.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Purav on 12/30/2014.
 */
public class TasksDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper dbhelper;
    private String [] allColumns = {SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_NOTE, SQLiteHelper.COLUMN_TITLE};

    public TasksDataSource(Context context) {
        dbhelper = new SQLiteHelper(context);
    }

    public void open() throws SQLiteException {
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        dbhelper.close();
    }

    public Tasks createTasks(String note, String title) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_NOTE,note);
        values.put(SQLiteHelper.COLUMN_TITLE,title);
        long insertId = database.insert(SQLiteHelper.TABLE_TASKS,null,values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_TASKS,allColumns,SQLiteHelper.COLUMN_ID + " = " +insertId,
                null,null,null,null);
        cursor.moveToFirst();
        Tasks newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void deleteTask(Tasks task) {
        long id = task.getId();
        database.delete(SQLiteHelper.TABLE_TASKS,SQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Tasks> getAllTasks() {
        List<Tasks> tasks = new ArrayList<Tasks>();
        Cursor cursor = database.query(SQLiteHelper.TABLE_TASKS, allColumns, null,null,null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Tasks task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public Tasks getTask(long id) {
        Cursor cursor = database.query(SQLiteHelper.TABLE_TASKS,allColumns,SQLiteHelper.COLUMN_ID + "=?",
                new String[] {String.valueOf(id)},null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursorToTask(cursor);
    }

    private Tasks cursorToTask(Cursor cursor) {
        Tasks task = new Tasks();
        task.setId(cursor.getLong(0));
        task.setNote(cursor.getString(1));
        task.setTitle(cursor.getString(2));
        return task;
    }
}
