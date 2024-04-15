package com.example.test.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test.AddNewTask;
import com.example.test.MainActivity;
import com.example.test.Model.TodoModel;
import com.example.test.R;
import com.example.test.TodoManager;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>
{
    private List<TodoModel> todoList;
    private MainActivity activity;
    private TodoManager todoManager;
    private static final String TAG = "TodoAdapter";
    public TodoAdapter(MainActivity activity, TodoManager todoManager){
        this.activity = activity;
        this.todoManager = todoManager;
    }
    public TodoAdapter(List<TodoModel> list, TodoManager todoManager){
        this.todoList = list;
        this.todoManager = todoManager;
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(TodoAdapter.ViewHolder holder, int position) {
        TodoModel item = todoList.get(position);
        holder.task.setText(item.getText());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setTag(item.getId());
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int taskId = (int)buttonView.getTag();
                if(isChecked){
                    if (todoManager != null) {
                        todoManager.changeStatus(taskId, 1);
                    } else {
                        Log.e(TAG, "todoManager is null");
                    }

                    //todoManager.changeStatus(taskId,1);
                }
                else{
                    todoManager.changeStatus(taskId,0);
                }
            }
        });
    }

    private boolean toBoolean(int n){
        return n!=0;
    }
    @Override
    public int getItemCount() {
        if(todoList == null){
            return 0;
        }
        return todoList.size();
    }
    public Context getContext(){
        return activity;
    }
    public void deleteItem(int position){
        TodoModel item = todoList.get(position);
        todoManager.deleteItem(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }
    public void editItem(int position){
        TodoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("task",item.getText());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
    }
    public void setTasks(List<TodoModel> Todolist){
        this.todoList = Todolist;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder (View view){
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }

    public void bind(Context context, TodoModel task){
    }


}
