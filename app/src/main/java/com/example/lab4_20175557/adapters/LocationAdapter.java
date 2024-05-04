package com.example.lab4_20175557.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20175557.R;
import com.example.lab4_20175557.dto.Location;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<Location> locations;

    public LocationAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.ciudadTextView.setText(location.getName());
        holder.latitudTextView.setText(String.format("Lat: %s", location.getLatitude()));
        holder.longitudTextView.setText(String.format("Lon: %s", location.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        public TextView ciudadTextView, latitudTextView, longitudTextView;

        public LocationViewHolder(View view) {
            super(view);
            ciudadTextView = view.findViewById(R.id.ciudad_text_view);
            latitudTextView = view.findViewById(R.id.latitud_text_view);
            longitudTextView = view.findViewById(R.id.longitud_text_view);
        }
    }
}
