package idnp.app.bicikm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuMainActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button inicio;
    private LocationRequest locationRequest;
    private boolean iniciado=false;
    private static final String TAG = MenuMainActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    //Punto
    LatLng puntoF=new LatLng(-33.8523341, 151.2106085);
    LatLng puntoI=new LatLng(-33.8523341, 151.2106085);
    LatLng punto=new LatLng(-33.8523341, 151.2106085);
    //Punto Temporal
    LatLng temp=new LatLng(-33.8523341, 151.2106085);

    //Marcadores

    private Marker ini= null;
    private Marker fin=null;
    private Marker recorridoM=null;
    private int contI=0;

    //Distancia
    Location loc1 = new Location("");
    Location loc2 = new Location("");

    float distanciaEnMetros=0;
    int limite=10;

    //Velocidad

    float velocidad;
    int tiempo =4;

    //Calorias

    int tiempoRecorridoSegundos=0;
    int tiempoRecorridoMinutos=0;
    int peso;
    double caloriasTotales=0;

    //Velocidad Media:

    double velocidadMedia=0;
    // The entry points to the Places API.
 //   private GeoDataClient mGeoDataClient;
 //   private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 18;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_estadisticas:
                        Intent a = new Intent(MenuMainActivity.this,EstadisticasActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_recompensas:
                        Intent b = new Intent(MenuMainActivity.this,RecompensasActivity.class);
                        startActivity(b);
                        break;
                    case R.id.navigation_servicios:
                        Intent c = new Intent(MenuMainActivity.this,ServiciosActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });

        ///MAPA

        // Retrieve location and camera position from saved instance state.
 /*       if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }*/
        //Boton de inicio de Recorrido

        // Retrieve the content view that renders the map.
       // setContentView(R.layout.activity_menu_main);



        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        inicio = (Button) findViewById(R.id.btnInicio);

        inicio.setOnClickListener(iniciarRecorrido);

        locationRequest = new LocationRequest();
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(2000);
        locationRequest.setInterval(4000);

    }
    private View.OnClickListener iniciarRecorrido = new View.OnClickListener() {
        public void onClick(View v) {

            if(iniciado==false ){
                if(contI==0){

                    ini = mMap.addMarker(new MarkerOptions()
                            .position(punto)
                            .title("Inicio")
                            .snippet("¡vamos por los premios!")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    inicio.setText("Finalizar");
                    iniciado=true;
                    contI++;
                }
                else  {
                     ini.setVisible(false);
                    //fin.remove();
                    //fin.setVisible(false);
                    fin.setVisible(false);

                    ini = mMap.addMarker(new MarkerOptions()
                            .position(punto)
                            .title("Inicio")
                            .snippet("¡vamos por los premios!")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    inicio.setText("Finalizar");
                    iniciado=true;
                    contI++;

                }

            }else{
                //  ini.setVisible(false);
              //  LatLng puntoF = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                fin = mMap.addMarker(new MarkerOptions()
                        .position(punto)
                        .title("Fin de Ruta")
                        .snippet("qué cansancio he!?")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                inicio.setText("Iniciar");
                iniciado=false;
                contI++;
            }

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PermissionChecker.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PermissionChecker.PERMISSION_GRANTED ) {
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    getDeviceLocation();

                    //inicio.setOnClickListener(iniciarRecorrido);
                    // Log.i("xxx", " Lat " + locationResult.getLastLocation().getLatitude() + " Long: " + locationResult.getLastLocation().getLongitude());
                }
            }, getMainLooper());

        }else  getLocationPermission();

    }
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();

                            punto=new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude());

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()),DEFAULT_ZOOM));

                            if (iniciado==true) {

                                if(temp.latitude==-33.8523341 && temp.longitude==151.2106085 ){
                                    recorridoM = mMap.addMarker(new MarkerOptions()
                                            .position(punto)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bici)));
                                    // Creamos en un temporal el ultimo punto
                                    temp = punto;
                                    Log.i("jjj","inicial");
                                }

                                else if(punto.latitude!=temp.latitude && punto.longitude!=temp.longitude ) {
                                    recorridoM = mMap.addMarker(new MarkerOptions()
                                            .position(punto)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bici)));
                                    // Creamos en un temporal el ultimo punto
                                    loc1.setLatitude(mLastKnownLocation.getLatitude());
                                    loc1.setLongitude(mLastKnownLocation.getLongitude());
                                    loc2.setLatitude(temp.latitude);
                                    loc2.setLongitude(temp.longitude);

                                    distanciaEnMetros+=loc1.distanceTo(loc2);
                                    //cada 10 metros actualizamos en la base de datos
                                    if(distanciaEnMetros>limite){
                                        //Enviar a la base de datos


                                        limite+=10;

                                    }
                                    //Velocidad
                                    velocidad=distanciaEnMetros/tiempo;
                                    //VelocidadMedia
                                    velocidadMedia=(velocidadMedia+velocidad)/2;
                                    //CALORIAS
                                    tiempoRecorridoSegundos+=4;
                                    if(tiempoRecorridoSegundos==60)
                                    {
                                        tiempoRecorridoMinutos+=1;
                                        tiempoRecorridoSegundos=0;
                                    }
                                    caloriasTotales+=getCalorias();

                                    Log.i("jjj","En recorrido::"+" distancia: "+distanciaEnMetros + " velocidad: "+ velocidad);
                                    temp=punto;
                                }

                                else{
                                    Log.i("jjj","no se ha movido");
                                }
                            }
                            Log.i("xxx","Lat: "+ mLastKnownLocation.getLatitude()+ " Lomg: "+mLastKnownLocation.getLongitude() );


                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    public double getCalorias(){
        double kiloCalorias=0;
        kiloCalorias=peso*(distanciaEnMetros/1000)*0.0175;
        return kiloCalorias;
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}