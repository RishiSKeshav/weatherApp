package com.training1.iMobile3.Presentation;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.training1.iMobile3.Model.City;
import com.training1.iMobile3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class CityListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View mView;
    private CityListAdapter mAdapter;
    private SharedPreference mSharedPreference;
    private ProgressDialog pd;
    private ResultBroadcastReceiver mReceiver;
    private IntentFilter mResultFilter;
    private ArrayList<City> mCities;
    private List<String> mCityList;
    private final String SUCCESS="SUCCESS";

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

        mResultFilter = new IntentFilter("completeAction");
        mSharedPreference = new SharedPreference();
        mReceiver = new ResultBroadcastReceiver();
        mCities = new ArrayList<City>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("CityListFragment", "onCreateView");
        mView = inflater.inflate(R.layout.fragment_city_list,container,false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("CityListFragment", "onViewCreated");

        mCityList = mSharedPreference.getFavorites(getContext());

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Downloading please wait...");
        pd.setCanceledOnTouchOutside(false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        getContext().registerReceiver(mReceiver, mResultFilter);

        if(mCityList.size()>0)
            new WeatherAsyncTask(pd,getContext()).execute(mCityList);

        mAdapter = new CityListAdapter(mCities,getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CityListFragment", "onPause");
        getContext().unregisterReceiver(mReceiver);
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

    public void showAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle("OOPS!!!");

        // Setting Dialog Message
        alertDialog.setMessage("Something Went Wrong. ");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public class ResultBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("CityListFragment", "onRecieve");
            if(pd.isShowing())
                pd.dismiss();

            String flag = intent.getExtras().get("flag").toString();

            if(flag.equals(SUCCESS)) {
                mCities = MySingleton.getInstance().getCities();
                if (mCities != null && !mCities.isEmpty()) {
                    mAdapter = new CityListAdapter(mCities, getContext());
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
            else{
                showAlert();
            }
        }
    }
}