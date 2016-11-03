package com.tangent.ikruger.tangentchallenge.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tangent.ikruger.tangentchallenge.DataEntities.Project;
import com.tangent.ikruger.tangentchallenge.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "com.tangent.ikruger.tangentchallenge.DataEntities.Project";
    private Project mProject;

    public ProjectDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param project Parameter 1.
     * @return A new instance of fragment ProjectDetailsFragment.
     */
    public static ProjectDetailsFragment newInstance(String project) {
        ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProject = Project.fromJson(getArguments().getString(ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_project_details, container, false);

        ListViewCompat listView = (ListViewCompat) RootView.findViewById(R.id.taskList);

        listView.setAdapter(new TaskListViewAdapter(getContext(),R.id.taskList));

        TextView startDate = (TextView) RootView.findViewById(R.id.startDateLabel);
        TextView endDate = (TextView) RootView.findViewById(R.id.endDateLabel);
        TextView detailedDescription = (TextView) RootView.findViewById(R.id.projectDescriptionText);

        ProgressBar projectProgress = (ProgressBar) RootView.findViewById(R.id.projectProgress);

        CheckedTextView billable = (CheckedTextView) RootView.findViewById(R.id.billableChecked);

        startDate.setText(mProject.getStart_date());
        endDate.setText(mProject.getEnd_date());
        detailedDescription.setText(mProject.getDescription());
        billable.setChecked(mProject.is_billable());

        projectProgress.setEnabled(mProject.is_active());

        SimpleDateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format));

        try {
            Date start = dateFormat.parse(mProject.getStart_date());
            Date end = dateFormat.parse(mProject.getEnd_date());

            Calendar today = Calendar.getInstance();

            long upperLimit = end.getTime() - start.getTime();
            long current = today.getTimeInMillis() - start.getTime();

            if (upperLimit > current) {
                Long l = (upperLimit / current) * 100;
                projectProgress.setProgress(l.intValue());
            } else if (upperLimit <= current) {
                projectProgress.setProgress(0);
            }

        } catch (ParseException ex) {
            projectProgress.setVisibility(View.GONE);
        }

        return RootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
