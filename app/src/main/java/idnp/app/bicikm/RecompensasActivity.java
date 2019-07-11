package idnp.app.bicikm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class RecompensasActivity extends AppCompatActivity {

    private TextView infoTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensas);

        TextView title = findViewById(R.id.tituloRecompensas);
        title.setText("Activity Recompensas");

        infoTextView2 = (TextView) findViewById(R.id.infoTextView2);

        if(getIntent().getExtras() !=null){
            for(String key : getIntent().getExtras().keySet()){
                if(key.equals((String)"titulo")||key.equals((String)"descripción")) {
                    String value = getIntent().getExtras().getString(key);
                    infoTextView2.append("\n" + key + ": " + value);
                }
            }
        }

        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.getMenu().findItem(R.id.navigation_recompensas).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(RecompensasActivity.this,MenuMainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_estadisticas:
                        Intent b = new Intent(RecompensasActivity.this,EstadisticasActivity.class);
                        startActivity(b);
                        break;
                    case R.id.navigation_recompensas:
                        break;
                    case R.id.navigation_servicios:
                        Intent c = new Intent(RecompensasActivity.this,ServiciosActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
    }
}