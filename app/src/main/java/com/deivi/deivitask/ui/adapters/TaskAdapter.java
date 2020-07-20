package com.deivi.deivitask.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deivi.deivitask.R;
import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.ui.intefaces.TaskDialogListener;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<Task> tasks;
    private TaskDialogListener taskDialogListener;

    public TaskAdapter() {
        tasks = new ArrayList<>();
    }

    public void setTaskDialogListener(TaskDialogListener taskDialogListener) {
        this.taskDialogListener = taskDialogListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(tasks.get(position).getTitle());
        holder.description.setText(tasks.get(position).getTask());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private CardView cardView;
        public  RelativeLayout viewBackground, viewForeground;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            cardView = itemView.findViewById(R.id.card);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskDialogListener.showDialogUpdateTask(tasks.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public void removeTask(int position) {
        tasks.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void  addTask(Task task){
        tasks.add(task);
        notifyItemInserted(tasks.size());
    }

    public void vaciar(){
        tasks.clear();
        notifyDataSetChanged();

    }

    public void update(Task task, int position){
        Task oldTask = tasks.get(position);
        oldTask.setTitle(task.getTitle());
        oldTask.setTask(task.getTask());
        notifyItemChanged(position);
    }

    public void setTasks(List<Task> tasks){ this.tasks = tasks; }

    public List<Task> getTasks() {
        return tasks;
    }
}
