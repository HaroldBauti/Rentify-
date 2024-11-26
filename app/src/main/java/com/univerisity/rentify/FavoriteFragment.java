package com.univerisity.rentify;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;
import com.univerisity.Common.profile.Profile;
import com.univerisity.Common.utils.Activities;
import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOImages;
import com.univerisity.DataAccess.DAOs.DAOProperty;
import com.univerisity.Domain.models.Favorito;
import com.univerisity.Domain.models.Image;
import com.univerisity.Domain.models.Property;
import com.univerisity.Domain.services.PropertyService;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private LinearLayout content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        content = view.findViewById(R.id.ly_favorite);
        getData();

        return view;
    }

    private void getData(){
        PropertyService service = new PropertyService();
        service.getFavorities(Profile.getIdRauser(), new DAOProperty.FavoriteCallback() {
            @Override
            public void onSuccess(ArrayList<Favorito> favoriteLost) {
                for (Favorito p : favoriteLost){
                    createCard(p.getoProperty().getIdRaproperty(), p.getoProperty().getTitulo(), p.getoProperty().getTitulo(), String.valueOf(p.getoProperty().getPrecio()), p.getoProperty());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createCard(int id, String title, String description, String price, Property p){
        // Crear el CardView
        CardView cardView = new CardView(getContext());
        CardView.LayoutParams cardParams = new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getContext().getResources().getDisplayMetrics().density * 120 // 120sp
        );
        cardView.setLayoutParams(cardParams);
        cardView.setRadius(10 * getContext().getResources().getDisplayMetrics().density); // 10sp
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setId(id);

        // Crear el LinearLayout
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Crear el TextView de título
        TextView titleTextView = new TextView(getContext());
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.3f
        );
        titleTextView.setLayoutParams(titleParams);
        titleTextView.setText(title);
        titleTextView.setTextSize(20);
        titleTextView.setTextColor(Color.BLACK);
        titleTextView.setTypeface(null, android.graphics.Typeface.BOLD);

        // Agregar el título al LinearLayout
        linearLayout.addView(titleTextView);

        // Crear el ConstraintLayout
        ConstraintLayout constraintLayout = new ConstraintLayout(getContext());
        LinearLayout.LayoutParams constraintParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.7f
        );
        constraintLayout.setLayoutParams(constraintParams);

        // Crear el ImageView
        ImageView imageView = new ImageView(getContext());
        imageView.setId(View.generateViewId());
        ConstraintLayout.LayoutParams imageParams = new ConstraintLayout.LayoutParams(
                (int) (70 * getContext().getResources().getDisplayMetrics().density),
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        imageParams.setMargins(0, 0, 5, 0);
        imageView.setLayoutParams(imageParams);
        imageView.setBackgroundColor(Color.parseColor("#ADD8E6")); // sky_blue_2
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

        // Agregar el ImageView al ConstraintLayout
        constraintLayout.addView(imageView);

        // Crear el LinearLayout secundario
        LinearLayout innerLinearLayout = new LinearLayout(getContext());
        innerLinearLayout.setId(View.generateViewId());
        ConstraintLayout.LayoutParams innerLayoutParams = new ConstraintLayout.LayoutParams(
                0, ConstraintLayout.LayoutParams.MATCH_PARENT
        );
        innerLayoutParams.setMargins(5, 0, 5, 0);
        innerLinearLayout.setLayoutParams(innerLayoutParams);
        innerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        // Crear el TextView de precio
        TextView priceTextView = new TextView(getContext());
        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        priceTextView.setLayoutParams(priceParams);
        priceTextView.setPadding(10, 5, 10, 5);
        priceTextView.setText("S/. " + price);
        priceTextView.setTextSize(16);

        // Crear el TextView de descripción
        TextView descriptionTextView = new TextView(getContext());
        descriptionTextView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        descriptionTextView.setText(description);
        descriptionTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        descriptionTextView.setTextSize(16);

        // Agregar precio y descripción al innerLinearLayout
        innerLinearLayout.addView(priceTextView);
        innerLinearLayout.addView(descriptionTextView);

        // Agregar innerLinearLayout al ConstraintLayout
        constraintLayout.addView(innerLinearLayout);

        // Crear el ImageButton
        ImageButton favoriteButton = new ImageButton(getContext());
        favoriteButton.setId(View.generateViewId());
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                (int) (48 * getContext().getResources().getDisplayMetrics().density),
                (int) (48 * getContext().getResources().getDisplayMetrics().density)
        );
        buttonParams.setMargins(5, 0, 0, 0);
        favoriteButton.setLayoutParams(buttonParams);
        favoriteButton.setBackgroundColor(Color.WHITE);
        favoriteButton.setImageResource(R.drawable.ic_favorite);
        favoriteButton.setId(id * 10);

        // Agregar ImageButton al ConstraintLayout
        constraintLayout.addView(favoriteButton);

        // Configurar restricciones del ConstraintLayout
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(imageView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        constraintSet.connect(innerLinearLayout.getId(), ConstraintSet.START, imageView.getId(), ConstraintSet.END);
        constraintSet.connect(innerLinearLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(innerLinearLayout.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        constraintSet.connect(innerLinearLayout.getId(), ConstraintSet.END, favoriteButton.getId(), ConstraintSet.START);

        constraintSet.connect(favoriteButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(favoriteButton.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(favoriteButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        constraintSet.applyTo(constraintLayout);

        // Agregar el ConstraintLayout al LinearLayout
        linearLayout.addView(constraintLayout);

        //Evento click
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailPropertyActivity.class);
                intent.putExtra("id", v.getId());
                startActivity(intent);
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idDelete = v.getId() / (int)10;
                Message.showDialog(v.getContext(),
                        "Información",
                        "¿Estas seguro que deseas quitar este elemento de favoritos?",
                        new Runnable() {
                            @Override
                            public void run() {
                                PropertyService service = new PropertyService();
                                service.deleteFavorite(Profile.getIdRauser(), idDelete, new DAOProperty.PropertyCallback() {
                                    @Override
                                    public void onSuccess(int id) {
                                        Toast.makeText(getContext(), "Propiedad eliminada", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(String errorMessage) {
                                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
            }
        });

        // Agregar el LinearLayout al CardView
        cardView.addView(linearLayout);

        content.addView(cardView);
    }
}