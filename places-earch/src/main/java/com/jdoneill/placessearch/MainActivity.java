package com.jdoneill.placessearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_LATLNG = "com.jdoneill.placesearch.LATLNG";

    private MapView mMapView;
    MenuItem mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        double lat = Double.NaN;
        double lon = Double.NaN;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            lat = extras.getDouble(PlaceSearchActivity.EXTRA_PLACE_LATITUDE, Double.NaN);
            lon = extras.getDouble(PlaceSearchActivity.EXTRA_PLACE_LONGITUDE, Double.NaN);
        }

        // create MapView from layout
        mMapView = findViewById(R.id.mapView);
        // create a map with the BasemapType topographic
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 47.498277, -121.783975, 12);
        // set the map to be displayed in this view
        mMapView.setMap(map);

        if(!Double.isNaN(lat) && !Double.isNaN(lon)){
            Point point = new Point(lon, lat, SpatialReferences.getWgs84());
            mMapView.setViewpointCenterAsync(point, 50000.0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_dashboard, menu);

        mSearch = menu.findItem(R.id.menu_search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.menu_search: {
                openPlaceSearchActivity();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }

    /**
     * Notification on selected place
     */
    private void openPlaceSearchActivity() {
        Intent intent = new Intent(this, PlaceSearchActivity.class);

        intent.putExtra(EXTRA_LATLNG, "47.498277,-121.783975");
        startActivity(intent);
    }

}
