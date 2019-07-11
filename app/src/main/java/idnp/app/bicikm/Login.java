package idnp.app.bicikm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private TextView titulo;

    private Typeface estilo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String fuente="fuentes/title.otf";
        this.estilo=Typeface.createFromAsset(getAssets(),fuente);
        titulo=(TextView) findViewById(R.id.bici);
        titulo.setTypeface(estilo);

    }
}
