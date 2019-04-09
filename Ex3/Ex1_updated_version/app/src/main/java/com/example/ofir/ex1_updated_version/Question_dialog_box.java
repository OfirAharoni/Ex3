package com.example.ofir.ex1_updated_version;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;

public class Question_dialog_box extends DialogFragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.delete_dialog_box, null);
        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        Button del_btn = view.findViewById(R.id.del_btn);

        // Get message info from activity
        Bundle args = getArguments();
        final String selected_msg = args.getString("msg", "N/A");

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // close the dialog with no further actions
                dismiss();
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i < MainActivity.messages.size(); i++)
                {
                    String msg = MainActivity.messages.get(i);
                    if (msg.equals(selected_msg))
                    {
                        MainActivity.messages.remove(msg);
                        break;
                    }
                }
                // update adapter
                MainActivity.recyclerViewAdapter.notifyDataSetChanged();
                // update sharedPreferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(MainActivity.messages);
                editor.putString("messages list", json);
                editor.apply();

                dismiss();
            }
        });

        return view;
    }
}
