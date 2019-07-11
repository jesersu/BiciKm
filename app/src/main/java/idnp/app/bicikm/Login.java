package idnp.app.bicikm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import idnp.app.bicikm.Objetos.Usuario;

public class Login extends AppCompatActivity {

    TextView titulo;
    private Typeface estilo;
    Button ingresar;
    EditText usuTxt, passTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String fuente="fuentes/title.otf";
        this.estilo=Typeface.createFromAsset(getAssets(),fuente);
        titulo=(TextView)findViewById(R.id.titel);

        usuTxt = (EditText) findViewById(R.id.userTxt);
        passTxt = (EditText) findViewById(R.id.contraTxt);

        ingresar = (Button) findViewById(R.id.ingresarBTN);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usuTxt.length() == 0 || passTxt.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Todos los campos deben estar llenos.", Toast.LENGTH_LONG).show();
                } else {
                    consultar(usuTxt.getText().toString(), passTxt.getText().toString());
                }
            }
        });

    }

    class MiTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... strings) {
            String result = enviarDatosLogin(strings[0]);
            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int estatus = obtDatosJSON(s);
            Usuario user = new Usuario();
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            if (estatus == 0) {
                Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos", Toast.LENGTH_LONG).show();
            } else {
                try {

                    JSONArray datos = new JSONArray(s);
                    JSONObject jsonObject = datos.getJSONObject(0);
                    user.setId(jsonObject.getString("UsuarioId"));
                    user.setNombre(jsonObject.getString("UsuarioNombre"));
                    user.setApellido(jsonObject.getString("UsuarioApellido"));
                    user.setPuntos(jsonObject.getString("UsuarioPuntos"));
                    user.setToken(jsonObject.getString("UsuarioToken"));

                } catch (Exception e) {

                }
                Intent intent = new Intent(Login.this, MenuMainActivity.class);
                intent.putExtra("usuarioId", user.getId());
                intent.putExtra("usuarioToken",user.getToken());
                startActivity(intent);
            }


        }
    }


    public void consultar(String usuario, String contra) {
        MiTask task = new MiTask();
        String url = "https://bicikm.000webhostapp.com/validar.php?usuario=" + usuario + "&contra=" + contra;
        Log.i("url",url);
        task.execute(url);
    }

    public String enviarDatosLogin(String direccion) {
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

    public int obtDatosJSON(String respuesta) {
        int res = 0;

        try {
            JSONArray datos = new JSONArray(respuesta);
            if (datos.length() > 0) {
                res = 1;
            }
        } catch (Exception e) {

        }

        return res;
    }
}
