package ratingapp.ddey.com.dam_project.activities.general;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ratingapp.ddey.com.dam_project.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final float DEFAULT_ZOOM = 17f;

    private GoogleMap mGoogleMap;
    private LatLng appHqCoordinates = new LatLng(44.447938, 26.099121);
    private String markerText = "This is where the application was built!";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BACK BUTTON pt toolbar pus in locul actionbarului
        this.setTitle("Maps");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        moveCameraToHQ(appHqCoordinates, DEFAULT_ZOOM, markerText);
    }

    private void moveCameraToHQ(LatLng latLng, float zoom, String text) {
        mGoogleMap.clear();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!text.equals("")) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(text)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_home_maps));
            mGoogleMap.addMarker(markerOptions);
        }
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}
