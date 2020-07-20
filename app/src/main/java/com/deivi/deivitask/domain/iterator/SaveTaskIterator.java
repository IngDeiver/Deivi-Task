package com.deivi.deivitask.domain.iterator;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.domain.respository.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SaveTaskIterator {
    private TaskRepository taskRepository;

    @Inject
    public SaveTaskIterator(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void insert(final Task task){
          taskRepository.insert(task);
    }
}
