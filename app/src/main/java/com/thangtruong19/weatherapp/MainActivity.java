package com.thangtruong19.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thangtruong19.weatherapp.adapter.ForecastAdapter;
import com.thangtruong19.weatherapp.data.SunshineReference;
import com.thangtruong19.weatherapp.util.NetworkUtil;
import com.thangtruong19.weatherapp.util.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements ForecastAdapter.ForecastAdapterOnClickListener{

    TextView mErrorTextView;
    ProgressBar mLoadingIndicator;
    RecyclerView mRecycleView;
    ForecastAdapter mForecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mErrorTextView = findViewById(R.id.tv_error_message);
        mLoadingIndicator = findViewById(R.id.pb_loading_indictor);
        mRecycleView = findViewById(R.id.rv_weather_forecast);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        layoutManager.setReverseLayout(false);

        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);

        mForecastAdapter = new ForecastAdapter(this);
        mRecycleView.setAdapter(mForecastAdapter);

        String location = SunshineReference.getPreferencedWeatherLocation(this);
        loadWeatherData(location);
    }

    public void showWeatherData(){
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(){
        mRecycleView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String weatherForDay) {
        Toast toast = Toast.makeText(this,weatherForDay,Toast.LENGTH_LONG);
        toast.show();
    }

    class FetchWeatherTask extends AsyncTask<String,Void,String[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... locations) {
            if(locations.length ==0){
                return null;
            }else {
                String location = locations[0];
                URL url = NetworkUtil.buildUrl(location);
                String jsonWeatherResponse = NetworkUtil.getResponseFromURL(url);
                String[] simpleJsonWeatherData =
                        OpenWeatherJsonUtils.getSimpleWeatherStingsFromJson(MainActivity.this,jsonWeatherResponse);
                return simpleJsonWeatherData;
            }
        }

        @Override
        protected void onPostExecute(String[] result) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            Log.i("MainActivity","result: "+result.toString());
            if (result != null){
                showWeatherData();
                mForecastAdapter.setWeatherData(result);
            }else {
                showErrorMessage();
            }
        }
    }

    public void loadWeatherData(String location){
        FetchWeatherTask task = new FetchWeatherTask();
        task.execute(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mForecastAdapter.setWeatherData(null);
        String location = SunshineReference.getPreferencedWeatherLocation(this);
        loadWeatherData(location);
        return true;
    }
}
