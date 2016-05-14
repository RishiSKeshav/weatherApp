package com.training1.myapplication;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends AppCompatActivity{

    protected final static String SEARCH_TERM = "search_term";
    private EditText search;
    private ArrayAdapter<String> listAdapter;
    private ListView listView;
    private ProgressDialog pd;
    private ResultBroadcastReceiever receiever;
    private IntentFilter filter;
    private List<String> list;
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        sharedPreference = new SharedPreference();
        list = new ArrayList<>();
        listView=(ListView) findViewById(R.id.listView);
        listAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listAdapter);

        filter = new IntentFilter("autocompleteAction");

        search = (EditText) findViewById(R.id.url);
        getSupportActionBar().setTitle("Add a City");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                new AutocompleteAsyncTask(getApplicationContext(), pd).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sharedPreference.addCity(getApplicationContext(), cleanUp(parent.getItemAtPosition(position).toString()));

                Intent intent = new Intent(AddCityActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        receiever = new ResultBroadcastReceiever();
        registerReceiver(receiever, filter);

    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiever);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String cleanUp(String selectedItem){

        String[] temp = selectedItem.split(",");
        return temp[1].trim().replace(" ","_") + "," + temp[0].trim().replace(" ","_");
    }

    public class ResultBroadcastReceiever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            listAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.autocomplete_item,R.id.txtview,intent.getStringArrayListExtra("suggestion"));
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }
}


