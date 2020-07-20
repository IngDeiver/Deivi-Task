package com.deivi.deivitask.domain.respository;

import androidx.lifecycle.LiveData;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.data.local.dao.TaskDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TaskRepository {
    private TaskDao taskDao;

    @Inject
    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void insert(final Task task){
      new Thread(new Runnable() {
            @Override
            public void run() {
                taskDao.insert(task);
            }
        }).start();
    }

    public void delete(final Task task){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //taskDao.delete(task);
                taskDao.deleteByTitle(task.getTitle());
            }
        }).start();
    }

    public void deleteAll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteAll();
            }
        }).start();
    }

    public void updateTask(final Task task){
        new  Thread(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTask(task.getId(), task.getTitle(), task.getTask());
            }
        }).start();
    }

    public List<Task> get(){
        return  taskDao.get();
    }
}
