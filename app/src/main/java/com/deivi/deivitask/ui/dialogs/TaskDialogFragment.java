package com.deivi.deivitask.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.deivi.deivitask.R;
import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.ui.intefaces.TaskDialogListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TaskDialogFragment extends DialogFragment {
    private TaskDialogListener taskDialogListener;
    private TextInputEditText title;
    private TextInputEditText task_description;
    private TextInputLayout descriptionLayout;
    private TextInputLayout titleLayout;
    private Button add_btn;
    private Button cancel_btn;
    private Task task;
    private TextView info_title;

    public TaskDialogFragment(Task task){
        super();
        this.task = task;
    }

    public TaskDialogFragment(){
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.new_task_layout, null);

        title = v.findViewById(R.id.title);
        task_description = v.findViewById(R.id.description);
        descriptionLayout = v.findViewById(R.id.descriptionLayout);
        titleLayout = v.findViewById(R.id.titleLayout);
        cancel_btn = v.findViewById(R.id.cancel_button);
        add_btn = v.findViewById(R.id.confirm_button);
        info_title =v.findViewById(R.id.info);

        if( task != null){
            title.setText(task.getTitle());
            task_description.setText(task.getTask());
            info_title.setText("Actualizar nota");
        }else {
            info_title.setText("Nueva nota");
        }

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDialogFragment.this.getDialog().cancel();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = title.getText().toString().trim();
                String description = task_description.getText().toString().trim();

                if(titulo.length() < 30 && titulo.length() > 0){
                    titleLayout.setError(null);
                    if(description.length() > 0){

                        if(task == null){
                            taskDialogListener.newTask(new Task(titulo, description));
                            taskDialogListener.showMessage("Nota añadida");
                        }else {
                            task.setTask(description);
                            task.setTitle(titulo);
                            taskDialogListener.updateTask(task);
                            taskDialogListener.showMessage("Nota Actualizada");
                        }

                        TaskDialogFragment.this.getDialog().cancel();

                    }
                }else{
                    titleLayout.setError("Titulo no valido");
                }

                if(description.length() == 0){
                    descriptionLayout.setError("Escriba una descripción");
                }else{
                    descriptionLayout.setError(null);
                }

            }
        });
        // Set the dialog title
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            taskDialogListener = (TaskDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(" must implement TaskDialogListener");
        }
    }

    public void setTaskDialogListener(TaskDialogListener taskDialogListener) {
        this.taskDialogListener = taskDialogListener;
    }
}
