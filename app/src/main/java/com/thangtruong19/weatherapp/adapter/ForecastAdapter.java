package com.thangtruong19.weatherapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thangtruong19.weatherapp.R;

/**
 * Created by User on 14/05/2019.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {
    private String[] mWeatherData;
    private TextView mWeatherTextView;
    private ForecastAdapterOnClickListener mClickHandler;

    public interface ForecastAdapterOnClickListener{
        void onClick(String weatherForDay);
    }
    public ForecastAdapter(ForecastAdapterOnClickListener clickHandler){
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_tem,parent,false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        mWeatherTextView.setText(mWeatherData[position]);
    }

    @Override
    public int getItemCount() {
        if(mWeatherData == null){
            return 0;
        }else{
            return mWeatherData.length;
        }
    }

    public void setWeatherData(String[] weatherData){
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        public ForecastViewHolder(View itemView){
            super(itemView);
            mWeatherTextView = itemView.findViewById(R.id.tv_weather_data);
            mWeatherTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String weatherData = mWeatherData[adapterPosition];
            mClickHandler.onClick(weatherData);
        }
    }
}
