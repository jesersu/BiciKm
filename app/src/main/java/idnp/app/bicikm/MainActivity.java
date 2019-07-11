package idnp.app.bicikm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //dasdfasdfasdf
        btnAceptar = findViewById(R.id.idbtn1);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent((getBaseContext()), MenuMainActivity.class));
                Intent a = new Intent(MainActivity.this,MenuMainActivity.class);
                startActivity(a);
            }
        });
    }
}
