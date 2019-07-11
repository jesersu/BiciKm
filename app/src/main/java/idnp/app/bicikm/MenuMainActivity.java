package idnp.app.bicikm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.widget.TextView;

public class MenuMainActivity extends AppCompatActivity {
   /* private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent myIntent1 = new Intent(MenuMainActivity.this, MenuMainActivity.class);
                    startActivity(myIntent1);
                    return true;
                case R.id.navigation_estadisticas:
                    Intent myIntent2 = new Intent(MenuMainActivity.this, EstadisticasActivity.class);
                    startActivity(myIntent2);
                    return true;
                case R.id.navigation_recompensas:
                    Intent myIntent3 = new Intent(MenuMainActivity.this, RecompensasActivity.class);
                    startActivity(myIntent3);
                    return true;
                case R.id.navigation_servicios:
                    Intent myIntent4 = new Intent(MenuMainActivity.this, ServiciosActivity.class);
                    startActivity(myIntent4);
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        TextView title = findViewById(R.id.tituloMainMenu);
        title.setText("Home");

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

    }
}
