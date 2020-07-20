package com.deivi.deivitask.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.data.local.dao.TaskDao;

@Database(entities = {Task.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
