package com.example.fynd_proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

public class ParadaAdapter extends ArrayAdapter<Map<String, Object>> {

    public ParadaAdapter(Context context, List<Map<String, Object>> paradas) {
        super(context, 0, paradas);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Map<String, Object> Parada = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_menu_parada, parent, false);
        }

        TextView liv_paradas = convertView.findViewById(R.id.lv_paradas);

        if (Parada != null && Parada.containsKey("nomParada")) {
            liv_paradas.setText(Parada.get("nomParada").toString());
        }

        return convertView;
    }


    public void addParada(Map<String, Object> Parada) {
        add(Parada);
    }
}
