package com.univerisity.rentify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.univerisity.Common.profile.Profile;
import com.univerisity.Common.utils.Activities;
import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOImages;
import com.univerisity.DataAccess.DAOs.DAOProperty;
import com.univerisity.Domain.models.Favorito;
import com.univerisity.Domain.models.Image;
import com.univerisity.Domain.models.Property;
import com.univerisity.Domain.services.ImageService;
import com.univerisity.Domain.services.PropertyService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.net.IDN;
import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    private  LinearLayout cardLayout;
    private FloatingActionButton btnAdd;
    private EditText txtSearch;
    private ImageButton btnFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardLayout = view.findViewById(R.id.list_home_card);
        btnAdd = view.findViewById(R.id.btn_add);
        txtSearch = view.findViewById(R.id.txt_search_property);
        btnFilter = (ImageButton) view.findViewById(R.id.btn_filter_property);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterProperty();
            }
        });


        if(Profile.isTipo())
            btnAdd.setVisibility(View.VISIBLE);
        else
            btnAdd.setVisibility(View.INVISIBLE);

        if(txtSearch.getText().toString().isEmpty()){
            getData();
        }

        return view;
    }

    private void getData(){
        cardLayout.removeAllViews();
        PropertyService service = new PropertyService();
        service.getAllProperties(new DAOProperty.PropertyListCallback() {
            @Override
            public void onSuccess(ArrayList<Property> properties) {
                for (Property p : properties){
                    createCard(p.getIdRaproperty(), p.getTitulo(), p.getDireccion(), p.getPrecio(), service, p);
                }

                if(cardLayout.getChildCount() <= 0)
                    createMessageText();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), Message.Error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filterProperty(){
        if(!txtSearch.getText().toString().isEmpty()){
            cardLayout.removeAllViews();
            saveHistory(txtSearch.getText().toString());
            PropertyService service = new PropertyService();
            service.propertiesSearch(txtSearch.getText().toString(), new DAOProperty.PropertyListCallback() {
                @Override
                public void onSuccess(ArrayList<Property> properties) {
                    for (Property p : properties){
                        createCard(p.getIdRaproperty(), p.getTitulo(), p.getDireccion(), p.getPrecio(), service, p);
                    }

                    if(cardLayout.getChildCount() <= 0)
                        createMessageText();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(getContext(), Message.Error, Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            getData();
        }
    }

    private void saveHistory(String text){
        SharedPreferences preferences = getContext().getSharedPreferences("history", Context.MODE_PRIVATE);
        SharedPreferences.Editor preferencesEdit = preferences.edit();

        ArrayList<String> filters = getHistory();

        String newFilter = text + "-" + new Date().toString();
        filters.add(newFilter);

        JSONArray jsonArray = new JSONArray(filters);
        preferencesEdit.putString("filters", jsonArray.toString());

        preferencesEdit.apply();
    }

    private ArrayList<String> getHistory() {
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return filters;
    }

    //Metodo para crear cardview de las propiedades
    private void createCard(int id, String title, String address, double price, PropertyService service, Property p){
        //Creamos el carview
        CardView cardView = new CardView(getContext());
        //Parametros principales del cardview
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 900);
        cardParams.bottomMargin = 15;
        cardView.setLayoutParams(cardParams);
        cardView.setId(id);
        cardView.setRadius(20);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.white)); // O cualquier color
        cardView.setContentPadding(10, 10, 10, 10);

        // Crear un LinearLayout dentro del CardView
        LinearLayout cardContentLayout = new LinearLayout(getContext());
        cardContentLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams content = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        cardContentLayout.setLayoutParams(content);

        // Crear un ImageView para la imagen principal
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.6f));
        imageView.setBackgroundColor(getResources().getColor(R.color.sky_blue_1));
        p.obtenerImagenes(new DAOImages.ImageListCallback() {
            @Override
            public void onSuccess(ArrayList<Image> images) {
                if(images.size() > 0){
                    if (images.get(0).getImagenUrl() != null && !images.get(0).getImagenUrl().isEmpty()) {
                        Glide.with(getContext())
                                .load(images.get(0).getImagenUrl())
                                .error(R.drawable.ic_no_image)
                                .into(imageView);
                    }
                    else{
                        Glide.with(getContext())
                                .load(R.drawable.ic_no_image)
                                .into(imageView);
                    }
                }
                else{
                    Glide.with(getContext())
                            .load(R.drawable.ic_no_image)
                            .into(imageView);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Glide.with(getContext())
                        .load(R.drawable.ic_no_image)
                        .into(imageView);
            }
        });



        // Crear otro LinearLayout para la información
        LinearLayout infoLayout = new LinearLayout(getContext());
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        infoLayout.setPadding(10, 10, 10, 10);

        //Constraint layout
        ConstraintLayout titleLayout = new ConstraintLayout(getContext());
        titleLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Crear TextViews
        //Textview para el titulo
        TextView titleText = new TextView(getContext());
        titleText.setId(View.generateViewId());
        titleText.setText(title);
        titleText.setTextSize(22);
        titleText.setTypeface(null, Typeface.BOLD);

        ImageButton fav = new ImageButton(getContext());
        fav.setId(id*10);
        fav.setBackground(null);
        Profile.getFavorites(new DAOProperty.FavoriteCallback() {
            @Override
            public void onSuccess(ArrayList<Favorito> favoriteLost) {
                fav.setImageResource(R.drawable.ic_favorite_outline);
                fav.setTag(R.drawable.ic_favorite_outline);
                for (Favorito f : favoriteLost){
                    if(f.getoProperty().getIdRaproperty() == id){
                        fav.setImageResource(R.drawable.ic_favorite);
                        fav.setTag(R.drawable.ic_favorite);
                    }
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                fav.setImageResource(R.drawable.ic_favorite_outline);
                fav.setTag(R.drawable.ic_favorite_outline);
            }
        });
        fav.setMinimumWidth(48);
        fav.setMaxHeight(48);

        titleLayout.addView(titleText);
        titleLayout.addView(fav);

        ConstraintLayout.LayoutParams titleParams = new ConstraintLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        titleParams.endToStart = fav.getId();
        titleParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        titleParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        titleParams.rightMargin = 10;
        titleText.setLayoutParams(titleParams);

        ConstraintLayout.LayoutParams favParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        favParams.startToEnd = titleText.getId();
        favParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        favParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        favParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        favParams.leftMargin = 10;
        fav.setLayoutParams(favParams);


        //Textview para la direccion
        TextView addressText = new TextView(getContext());
        addressText.setText(address);
        addressText.setTextSize(16);

        //Textview para el precio
        TextView priceText = new TextView(getContext());
        priceText.setText("S/. " + price);
        priceText.setTextSize(22);
        priceText.setTypeface(null, Typeface.BOLD);

        // Añadir elementos al layout de informacion
        infoLayout.addView(titleLayout);
        infoLayout.addView(addressText);
        infoLayout.addView(priceText);

        // Añadir ImageView y el LinearLayout con info al linear layout principal
        cardContentLayout.addView(imageView);
        cardContentLayout.addView(infoLayout);

        //Añadimos el linear layout principal al cardview
        cardView.addView(cardContentLayout);

        //Evento click del cardview
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailPropertyActivity.class);
                intent.putExtra("id", v.getId());
                startActivity(intent);
            }
        });

        //Evento clik de favorito
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView) v;
                if((int)iv.getTag() == R.drawable.ic_favorite_outline){
                    iv.setImageResource(R.drawable.ic_favorite);
                    iv.setTag(R.drawable.ic_favorite);

                    service.addFavority(Profile.getIdRauser(), (iv.getId() / (int)10), new DAOProperty.PropertyCallback(){
                        @Override
                        public void onSuccess(int id) {
                            Toast.makeText(getContext(), "Propiedad añadida a favoritos", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText (getContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    iv.setImageResource(R.drawable.ic_favorite_outline);
                    iv.setTag(R.drawable.ic_favorite_outline);

                    service.deleteFavorite(Profile.getIdRauser(), (iv.getId() / (int) 10), new DAOProperty.PropertyCallback() {
                        @Override
                        public void onSuccess(int id) {
                            Toast.makeText(getContext(), "Propiedad eimindad de favoritos", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        // Finalmente, añadir el CardView al contenedor principal del fragment
        cardLayout.addView(cardView);
    }

    //Metodo para crear de mensaje de aviso
    private void createMessageText(){
        //Poner propiedad de centrar componentes en el contenede¿or
        cardLayout.setGravity(Gravity.CENTER);

        //Crear textview
        TextView txt = new TextView(getContext());
        txt.setText("No hay propiedades registradas");
        txt.setTextSize(18);
        txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txt.setTextColor(getResources().getColor(R.color.sky_blue_2));

        //agregamos texto a layout
        cardLayout.addView(txt);
    }
}