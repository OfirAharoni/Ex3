package com.example.ofir.ex1_updated_version;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ofir on 25/03/2019.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(MainActivity.messages.get(position));
    }

    @Override
    public int getItemCount() {
        return MainActivity.messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.custom_tv);
            textView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // Create fragment in order to present additional info
                    Question_dialog_box question_dialog_box = new Question_dialog_box();
                    Bundle args = new Bundle();
                    args.putString("msg", textView.getText().toString());
                    question_dialog_box.setArguments(args);

                    FragmentManager manager = ((AppCompatActivity)v.getContext()).getFragmentManager();
                    question_dialog_box.show(manager, "Delete message warning");

                    return false;
                }
            });
        }
    }
}
