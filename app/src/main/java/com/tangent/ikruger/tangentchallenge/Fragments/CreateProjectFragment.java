package com.tangent.ikruger.tangentchallenge.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.tangent.ikruger.tangentchallenge.R;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateProjectFragment.OnProjectCreateSubmitListener} interface
 * to handle interaction events.
 */
public class CreateProjectFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private OnProjectCreateSubmitListener mListener;
    private DatePickerDialog mDatePicker;
    private boolean startDateSelected = true;

    private Button btStartDate;
    private Button btEndDate;

    public CreateProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_create_project, container, false);

       // Toolbar tb = (Toolbar) rootView.findViewById(R.id.toolbar);
       // ((AppCompatActivity)getActivity()).setSupportActionBar(tb);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProjectCreated();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProjectCreateSubmitListener) {
            mListener = (OnProjectCreateSubmitListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btStartDate = (Button) getActivity().findViewById(R.id.startDateButton);
        btEndDate = (Button) getActivity().findViewById(R.id.endDateButton);

        DateFormat dateFormatter = DateFormat.getDateInstance();

        Calendar today = Calendar.getInstance();

        btStartDate.setText(dateFormatter.format(today.getTime()));
        btEndDate.setText(dateFormatter.format(today.getTime()));

        mDatePicker = new DatePickerDialog(getContext(),this,today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH));

        btStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateSelected = true;
                mDatePicker.show();
            }
        });

        btEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateSelected = false;
                mDatePicker.show();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,dayOfMonth);
        DateFormat df = DateFormat.getDateInstance();

        if (startDateSelected){
            btStartDate.setText(df.format(cal.getTime()));
        }else{
            btEndDate.setText(df.format(cal.getTime()));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_project_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save:
                mListener.onProjectCreated();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProjectCreateSubmitListener {
        // TODO: Update argument type and name
        void onProjectCreated();
    }
}
