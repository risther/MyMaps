package com.desarrollo.mymaps;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;

    String numero ="";
    int numer2;
    Double longitud,latitud;
    String nombre;
    private static final String TAG = "Estilo del mapa";
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    // CREAR EL MENU DE OPCIONES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.map_options,menu);


        numero=getIntent().getStringExtra("dato");
      numer2=Integer.parseInt(numero);
        if (numer2==1){
           latitud=35.680513;
            longitud=139.769051;
            nombre="Japon";
        }
        if (numer2==2){
            latitud=52.516934;
            longitud=13.403190;
            nombre="Alemania";
        }
        if (numer2==3){
            latitud=41.902609;
            longitud=12.494847;
            nombre="Italia";
        }
        if (numer2==4){
            latitud=48.843489;
            longitud=2.355331;
            nombre="Francia";
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (numer2==1){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (numer2==2){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        if (numer2==3){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (numer2==4){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

        // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        // mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom = 16;

        try {
            boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(),R.raw.map_style));

            if(!success) {
                Log.e(TAG, "Fallo al cargar estilo del mapa");
            }

        }catch (Resources.NotFoundException exception ){
            Log.e(TAG,"No es posible hallar el estilo,Error:",exception);
        }
        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
        LatLng tacna = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(tacna).title(nombre));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tacna,zoom));


        if (numer2==1){
            GroundOverlayOptions iconOverlay = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.mundo))
                    .position(tacna,100);

            mMap.addGroundOverlay(iconOverlay);
        }
        if (numer2==2){
            GroundOverlayOptions iconOverlay = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.satelite))
                    .position(tacna,100);

            mMap.addGroundOverlay(iconOverlay);
        }
        if (numer2==3){
            GroundOverlayOptions iconOverlay = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.montanas))
                    .position(tacna,100);

            mMap.addGroundOverlay(iconOverlay);
        }
        if (numer2==4){
            GroundOverlayOptions iconOverlay = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.drawable.planos))
                    .position(tacna,100);

            mMap.addGroundOverlay(iconOverlay);
        }


        setMapLongClick(mMap);
        setPoiClick(mMap);
        //AGREGANDO METODO
        enableMyLocation();
        setInfoWindowClickToPanorama(mMap);
    }


    private void setMapLongClick(final GoogleMap map){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()  {

            @Override
            public void onMapLongClick(LatLng latLng){

                String snippet = String.format(Locale.getDefault(),"Lat: %1$.5f , Long : %2$.5f",
                        latLng.latitude,latLng.longitude);
                map.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .position(latLng)
                        .title(getString(R.string.app_name))
                        .snippet(snippet));

                // map.addMarker(new MarkerOptions().position(latLng));
            }
        });
    }

    //Puntos de interes
    private void setPoiClick(final GoogleMap map){
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest pointOfInterest) {
                Marker poiMarker = map.addMarker(new MarkerOptions()
                        .position(pointOfInterest.latLng)
                        .title(pointOfInterest.name));
                poiMarker.setTag("poi");

                // poiMarker.getTag().toString();
                poiMarker.showInfoWindow();

            }
        });
    }


    private void enableMyLocation(){
        if(ContextCompat.checkSelfPermission(getBaseContext()
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        } else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    ,REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case REQUEST_LOCATION_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    enableMyLocation();
                }
                break;
        }

    }

    private void setInfoWindowClickToPanorama(GoogleMap map){
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.getTag() == "poi"){

                    //Toast.makeText(getApplicationContext(),"Hola si funka",Toast.LENGTH_SHORT).show();

                    StreetViewPanoramaOptions options = new StreetViewPanoramaOptions()
                            .position(marker.getPosition());
                    SupportStreetViewPanoramaFragment streetViewPanoramaFragment = SupportStreetViewPanoramaFragment.newInstance(options);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,streetViewPanoramaFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

}