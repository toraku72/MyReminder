package com.toracode.myreminder.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.toracode.myreminder.Task;
import com.toracode.myreminder.database.TaskDbSchema.TaskTable;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskTable.Cols.UUID));
        String title = getString(getColumnIndex(TaskTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TaskTable.Cols.DATE));
        int isDone = getInt(getColumnIndex(TaskTable.Cols.DONE));

        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setDate(new Date(date));
        task.setDone(isDone != 0);

        return task;
    }
}
