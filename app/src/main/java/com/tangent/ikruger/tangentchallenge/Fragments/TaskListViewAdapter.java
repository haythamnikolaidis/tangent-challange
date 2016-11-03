package com.tangent.ikruger.tangentchallenge.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tangent.ikruger.tangentchallenge.DataEntities.Task;
import com.tangent.ikruger.tangentchallenge.R;

import java.util.List;

/**
 * Created by Ivan Kruger on 2016-11-01.
 */

public class TaskListViewAdapter extends ArrayAdapter<TaskListViewAdapter.ViewHolder> {

    private List<Task> taskList;

    public TaskListViewAdapter(Context context, int resource) {
        super(context, resource);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
