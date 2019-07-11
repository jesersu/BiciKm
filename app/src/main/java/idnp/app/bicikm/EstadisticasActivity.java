package idnp.app.bicikm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.TextView;

public class EstadisticasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        TextView title = findViewById(R.id.tituloEstadisticas);
        title.setText("Activity Estadisticas");

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
    }
}
