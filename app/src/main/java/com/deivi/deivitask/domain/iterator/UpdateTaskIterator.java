package com.deivi.deivitask.domain.iterator;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.domain.respository.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UpdateTaskIterator {
    private TaskRepository taskRepository;

    @Inject
    public UpdateTaskIterator(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void updateTask(Task task){
        taskRepository.updateTask(task);
    }
}
