package com.example.navigationdrawer.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.navigationdrawer.R;

public class CountryFragment extends android.app.Fragment {

    TextView textViewCCapital;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textViewCCapital = getActivity().findViewById(R.id.textViewCCapital);
        Country country = (Country)getArguments().getSerializable("country"); // country comes from mainactivity
        textViewCCapital.setText(country.getcCapital());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country,container,false );
    }
}
