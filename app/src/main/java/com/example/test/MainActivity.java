package com.example.test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Adapter.TodoAdapter;
import com.example.test.Model.TodoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {
    private RecyclerView taskRV;
    private TodoAdapter adapter;
    private FloatingActionButton fab;

    private List<TodoModel> taskList;
    private TodoManager todoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        taskList = new ArrayList<>();
        todoManager = new TodoManager(this);

        taskRV = findViewById(R.id.taskRecyclerView);
        taskRV.setLayoutManager((new LinearLayoutManager(this)));

        adapter = new TodoAdapter(taskList,todoManager);
        taskRV.setAdapter(adapter);
        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper( new RecyclerItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(taskRV);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
        /*TodoModel task = new TodoModel();
        task.setText("Zoom class tomorrow");
        task.setStatus(0);
        task.setId(1);

        todoManager.addItem(task);*/


        for(int i=0; i<todoManager.getTodoList().size();i++){
            taskList.add(todoManager.getTodoList().get(i));
        }
       //todoManager.deleteItem(0);
    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList.clear();
        for(int i=0; i<todoManager.getTodoList().size();i++){
            taskList.add(todoManager.getTodoList().get(i));
        }
        Collections.reverse(taskList);
        adapter.setTasks(taskList);
        adapter.notifyDataSetChanged();

    }


}