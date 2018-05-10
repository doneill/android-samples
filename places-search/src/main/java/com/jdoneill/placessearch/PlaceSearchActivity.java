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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jdoneill.placessearch.model.Predictions;
import com.jdoneill.placessearch.model.Result;
import com.jdoneill.placessearch.presenter.PlaceAutocomplete;
import com.jdoneill.placessearch.presenter.PlacesListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceSearchActivity extends AppCompatActivity implements PlacesListener {

    public static final String EXTRA_PLACE_LATITUDE = "com.jdoneill.placesearch.LATITUDE";
    public static final String EXTRA_PLACE_LONGITUDE = "com.jdoneill.placesearch.LONGITUDE";

    private PlaceAutocomplete mPlaceAutocomplete;
    private List<Predictions> mPredictions;
    private ListView mPlacesListView;
    private String mLatLng;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search_list);

        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        // get the intent
        Intent intent = getIntent();
        mLatLng = intent.getStringExtra(MainActivity.EXTRA_LATLNG);

        mPlacesListView = findViewById(R.id.lvPlaces);

        mPlacesListView.setOnItemClickListener((adapterView, view, pos, l) -> {
            String mainText;
            String placeId = "";
            Object items = mPlacesListView.getItemAtPosition(pos);

            if(items instanceof HashMap){
                HashMap<?, ?> selectedItem = (HashMap<?, ?>) items;

                Map.Entry<?, ?> item = selectedItem.entrySet().iterator().next();
                mainText = (String) item.getValue();

                for(int i = 0; i < mPredictions.size(); i++){
                    if(mainText.equals(mPredictions.get(i).getStructuredFormatting().getMainText())){
                        placeId = mPredictions.get(i).getPlaceId();
                    }
                }

            }
            mPlaceAutocomplete.getResultFromPlaceId(placeId);
        });

        mPlaceAutocomplete = new PlaceAutocomplete(this, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.placeSearch);
        searchMenuItem.expandActionView();

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
                mPlacesListView.setVisibility(View.VISIBLE);
                mPlaceAutocomplete.getPredictions(newText, mLatLng);
                return false;
            }
        });

        return true;
    }

    @Override
    public void getPredictionsList(List<Predictions> predictions) {
        this.mPredictions = predictions;

        ArrayList<HashMap<String, String>> places = new ArrayList<>();
        HashMap<String, String> results;

        for(int i = 0; i < mPredictions.size(); i++){
            results = new HashMap<>();
            results.put("place", mPredictions.get(i).getStructuredFormatting().getMainText());
            results.put("desc", mPredictions.get(i).getStructuredFormatting().getSecondaryText());
            places.add(results);
        }

        //Creating an simple 2 line adapter for list view
        SimpleAdapter adapter = new SimpleAdapter(this, places, android.R.layout.simple_list_item_2,
                new String[]{"place", "desc"},
                new int[]{android.R.id.text1, android.R.id.text2});

        mPlacesListView.setAdapter(adapter);
    }

    @Override
    public void getResult(Result result) {
        double lat = result.getGeometry().getLocation().getLat();
        double lon = result.getGeometry().getLocation().getLng();

        openMapView(lat, lon);
    }

    /**
     * Notification on selected place
     */
    private void openMapView(double lat, double lon) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_PLACE_LATITUDE, lat);
        intent.putExtra(EXTRA_PLACE_LONGITUDE, lon);
        startActivity(intent);
    }
}
