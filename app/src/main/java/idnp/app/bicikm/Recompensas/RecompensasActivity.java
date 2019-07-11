package idnp.app.bicikm.Recompensas;

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
import idnp.app.bicikm.Servicios.ServiciosActivity;

public class RecompensasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensas);

        TextView title = findViewById(R.id.tituloRecompensas);
        title.setText("Activity Recompensas");

        BottomNavigationView navigation = findViewById(R.id.nav_view);
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
    }
}