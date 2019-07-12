package idnp.app.bicikm.Servicios;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.TextView;

import idnp.app.bicikm.Estadisticas.EstadisticasActivity;
import idnp.app.bicikm.Inicio.MenuMainActivity;
import idnp.app.bicikm.R;
import idnp.app.bicikm.Recompensas.RecompensasActivity;

public class ServiciosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        TextView title = findViewById(R.id.tituloServicios);
        title.setText("Activity Servicios");

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.getMenu().findItem(R.id.navigation_servicios).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(ServiciosActivity.this, MenuMainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_estadisticas:
                        Intent b = new Intent(ServiciosActivity.this, EstadisticasActivity.class);
                        startActivity(b);
                        break;
                    case R.id.navigation_recompensas:
                        Intent c = new Intent(ServiciosActivity.this, RecompensasActivity.class);
                        startActivity(c);
                        break;
                    case R.id.navigation_servicios:
                        break;
                }
                return false;
            }
        });
    }
}