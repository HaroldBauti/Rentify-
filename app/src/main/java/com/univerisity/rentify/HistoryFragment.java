package com.univerisity.rentify;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private LinearLayout lyHistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        lyHistory = view.findViewById(R.id.ly_history);
        getHistory();
        return view;
    }

    private void getHistory(){
        // Obtener las preferencias
        SharedPreferences preferences = getContext().getSharedPreferences("history", Context.MODE_PRIVATE);

        // Obtener el string JSON guardado
        String json = preferences.getString("filters", null);

        ArrayList<String> filters = new ArrayList<>();
        if (json != null) {
            try {
                // Convertir el string JSON a una lista
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    filters.add(jsonArray.getString(i));
                }

                for (String txt : filters){
                    createCardView(txt.split("-")[0]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public void createCardView(String title) {
        // Crear el CardView
        CardView cardView = new CardView(getContext());
        cardView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (50 * getContext().getResources().getDisplayMetrics().density) // Altura de 50sp
        ));
        cardView.setRadius(10 * getContext().getResources().getDisplayMetrics().density); // Esquinas redondeadas

        // Crear el LinearLayout
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(
                (int) (10 * getContext().getResources().getDisplayMetrics().density), // Padding horizontal
                (int) (5 * getContext().getResources().getDisplayMetrics().density),  // Padding vertical superior
                (int) (10 * getContext().getResources().getDisplayMetrics().density), // Padding horizontal
                (int) (5 * getContext().getResources().getDisplayMetrics().density)   // Padding vertical inferior
        );
        linearLayout.setGravity(Gravity.CENTER_VERTICAL); // Centrar verticalmente

        // Crear el TextView
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (30 * getContext().getResources().getDisplayMetrics().density) // Altura de 30sp
        ));
        textView.setText(title);
        textView.setTextSize(20); // Tamaño de texto en sp
        textView.setTypeface(null, android.graphics.Typeface.BOLD); // Estilo de texto negrita
        textView.setTextColor(Color.BLACK); // Color del texto
        textView.setGravity(Gravity.CENTER_VERTICAL); // Alinear verticalmente
        ((LinearLayout.LayoutParams) textView.getLayoutParams()).setMargins(
                0, 0, 0, (int) (5 * getContext().getResources().getDisplayMetrics().density) // Margen inferior
        );

        // Agregar el TextView al LinearLayout
        linearLayout.addView(textView);

        // Agregar el LinearLayout al CardView
        cardView.addView(linearLayout);

        // Agregar a historial
        lyHistory.addView(cardView);
    }
}