package idnp.app.bicikm;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class EstadisticasMapa extends AppCompatActivity {
    TextView idTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_mapa);

        idTxt = findViewById(R.id.textView1);
        String stringId = getIntent().getStringExtra("id");
        idTxt.setText(stringId);
    }
}
