package idnp.app.bicikm.Recompensas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

import idnp.app.bicikm.Estadisticas.EstadisticasActivity;
import idnp.app.bicikm.Inicio.MenuMainActivity;
import idnp.app.bicikm.Objetos.Recompensa;
import idnp.app.bicikm.R;
import idnp.app.bicikm.Servicios.ServiciosActivity;

public class RecompensasActivity extends AppCompatActivity {
    ListView listaDet;
    List<Recompensa> listaRecompensas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensas);

        BottomNavigationView navigation = findViewById(R.id.nav_viewrecompensas);
        navigation.getMenu().findItem(R.id.navigation_recompensas).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(RecompensasActivity.this, MenuMainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_estadisticas:
                        Intent b = new Intent(RecompensasActivity.this, EstadisticasActivity.class);
                        startActivity(b);
                        break;
                    case R.id.navigation_recompensas:
                        break;
                    case R.id.navigation_servicios:
                        Intent c = new Intent(RecompensasActivity.this, ServiciosActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
        // ListView Recompensas
        listaDet = findViewById(R.id.listrecompensas);
        RecompensasActivity.MiTaskRecompensa task = new RecompensasActivity.MiTaskRecompensa();
        String url = "https://bicikm.000webhostapp.com/buscarPremio.php";
        task.execute(url);
    }
    class MiTaskRecompensa extends AsyncTask<String, Void, String> {

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
                    Recompensa recompensa = new Recompensa();
                    recompensa.setTitulo(jsonObject.getString("PremioTitulo"));
                    recompensa.setEmpresa(jsonObject.getString("PremioEmpresa"));
                    recompensa.setDetalle(jsonObject.getString("PremioDetalle"));
                    recompensa.setFoto(jsonObject.getString("PremioFoto"));
                    recompensa.setCosto(jsonObject.getString("PremioCosto"));
                    listaRecompensas.add(recompensa);
                }
            } catch (Exception e) {
            }
            listaDet.setAdapter(new RecompensaAdapter(listaRecompensas, getApplicationContext()));
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