package com.deivi.deivitask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.deivi.deivitask.data.entity.Task;
import com.deivi.deivitask.ui.adapters.TaskAdapter;
import com.deivi.deivitask.ui.dialogs.TaskDialogFragment;
import com.deivi.deivitask.ui.helpers.RecyclerItemTouchHelper;
import com.deivi.deivitask.ui.intefaces.RecyclerItemTouchHelperListener;
import com.deivi.deivitask.ui.intefaces.TaskDialogListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener, TaskDialogListener {
    private RecyclerView recyclerView;
    private TaskAdapter  taskAdapter;
    private RecyclerView.LayoutManager linearLayout;
    private TaskViewModel taskViewModel;
    private FloatingActionButton floatingActionButton;
    private MaterialToolbar materialToolbar;
    private TaskDialogFragment taskDialogFragment;
    private LinearLayout emtyTaskView;
    private BottomAppBar bottomAppBar;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        taskViewModel =  ViewModelProviders.of(this).get(TaskViewModel.class);
        setContentView(R.layout.activity_main);
        configRecycler();
        configViews();
        getTasks();
    }

    private void configViews() {
        bottomAppBar = findViewById(R.id.bottomAppBar);
        materialToolbar = findViewById(R.id.topAppBar);
        emtyTaskView = findViewById(R.id.emtyTaskView);
        taskDialogFragment = new TaskDialogFragment(null);
        setSupportActionBar(materialToolbar);
        floatingActionButton = findViewById(R.id.floating_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDialogFragment = new TaskDialogFragment(null);
                taskDialogFragment.show(getSupportFragmentManager(), "NewTaskDialogFragment");
            }
        });
    }

    private void getTasks() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = taskViewModel.getTasks();
                taskAdapter.setTasks(tasks);
                toggleEmtyTaskListView();
            }
        }).start();
    }

    public void configRecycler(){
        recyclerView = findViewById(R.id.tasks_recycler);
        taskAdapter = new TaskAdapter();
        taskAdapter.setTaskDialogListener(this);
        linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(taskAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    public void toggleEmtyTaskListView(){
        if(taskAdapter.getItemCount() == 0){
            emtyTaskView.setVisibility(View.VISIBLE);
        }else {
            emtyTaskView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clear : {
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Vaciar lista de notas")
                        .setMessage("¿ Esta seguro de que quiere vaciar la lista ?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(taskAdapter.getItemCount() > 0){
                                    taskViewModel.vaciarLista();
                                    taskAdapter.vaciar();
                                    showMessage("Notas eliminadas");
                                    toggleEmtyTaskListView();
                                }else {
                                    showMessage("No hay notas por eliminar");
                                }
                            }
                        })
                        .show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottombar_menu, menu);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TaskAdapter.ViewHolder) {
            final int deletedIndex = viewHolder.getAdapterPosition();
            Task task = taskAdapter.getTasks().get(deletedIndex);
            taskViewModel.removeTask(task);
            taskAdapter.removeTask(deletedIndex);
            toggleEmtyTaskListView();
            showMessage("Nota eliminada");
        }
    }

    @Override
    public void showMessage(String message){
       Snackbar.make(bottomAppBar, message, Snackbar.LENGTH_SHORT).setAnchorView(floatingActionButton).show();
    }

    @Override
    public void updateTask(Task task) {
        taskViewModel.updateTask(task);
        taskAdapter.update(task, position);
    }

    @Override
    public void showDialogUpdateTask(Task task, int position) {
        this.position = position;
        taskDialogFragment = new TaskDialogFragment(task);
        taskDialogFragment.show(getSupportFragmentManager(), "NewTaskDialogFragment");
    }

    @Override
    public void newTask(Task task) {
        taskViewModel.insertTask(task);
        taskAdapter.addTask(task);
        toggleEmtyTaskListView();
    }
}