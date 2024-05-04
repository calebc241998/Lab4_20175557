package com.example.lab4_20175557.fragmentos;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20175557.R;
import com.example.lab4_20175557.adapters.LocationAdapter;
import com.example.lab4_20175557.dto.Location;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeoFragment extends Fragment {
    private EditText etCiudad;
    private Button btnBuscar;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geo, container, false);
        etCiudad = view.findViewById(R.id.etCiudad);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        recyclerView = view.findViewById(R.id.recyclerViewGeolocalizacion);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LocationAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        btnBuscar.setOnClickListener(v -> buscarGeolocalizacion(etCiudad.getText().toString()));
        return view;
    }

    private void buscarGeolocalizacion(String ciudad) {
        executorService.execute(() -> {
            try {
                URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + ciudad + "&limit=1&appid=8dd6fc3be19ceb8601c2c3e811c16cf1");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String response = reader.readLine();
                    reader.close();
                    Location[] locations = new Gson().fromJson(response, Location[].class);
                    if (locations.length > 0) {
                        getActivity().runOnUiThread(() -> {
                            adapter.updateLocations(locations[0]);
                        });
                    }
                } else {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    });
                }
                connection.disconnect();
            } catch (IOException e) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
