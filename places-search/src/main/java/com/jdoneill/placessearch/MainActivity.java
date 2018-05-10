package com.jdoneill.placessearch;

import android.content.Intent;
import android.graphics.Color;
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
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_LATLNG = "com.jdoneill.placesearch.LATLNG";

    private MapView mMapView;

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
        // graphics overlay for place search location marker
        GraphicsOverlay graphicsOverlay = addGraphicsOverlay(mMapView);
        // check if activity is returned from place search
        if(!Double.isNaN(lat) && !Double.isNaN(lon)){
            graphicsOverlay.getGraphics().clear();
            // create a point from returned place search
            Point point = new Point(lon, lat, SpatialReferences.getWgs84());
            // create a marker at search location
            SimpleMarkerSymbol sms = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CROSS, Color.BLACK, 15.0f);
            Graphic graphic = new Graphic(point, sms);
            graphicsOverlay.getGraphics().add(graphic);
            // zoom in to point location
            mMapView.setViewpointCenterAsync(point, 50000.0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_dashboard, menu);

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
     * Create a Graphics Overlay
     *
     * @param mapView MapView to add the graphics overlay to
     */
    private GraphicsOverlay addGraphicsOverlay(MapView mapView){
        //create graphics overlay
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        // add overlay to mapview
        mapView.getGraphicsOverlays().add(graphicsOverlay);
        return graphicsOverlay;
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
