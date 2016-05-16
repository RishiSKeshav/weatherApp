package com.training1.iMobile3.Presentation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.training1.iMobile3.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreference mSharedPreference;
    private CityListFragment mFragment=new CityListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("WeatherApp");
        getSupportActionBar().setIcon(R.drawable.ic_action_icon);

        mSharedPreference = new SharedPreference();

        if(mSharedPreference.getCityCount(getApplicationContext())>0){
            callFragment(new CityListFragment());
        }
        else{
            callFragment(new LocationFragment());
        }
    }

    private void callFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        trans.replace(R.id.frameLayout,fragment );
        trans.commit();
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

                startActivity(new Intent(this,AddCityActivity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}