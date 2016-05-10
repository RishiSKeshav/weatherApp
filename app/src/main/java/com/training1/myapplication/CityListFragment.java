package com.training1.myapplication;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class CityListFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    private CityListAdapter adapter;
    private SharedPreference sharedPreference;
    private ProgressDialog pd;
    private ResultBroadcastReceiver receiver;
    private IntentFilter resultFilter;
    private ArrayList<City> cities;

    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("CityListFragment", "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultFilter = new IntentFilter("completeAction");
        sharedPreference = new SharedPreference();
        receiver = new ResultBroadcastReceiver();
        cities = new ArrayList<City>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("CityListFragment","onCreateView");
        view = inflater.inflate(R.layout.fragment_city_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CityListFragment", "onViewCreated");

        List<String> cityList = sharedPreference.getFavorites(getContext());

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Downloading please wait...");
        pd.setCanceledOnTouchOutside(false);

        if(cityList.size()>0)
            new WeatherAsyncTask(pd,getContext()).execute(cityList);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CityListAdapter(cities,getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("CityListFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("CityListFragment", "onResume");
        getContext().registerReceiver(receiver, resultFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CityListFragment", "onPause");
        getContext().unregisterReceiver(receiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("CityListFragment", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class ResultBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("CityListFragment", "onRecieve");
            if(pd.isShowing())
                pd.dismiss();

            cities = MySingleton.getInstance().getCities();
            //cities = intent.getParcelableArrayListExtra("cities");

            if(cities!=null && !cities.isEmpty()) {
                adapter = new CityListAdapter(cities,getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }
}