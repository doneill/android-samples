package com.jdoneill.placessearch;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jdoneill.placessearch.model.Predictions;
import com.jdoneill.placessearch.presenter.PlaceAutocomplete;
import com.jdoneill.placessearch.presenter.PredictionsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements PredictionsListener {

    private PlaceAutocomplete placeAutocomplete;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lvPlaces);

        placeAutocomplete = new PlaceAutocomplete(this, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.placeSearch).getActionView();

        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                placeAutocomplete.getPredictions(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void getPredictionsList(List<Predictions> predictions) {
        List<String> place = new ArrayList<>();
        List<String> description = new ArrayList<>();

        for(int i = 0; i < predictions.size(); i++){
            place.add(predictions.get(i).getStructuredFormatting().getMainText());
            description.add(predictions.get(i).getDescription());
        }

        //Creating an array adapter for list view
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, place);
        listView.setAdapter(adapter);
    }
}
