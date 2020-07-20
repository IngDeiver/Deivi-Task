package com.deivi.deivitask.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.deivi.deivitask.data.entity.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public  void insert(Task task);

    @Delete
    public void delete(Task task);

    @Query("DELETE FROM tasks WHERE title =:title")
    public void deleteByTitle(String title);

    @Query("SELECT * FROM tasks")
    public List<Task> get();

    @Query("DELETE FROM tasks")
    public void deleteAll();

    @Query("UPDATE tasks SET title =:title, task =:description WHERE id =:id")
    public void updateTask(Long id, String title, String description);
}
