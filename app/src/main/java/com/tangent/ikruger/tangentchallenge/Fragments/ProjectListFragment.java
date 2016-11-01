package com.tangent.ikruger.tangentchallenge.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tangent.ikruger.tangentchallenge.DataEntities.Project;
import com.tangent.ikruger.tangentchallenge.DataEntities.User;
import com.tangent.ikruger.tangentchallenge.R;
import com.tangent.ikruger.tangentchallenge.Util.RestRequestTask;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProjectListFragment extends Fragment implements RestRequestTask.RestResult {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private RestRequestTask mAsyncTask;
    private List<Project> projects = new ArrayList<>();

    private User mUser;

    private MyProjectListRecyclerViewAdapter viewAdapter = new MyProjectListRecyclerViewAdapter(projects,mListener);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProjectListFragment() {
    }


    public static ProjectListFragment newInstance(int columnCount) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projectlist_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(viewAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

        Gson gson = new Gson();

        mUser = gson.fromJson(getArguments().getString("com.tangent.ikruger.tangentchallenge.Activities.User"),User.class);

        Toast.makeText(getContext(), String.format("Welcome %1$s,%2$s", mUser.getFirst_name(),mUser.getLast_name()),Toast.LENGTH_LONG).show();

        mAsyncTask = new RestRequestTask(getString(R.string.projects_url),"");
        mAsyncTask.registerRestResultListener(this);
        String token = getArguments().getString("com.tangent.ikruger.tangentchallenge.Activities.Token");
        mAsyncTask.setMETHOD("GET");
        mAsyncTask.setAUTHERIZATION(token);

        mAsyncTask.execute((URL)null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Project item);
    }

    @Override
    public void onRestResult(String result) {
        Gson gson = new Gson();
        Project[] projects = gson.fromJson(result,Project[].class);

        if (projects.length > 0) {
            this.projects.addAll(Arrays.asList(projects));
            this.viewAdapter.notifyDataSetChanged();
        }else{
            //TODO: Ask user to create a project
            Toast.makeText(getContext(),getString(R.string.no_projects_for_user),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRestError(String error) {
        Log.d("PRoject List",error);
    }
}
