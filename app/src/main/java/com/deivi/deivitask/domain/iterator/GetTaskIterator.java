package com.deivi.deivitask.domain.iterator;

import androidx.lifecycle.LiveData;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.domain.respository.TaskRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetTaskIterator {
    private TaskRepository taskRepository;

    @Inject
    public GetTaskIterator(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> get(){
        return taskRepository.get();
    }
}
