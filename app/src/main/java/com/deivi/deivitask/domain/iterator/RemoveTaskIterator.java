package com.deivi.deivitask.domain.iterator;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.domain.respository.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RemoveTaskIterator {
    private TaskRepository taskRepository;

    @Inject
    public RemoveTaskIterator(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void delete(Task task){
        taskRepository.delete(task);
    }

    public void deleteAll(){
        taskRepository.deleteAll();
    }
}
