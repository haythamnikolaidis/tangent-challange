package com.tangent.ikruger.tangentchallenge.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tangent.ikruger.tangentchallenge.DataEntities.Project;
import com.tangent.ikruger.tangentchallenge.DataEntities.User;
import com.tangent.ikruger.tangentchallenge.Fragments.CreateProjectFragment;
import com.tangent.ikruger.tangentchallenge.Fragments.ProjectListFragment;
import com.tangent.ikruger.tangentchallenge.R;
import com.tangent.ikruger.tangentchallenge.Util.RestRequestTask;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements ProjectListFragment.OnListFragmentInteractionListener, RestRequestTask.RestResult, CreateProjectFragment.OnProjectCreateSubmitListener {

    private RestRequestTask getUserDataTask;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                /*String test = getIntent().getExtras().getString("com.tangent.ikruger.tangentchallenge.Activities.Token");

                Gson gson = new Gson();

                User me = gson.fromJson("{\"id\":13,\"first_name\":\"admin\",\"last_name\":\"admin\",\"username\":\"admin1\",\"email\":\"\",\"is_staff\":true,\"is_superuser\":true,\"profile\":{\"contact_number\":\"\",\"status_message\":null,\"bio\":null},\"authentications\":[],\"roles\":[]}",User.class);

                Snackbar.make(view, String.format("Welcome %1$s %2$s", me.getFirst_name(), me.getLast_name()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                CreateProjectFragment createFragment = new CreateProjectFragment();

                createFragment.setArguments(getIntent().getExtras());

                getSupportFragmentManager().beginTransaction().add(R.id.content_projects,createFragment).commit();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Project item) {
        Toast.makeText(getApplicationContext(),item.getDescription(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRestResult(String result) {
        ProjectListFragment listFragment = new ProjectListFragment();

        getIntent().getExtras().putString("com.tangent.ikruger.tangentchallenge.Activities.User",result);

        listFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction().add(R.id.content_projects,listFragment).commit();
    }

    @Override
    public void onRestError(String error) {

    }

    @Override
    public void onProjectCreated() {

    }
}
