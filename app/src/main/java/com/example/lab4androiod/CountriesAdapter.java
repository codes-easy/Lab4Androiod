package com.example.lab4androiod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CountriesAdapter extends
            RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {


        interface ItemListener{
            void onClicked(int post);
        }

        Context context;
        ArrayList<Country> list;
        ItemListener listener;

        public CountriesAdapter(Context context, ArrayList<Country> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.country_row,parent,false);
            return new CountryViewHolder(v);
        }


        @Override
        public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
            holder.countryText.setText(list.get(position).country + "");
       // holder.languageText.setText(list.get(position).country + "");
        }

    @Override
        public int getItemCount() {
            return list.size();
        }

        // inner class
    public class CountryViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener
        {
           // TextView languageText;
            TextView countryText;
           // TextView currencyText;
           // TextView flagText;

            public CountryViewHolder(@NonNull View itemView) {
                super(itemView);
                //languageText =  itemView.findViewById(R.id.language);
                countryText =  itemView.findViewById(R.id.country);
              //  currencyText =  itemView.findViewById(R.id.currency);
              //  flagText =  itemView.findViewById(R.id.flag);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                listener.onClicked( getAdapterPosition());

            }
        }


    }





