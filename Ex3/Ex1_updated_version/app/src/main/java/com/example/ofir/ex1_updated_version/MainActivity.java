package com.example.ofir.ex1_updated_version;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> messages = new ArrayList<String>();
    public static RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send_btn = findViewById(R.id.send_btn);
        final EditText et_input = findViewById(R.id.et_input);
        RecyclerView recyclerView = findViewById(R.id.list_view);
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = et_input.getText().toString();
                if (input.equals("")){
                    Toast.makeText(view.getContext(), "you can't send an empty message, oh silly!", Toast.LENGTH_LONG).show();
                }
                else {
                    // add the message to the messages list and update recycle view
                    messages.add(input);
                    recyclerViewAdapter.notifyDataSetChanged();
                    // clear edit text
                    et_input.setText("");
                    saveData();
                }
            }
        });

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(messages);
        editor.putString("messages list", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("messages list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        messages = gson.fromJson(json, type);

        if (messages == null)
        {
            Log.w("ChatApp", "Current size of chat messages list: 0");
            Toast.makeText(this, "0", Toast.LENGTH_LONG).show();
            messages = new ArrayList<String>();
        }

        else {
            Log.w("ChatApp", "Current size of chat messages list:" + Integer.toString(messages.size()));
        }
    }

}
