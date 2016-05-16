package com.training1.iMobile3.Presentation;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.training1.iMobile3.R;

import java.util.ArrayList;
import java.util.List;

public class AddCityActivity extends AppCompatActivity {

    private EditText mSearch;
    private ArrayAdapter<String> mListAdapter;
    private ListView mListView;
    private ResultBroadcastReceiever mReceiever;
    private IntentFilter mFilter;
    private List<String> mList;
    private SharedPreference mSharedPreference;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        mSharedPreference = new SharedPreference();
        mList = new ArrayList<>();
        mListView = (ListView) findViewById(R.id.listView);
        mListAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mListAdapter);

        mFilter = new IntentFilter("autocompleteAction");

        mSearch = (EditText) findViewById(R.id.url);
        getSupportActionBar().setTitle("Add a City");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                new AutocompleteAsyncTask(getApplicationContext(), pd)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mSearch.getText()
                                .toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mSharedPreference.addCity(getApplicationContext(),
                        cleanUp(parent.getItemAtPosition(position).toString()));

                /*Starting MainActivity after adding the city to shared preference*/
                Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mReceiever = new ResultBroadcastReceiever();
        registerReceiver(mReceiever, mFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiever);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*cleanUp method is to format the selectedItem as needed*/
    private String cleanUp(String selectedItem) {

        String[] temp = selectedItem.split(",");
        return temp[1].trim().replace(" ", "_") + "," + temp[0].trim().replace(" ", "_");
    }

    public class ResultBroadcastReceiever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            mListAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.autocomplete_item, R.id.txtview,
                    intent.getStringArrayListExtra("suggestion"));
            mListView.setAdapter(mListAdapter);
            mListAdapter.notifyDataSetChanged();
        }
    }
}


