package com.example.navigationdrawer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.navigationdrawer.model.Country;
import com.example.navigationdrawer.model.CountryFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    DrawerLayout drawerLayout;
    ListView listViewCountries;

    ArrayList<Country> listOfCountries;
    ArrayAdapter<Country> countryArrayAdapter;
    ImageButton btnOpenClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

    }

    private void initialize() {

        drawerLayout = findViewById(R.id.drawer_layout);
        listViewCountries = findViewById(R.id.ListViewCountries);
        listViewCountries.setOnItemClickListener(this);
        listOfCountries = new ArrayList<Country>();
        listOfCountries .add(new Country("India","New Delhi"));
        listOfCountries .add(new Country("Bresil","Brasilia"));
        listOfCountries .add(new Country("Canada","Ottawa"));

        countryArrayAdapter = new ArrayAdapter<Country>(
                this, android.R.layout.simple_expandable_list_item_1,listOfCountries
        );
        listViewCountries.setAdapter(countryArrayAdapter);
        // manage custom toolbar
        // 1) Access to actual toolbar

        ActionBar mActionBar = getSupportActionBar();

        // 2)  Hide the application label (its a project name)
        mActionBar.setDisplayShowTitleEnabled(false);

        // 3 - Do a layout inflation : convert layout to a view
        LayoutInflater li = LayoutInflater.from(this);
        View customToolbar = li.inflate(R.layout.custom_toolbar,null);

        // 4 - set the custom toolbar
        mActionBar.setCustomView(customToolbar);
        mActionBar.setDisplayShowCustomEnabled(true);

        //Manage the ImageButton

        // if you set like this --> the app crashes
        // because the widget btnOpenClose is not in activity_main.xml
       // btnOpenClose = findViewById(R.id.btnOpenClose);

        btnOpenClose = customToolbar.findViewById(R.id.btnOpenClose);
        btnOpenClose.setOnClickListener(this);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Bundle bundle = new Bundle();
        // the data that we want to send to the fragment


        bundle.putSerializable("country",listOfCountries.get(position));
        // replace the liner with the fragment layout : fragment_country

        CountryFragment countryFragment = new CountryFragment();
        countryFragment.setArguments(bundle);
        // reference the fragment manager
        android.app.FragmentManager fragmentManager = getFragmentManager();

        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_ui,countryFragment);
        //commit the transaction
        fragmentTransaction.commit();
        setTitle(listOfCountries.get(position).getcName());
        drawerLayout.closeDrawer(listViewCountries);
    }

    @Override
    public void onClick(View v) {

        if (drawerLayout.isDrawerOpen(listViewCountries))
            drawerLayout.closeDrawer(listViewCountries);
        else
            drawerLayout.openDrawer(listViewCountries);


    }
}
