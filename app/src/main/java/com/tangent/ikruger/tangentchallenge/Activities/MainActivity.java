package com.tangent.ikruger.tangentchallenge.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tangent.ikruger.tangentchallenge.DataEntities.Project;
import com.tangent.ikruger.tangentchallenge.DataEntities.User;
import com.tangent.ikruger.tangentchallenge.Fragments.CreateProjectFragment;
import com.tangent.ikruger.tangentchallenge.Fragments.ProjectDetailsFragment;
import com.tangent.ikruger.tangentchallenge.Fragments.ProjectListFragment;
import com.tangent.ikruger.tangentchallenge.R;
import com.tangent.ikruger.tangentchallenge.Util.RestRequestTask;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements ProjectListFragment.OnListFragmentInteractionListener, RestRequestTask.RestResult, CreateProjectFragment.OnProjectCreateSubmitListener {

    private RestRequestTask getUserDataTask;
    private ProjectListFragment mProjecList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProjecList = new ProjectListFragment();

        if (findViewById(R.id.content_projects) != null){
            if (savedInstanceState != null) {
                return;
            }

            getUserDataTask = new RestRequestTask(getString(R.string.user_url),"");
            getUserDataTask.registerRestResultListener(this);
            getUserDataTask.setAUTHERIZATION(getIntent().getExtras().getString("com.tangent.ikruger.tangentchallenge.Activities.Token"));
            getUserDataTask.execute((URL)null);


        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateProjectFragment createFragment = new CreateProjectFragment();

                createFragment.setArguments(getIntent().getExtras());

                getSupportFragmentManager().beginTransaction().replace(R.id.content_projects,createFragment).commit();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Project item) {
        ProjectDetailsFragment detailsFragment = new ProjectDetailsFragment();

        getIntent().getExtras().putString("com.tangent.ikruger.tangentchallenge.DataEntities.Project",item.toJson());

        detailsFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.content_projects,detailsFragment).commit();
    }

    @Override
    public void onRestResult(String result) {

        getIntent().getExtras().putString("com.tangent.ikruger.tangentchallenge.Activities.User",result);

        mProjecList.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().replace(R.id.content_projects,mProjecList).commit();
    }

    @Override
    public void onRestError(String error) {
        Log.d("REST","Could not retrieve user data:" + error);

        ErrorDialog ed = new ErrorDialog();
        Bundle argumnet = new Bundle();
        argumnet.putString(ErrorDialog.MESSAGE,getString(R.string.user_data_retireve_error));
        ed.setArguments(argumnet);

        ed.show(getSupportFragmentManager(),"");
    }

    @Override
    public void onProjectCreated(Project newProject) {
        RestRequestTask addProjectTask = new RestRequestTask(getString(R.string.projects_url),newProject.toJson());
        addProjectTask.registerRestResultListener(new RestRequestTask.RestResult() {
            @Override
            public void onRestResult(String result) {
                Toast.makeText(getApplicationContext(),R.string.project_create_project_created,Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_projects,mProjecList).commit();
            }

            @Override
            public void onRestError(String error) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_projects,mProjecList).commit();
                ErrorDialog ed = new ErrorDialog();
                Bundle argumnet = new Bundle();
                argumnet.putString(ErrorDialog.MESSAGE,getString(R.string.project_create_project_create_error));
                ed.setArguments(argumnet);

                ed.show(getSupportFragmentManager(),"");
            }
        });
        addProjectTask.setAUTHERIZATION(getIntent().getExtras().getString("com.tangent.ikruger.tangentchallenge.Activities.Token"));
        addProjectTask.execute((URL)null);
    }

    public static class ErrorDialog extends DialogFragment{

        public final static String MESSAGE = "MESSAGE";

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){

            Bundle arguments = getArguments();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(arguments.getString(ErrorDialog.MESSAGE))
                    .setPositiveButton(R.string.error_dialog_ok_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).setTitle(R.string.error_dialog_title);
            return builder.create();
        }
    }
}
