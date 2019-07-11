package idnp.app.bicikm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.List;

import idnp.app.bicikm.Objetos.Recorrido;

public class EstadisticasActivity extends AppCompatActivity {
    ListView listaDet;
    List<Recorrido> listaRecorridos = new ArrayList<>();
    String stringId = "salazarmariot@gmail.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        // Navigation
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.getMenu().findItem(R.id.navigation_estadisticas).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(EstadisticasActivity.this,MenuMainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_estadisticas:
                        break;
                    case R.id.navigation_recompensas:
                        Intent b = new Intent(EstadisticasActivity.this,RecompensasActivity.class);
                        startActivity(b);
                        break;
                    case R.id.navigation_servicios:
                        Intent c = new Intent(EstadisticasActivity.this,ServiciosActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
        // Inicializar listView de Estadisticas
        listaDet = findViewById(R.id.list);
        MiTask3 task = new MiTask3();
        String url = "https://bicikm.000webhostapp.com/buscarRecorridoById.php?id=" + stringId;

        task.execute(url);
    }
    class MiTask3 extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(String... strings) {
            String result = listar(strings[0]);
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray datos = new JSONArray(s);
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject jsonObject = datos.getJSONObject(i);
                    Recorrido recorrido = new Recorrido();
                    recorrido.setUsuario(jsonObject.getString("RecorridoUsuario"));
                    recorrido.setFecha(jsonObject.getString("RecorridoFecha"));
                    recorrido.setLatitud(jsonObject.getString("RecorridoLatitud"));
                    recorrido.setLongitud(jsonObject.getString("RecorridoLongitud"));
                    listaRecorridos.add(recorrido);
                }
            } catch (Exception e) {
            }
            listaDet.setAdapter(new EstadisticasAdaptador(listaRecorridos, getApplicationContext()));
        }
    }

    public String listar(String direccion) {
        URL url = null;
        String linea = "";
        int respuesta = 0;
        StringBuilder result = null;

        try {
            url = new URL(direccion);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setDoOutput(true);
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
}
