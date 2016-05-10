package com.training1.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String city=null;
    private String state=null;
    private SharedPreference sharedPreference;
    private CityListFragment fragment=new CityListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("WeatherApp");
        actionBar.setIcon(R.drawable.ic_action_icon);

        sharedPreference = new SharedPreference();
    }

    private void getCityStateDetails1(){

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.city_prompt,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        final EditText cityName = (EditText) view.findViewById(R.id.cityName);
        final EditText stateName = (EditText) view.findViewById(R.id.stateName);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                city=cityName.getText().toString();
                                state=stateName.getText().toString();

                                sharedPreference.addCity(getApplicationContext(), state + "," + city);

                                FragmentManager manager = getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();

                                transaction.add(R.id.list_fragment, fragment);
                                transaction.commit();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                getCityStateDetails1();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}