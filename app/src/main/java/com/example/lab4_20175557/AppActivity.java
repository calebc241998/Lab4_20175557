package com.example.lab4_20175557;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.lab4_20175557.fragmentos.ClimaFragment;
import com.example.lab4_20175557.fragmentos.GeoFragment;

public class AppActivity extends AppCompatActivity {
    private Fragment geolocalizacionFragment;
    private Fragment climaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_inicio);

        geolocalizacionFragment = new GeoFragment();
        climaFragment = new ClimaFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, geolocalizacionFragment)
                .commit();
    }

    public void mostrarGeolocalizacion(View view) {
        mostrarFragmento(geolocalizacionFragment);
    }

    public void mostrarClima(View view) {
        mostrarFragmento(climaFragment);
    }

    private void mostrarFragmento(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
