package com.deivi.deivitask.ui.intefaces;

import com.deivi.deivitask.data.entity.Task;

public interface TaskDialogListener {
    public void newTask(Task task);
    public void showMessage(String message);
    public void updateTask(Task task);
    public void showDialogUpdateTask(Task task, int position);
}
