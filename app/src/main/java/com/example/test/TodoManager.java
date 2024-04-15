package com.example.test;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.test.Model.TodoModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TodoManager {
    private static final String TODO_LIST_KEY = "todo_list";
    private static final String COUNTER_KEY = "counter";
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private int counter = 1;
    public TodoManager(Context context) {
        sharedPreferences = context.getSharedPreferences("TodoListPrefs", Context.MODE_PRIVATE);
        gson = new Gson();
        counter = sharedPreferences.getInt(COUNTER_KEY, 0);
    }

    public void addItem(TodoModel item) {
        List<TodoModel> todoList = getTodoList();
        item.setId(counter++);
        saveCounter();
        todoList.add(item);
        saveTodoList(todoList);
    }
    private void saveCounter() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNTER_KEY, counter);
        editor.apply();
    }
    public List<TodoModel> getTodoList() {
        //Gson gson = new Gson();
        String json = sharedPreferences.getString(TODO_LIST_KEY, "[]");
        Type type = new TypeToken<List<TodoModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveTodoList(List<TodoModel> todoList) {
        Gson gson = new Gson();
        String json = gson.toJson(todoList);
        sharedPreferences.edit().putString(TODO_LIST_KEY, json).apply();
    }
    public void changeStatus(int itemId, int newStatus) {

        List<TodoModel> todoList = getTodoList();

        // Find the item by its ID
        for (TodoModel item : todoList) {
            if (item.getId() == itemId) {
                // Update the status of the item
                item.setStatus(newStatus);
                break; // Exit the loop once the item is found and updated
            }
        }

        // Save the updated list back to SharedPreferences
        saveTodoList(todoList);
    }
    // Inside TodoManager class
    public void updateItem(int taskId, String newText) {
        List<TodoModel> todoList = getTodoList();
        for (TodoModel task : todoList) {
            if (task.getId() == taskId) {
                task.setText(newText);
                break;
            }
        }
        saveTodoList(todoList);
    }
    public void deleteItem(int position) {
        List<TodoModel> todoList = getTodoList();

        // Check if the position is valid
        if (position >= 0 && position < todoList.size()) {
            // Remove the item at the specified position
            todoList.remove(position);

            // Save the updated list back to SharedPreferences
            saveTodoList(todoList);
        }
    }

}
