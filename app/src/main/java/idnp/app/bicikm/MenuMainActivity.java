package idnp.app.bicikm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.AsyncTask;
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

import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MenuMainActivity extends AppCompatActivity implements OnMapReadyCallback {


    String id="salazarmariot@gmail.com";
    //String id;
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
    int peso=63;
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

    //Fecha

    String fecha;
    SimpleDateFormat dateFormat;

    //Hora
    Time horaMinutoSegundo;
    String h;
    String m;
    String s;

    //Fecha y hora

    String fechaYHora;

    //Texto de Velociadad
    TextView txt_vel;
    TextView txt_velMed;
    TextView txt_dist;
    TextView txt_cal;
    TextView txt_time;

    //Usuario

    String token;

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

        //ID DE USUARIO
        //id = getIntent().getStringExtra("usuarioId");
        token = getIntent().getStringExtra("usuarioToken");

        //Estadistica:
        txt_vel=findViewById(R.id.txt_vel);
        txt_cal=findViewById(R.id.txt_calorias);
        txt_velMed=findViewById(R.id.txt_velMed);
        txt_dist=findViewById(R.id.txt_dist);
        txt_time=findViewById(R.id.txt_time);
        //Fecha

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        fecha = (String)dateFormat.format(date);
        Log.i("jjj","fecha: "+fecha);

        //hora


        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        horaMinutoSegundo=today;
        /*int hora = today.hour;
        int min = today.minute;
        int seg = today.second;
        h = String.valueOf(hora);
        m = String.valueOf(min);
        s = String.valueOf(seg);
        Log.i("jjj","hora: "+hora+"minuto: "+m+"s:"+s);*/


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
                txt_vel.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC);
                txt_velMed.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC);
                txt_dist.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC);
                txt_cal.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC);
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

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude())));

                            if (iniciado==true) {

                                if(temp.latitude==-33.8523341 && temp.longitude==151.2106085 ){
                                    recorridoM = mMap.addMarker(new MarkerOptions()
                                            .position(punto)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bici2)));
                                    // Creamos en un temporal el ultimo punto
                                    temp = punto;
                                    Log.i("jjj","inicial");
                                }

                                else if(punto.latitude!=temp.latitude && punto.longitude!=temp.longitude ) {
                                    recorridoM = mMap.addMarker(new MarkerOptions()
                                            .position(punto)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bici2)));
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
                                    //Hora
                                    horaMinutoSegundo.setToNow();
                                    int hora = horaMinutoSegundo.hour;
                                    int min = horaMinutoSegundo.minute;
                                    int seg = horaMinutoSegundo.second;
                                    h = String.valueOf(hora);
                                    m = String.valueOf(min);
                                    s = String.valueOf(seg);
                                    Log.i("jjj","hora: "+hora+"minuto: "+m+"s:"+s);
                                    fechaYHora=fecha+" "+h+":"+m+":"+s;


                                    Log.i("jjj","En recorrido::"+" distancia: "+distanciaEnMetros + " velocidad: "+ velocidad);
                                    temp=punto;
                                    //Estadistica
                                    String velo=String.valueOf(velocidad);
                                    String dts=String.valueOf(distanciaEnMetros);
                                    String vm=String.valueOf(velocidadMedia);
                                    String cal=String.valueOf(getCalorias());
                                    String tiempo=String.valueOf(h)+":"+String.valueOf(m)+":"+String.valueOf(s);

                                    txt_vel.setText("Vel. "+velo.substring(0,4)+" m/s");
                                    txt_dist.setText("Dist. "+dts.substring(0,3)+" m");
                                    txt_velMed.setText("Vel. Med. "+vm.substring(0,4)+" m/s");
                                    txt_cal.setText("Vel. "+cal.substring(0,3)+" Kal");
                                    txt_time.setText("Tiempo: "+tiempo);




                                    //Enviando Datos

                                    String latitud = String.valueOf(mLastKnownLocation.getLatitude());
                                    String longitud = String.valueOf(mLastKnownLocation.getLongitude());
                                    String dist=String.valueOf(distanciaEnMetros);
                                    //dist="100";

                                    guardarLocacion( id, fechaYHora.toString(), latitud, longitud,dist);

                                }

                                else{
                                    Log.i("jjj","no se ha movido");
                                } }
                            Log.i("xxx","Lat: "+ mLastKnownLocation.getLatitude()+ " Lomg: "+mLastKnownLocation.getLongitude() );
                          //  Log.i("jjj","fecha: "+ fecha);

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
        kiloCalorias=peso*(distanciaEnMetros/10);
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


    class MiTask5 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = enviarDatosRegistro(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String stat = obtDatosJSON(s);
            if (stat.equals("0")) {
                Toast.makeText(getApplicationContext(), "Error al guardar datos", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "actualiza", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void guardarLocacion(String id, String fecha, String latitud, String longitud , String dist) {
        MiTask5 task = new MiTask5();
        //String url = "guardarDatos.php?id=" + id + "&fecha=" + fecha + "&latitud=" + latitud + "&longitud=" + longitud + "&velocidad=14&angulo=90 ";
       // String url="http://jashin.orgfree.com/Examen/guardarDatos.php?id="+id+"&fecha="+fecha+"&latitud="+latitud+"&longitud="+longitud+"&velocidad=12&angulo=12";
        String url="http://bicikm.000webhostapp.com/registrarRecorrido.php?id="+id+"&latitud="+latitud+"&longitud="+longitud+"&fecha="+fecha+"&distancia="+dist;


        task.execute(url);
        //enviarDatosRegistro(url);

    }

    public String enviarDatosRegistro(String direccion) {

        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder result = null;

        try {
            url = new URL(direccion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoOutput(true);
            conexion.setDoInput(true);
            respuesta = conexion.getResponseCode();//200, 404
            result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conexion.getInputStream()); //traemos la rpta
                BufferedReader reader = new BufferedReader(new InputStreamReader(in)); //leemos la rpta

                while ((linea = reader.readLine()) != null) {
                    result.append(linea);
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public String obtDatosJSON(String respuesta) {
        String res = "";

        try {
            JSONArray datos = new JSONArray(respuesta);
            JSONObject jsonObject = datos.getJSONObject(0);
            res = (jsonObject.getString("estatus"));
        } catch (Exception e) {

        }

        return res;
    }

}
