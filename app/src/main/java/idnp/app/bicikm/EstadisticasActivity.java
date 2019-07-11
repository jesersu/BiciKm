package idnp.app.bicikm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class EstadisticasActivity extends AppCompatActivity {
    String[] array = new String[] {
            "Elemento 1"
            ,"Elemento 2"
            ,"Elemento 3"
            ,"Elemento 4"
            ,"Elemento 5"
            ,"Elemento 6"
    };
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
        // ListView
        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.listview_row, array);
        ListView mlistView = findViewById(R.id.list);
        mlistView.setAdapter(adapter);
    }
}
