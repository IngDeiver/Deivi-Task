package com.deivi.deivitask;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.domain.di.App;
import com.deivi.deivitask.domain.iterator.GetTaskIterator;
import com.deivi.deivitask.domain.iterator.RemoveTaskIterator;
import com.deivi.deivitask.domain.iterator.SaveTaskIterator;
import com.deivi.deivitask.domain.iterator.UpdateTaskIterator;

import java.util.List;

import javax.inject.Inject;

public class TaskViewModel extends AndroidViewModel {

    @Inject
    SaveTaskIterator saveTaskIterator;
    @Inject
    RemoveTaskIterator removeTaskIterator;
    @Inject
    GetTaskIterator getTaskIterator;
    @Inject
    UpdateTaskIterator updateTaskIterator;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        ((App) getApplication()).getApplicationComponent().inject(this);
    }

    public void insertTask (Task task){
         saveTaskIterator.insert(task);
    }

    public void removeTask(Task task){
        removeTaskIterator.delete(task);
    }

    public void vaciarLista(){
        removeTaskIterator.deleteAll();
    }

    public  void updateTask(Task task){
        updateTaskIterator.updateTask(task);
    }

    public List<Task> getTasks(){
        return getTaskIterator.get();
    }
}
