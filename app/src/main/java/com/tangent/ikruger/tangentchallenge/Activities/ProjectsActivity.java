package com.tangent.ikruger.tangentchallenge.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tangent.ikruger.tangentchallenge.R;

public class ProjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = getIntent().getExtras().getString("com.tangent.ikruger.tangentchallenge.Activities.Token");
                Snackbar.make(view, test, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
