package idnp.app.bicikm;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class MenuMainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        HistorialFragment.OnFragmentInteractionListener,
        RecompensasFragment.OnFragmentInteractionListener,
        NotificationsFragment.OnFragmentInteractionListener{
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // mTextMessage.setText(R.string.title_home);
                    HomeFragment homeFragment = new HomeFragment();
                    openFragment(homeFragment);
                    return true;
                case R.id.navigation_historial:
                    // mTextMessage.setText(R.string.title_historial);
                    HistorialFragment historialFragment = new HistorialFragment();
                    openFragment(historialFragment);
                    return true;
                case R.id.navigation_recompensas:
                    // mTextMessage.setText(R.string.title_recompensas);
                    RecompensasFragment recompensasFragment = new RecompensasFragment();
                    openFragment(recompensasFragment);
                    return true;
                case R.id.navigation_notificaciones:
                    // mTextMessage.setText(R.string.title_notificaciones);
                    NotificationsFragment notificationsFragment = new NotificationsFragment();
                    openFragment(notificationsFragment);
                    return true;
            }
            return false;
        }
    };
    private void openFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
