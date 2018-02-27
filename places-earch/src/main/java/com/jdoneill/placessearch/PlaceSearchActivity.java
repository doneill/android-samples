package com.jdoneill.placessearch;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jdoneill.placessearch.model.Predictions;
import com.jdoneill.placessearch.presenter.PlaceAutocomplete;
import com.jdoneill.placessearch.presenter.PredictionsListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlaceSearchActivity extends AppCompatActivity implements PredictionsListener {

    public static final String EXTRA_PLACE = "com.jdoneill.placesearch.PLACE";

    private PlaceAutocomplete placeAutocomplete;
    private ListView listView;
    private SimpleAdapter adapter;
    private String latLng;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search_list);

        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        // get the intent
        Intent intent = getIntent();
        latLng = intent.getStringExtra(MainActivity.EXTRA_LATLNG);
        Toast.makeText(this, "LatLng: " + latLng, Toast.LENGTH_SHORT).show();

        listView = findViewById(R.id.lvPlaces);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = listView.getItemAtPosition(i).toString();
                openMapView(item);
            }
        });

        placeAutocomplete = new PlaceAutocomplete(this, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.placeSearch).getActionView();
        searchView.setFocusable(true);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listView.setVisibility(View.VISIBLE);
                placeAutocomplete.getPredictions(newText, latLng);
                return false;
            }
        });

        return true;
    }

    @Override
    public void getPredictionsList(List<Predictions> predictions) {

        ArrayList<HashMap<String, String>> places = new ArrayList<>();
        HashMap<String, String> results;

        for(int i = 0; i < predictions.size(); i++){
            results = new HashMap<>();
            results.put("place", predictions.get(i).getStructuredFormatting().getMainText());
            results.put("desc", predictions.get(i).getStructuredFormatting().getSecondaryText());
            places.add(results);
        }

        //Creating an simple 2 line adapter for list view
        adapter = new SimpleAdapter(this, places, android.R.layout.simple_list_item_2,
                new String[]{"place", "desc"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);
    }

    /**
     * Notification on selected place
     */
    private void openMapView(String item) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(EXTRA_PLACE, item);

        startActivity(intent);

        Toast.makeText(this, "Selected Item: " + item, Toast.LENGTH_LONG).show();
    }
}
