package com.univerisity.rentify;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.univerisity.Common.profile.Profile;
import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOImages;
import com.univerisity.DataAccess.DAOs.DAOProperty;
import com.univerisity.Domain.models.Favorito;
import com.univerisity.Domain.models.Image;
import com.univerisity.Domain.models.Property;
import com.univerisity.Domain.services.PropertyService;

import java.io.File;
import java.util.ArrayList;

public class MyPropertiesActivity extends AppCompatActivity {

    LinearLayout cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_properties);

        cardLayout = findViewById(R.id.list_my_property_card);

        getData();
    }

    public void btnBackMyProperties_Click(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getData(){
        PropertyService service = new PropertyService();
        service.myProperties(Profile.getIdRauser(), new DAOProperty.PropertyListCallback() {
            @Override
            public void onSuccess(ArrayList<Property> properties) {
                for (Property p : properties){
                    createCard(p.getIdRaproperty(), p.getTitulo(), p.getDireccion(), p.getPrecio(), "", service, !p.isEstado(), p);
                }

                if(cardLayout.getChildCount() <= 0)
                    createMessageText();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(MyPropertiesActivity.this, Message.Error, Toast.LENGTH_LONG).show();
            }
        });
    }

    //Metodo para crear cardview de las propiedades
    private void createCard(int id, String title, String address, double price, String urlImage, PropertyService service, boolean state, Property p){
        //Creamos el carview
        CardView cardView = new CardView(MyPropertiesActivity.this);
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
        LinearLayout cardContentLayout = new LinearLayout(MyPropertiesActivity.this);
        cardContentLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams content = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        cardContentLayout.setLayoutParams(content);

        // Crear un ImageView para la imagen principal
        ImageView imageView = new ImageView(MyPropertiesActivity.this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.6f));
        imageView.setBackgroundColor(getResources().getColor(R.color.sky_blue_1));
        p.obtenerImagenes(new DAOImages.ImageListCallback() {
            @Override
            public void onSuccess(ArrayList<Image> images) {
                if(images.size() > 0){
                    if (images.get(0).getImagenUrl() != null && !images.get(0).getImagenUrl().isEmpty()) {
                        Glide.with(MyPropertiesActivity.this)
                                .load(images.get(0).getImagenUrl())
                                .error(R.drawable.ic_no_image)
                                .into(imageView);
                    }
                    else{
                        Glide.with(MyPropertiesActivity.this)
                                .load(R.drawable.ic_no_image)
                                .into(imageView);
                    }
                }
                else{
                    Glide.with(MyPropertiesActivity.this)
                            .load(R.drawable.ic_no_image)
                            .into(imageView);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Glide.with(MyPropertiesActivity.this)
                        .load(R.drawable.ic_no_image)
                        .into(imageView);
            }
        });

        // Crear otro LinearLayout para la información
        LinearLayout infoLayout = new LinearLayout(MyPropertiesActivity.this);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.4f));
        infoLayout.setPadding(10, 10, 10, 10);

        //Constraint layout
        ConstraintLayout titleLayout = new ConstraintLayout(MyPropertiesActivity.this);
        titleLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Crear TextViews
        //Textview para el titulo
        TextView titleText = new TextView(MyPropertiesActivity.this);
        titleText.setId(View.generateViewId());
        titleText.setText(title);
        titleText.setTextSize(22);
        titleText.setTypeface(null, Typeface.BOLD);

        ImageButton fav = new ImageButton(MyPropertiesActivity.this);
        fav.setId(id*10);
        fav.setBackground(null);
        fav.setImageResource(R.drawable.ic_edit);
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
        TextView addressText = new TextView(MyPropertiesActivity.this);
        addressText.setText(address);
        addressText.setTextSize(16);

        ConstraintLayout bottomLayout = new ConstraintLayout(MyPropertiesActivity.this);
        titleLayout.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //Textview para el precio
        TextView priceText = new TextView(MyPropertiesActivity.this);
        priceText.setText("S/. " + price);
        priceText.setTextSize(22);
        priceText.setTypeface(null, Typeface.BOLD);

        Switch rentar = new Switch(MyPropertiesActivity.this);
        rentar.setChecked(state);
        rentar.setText("Rentado: ");

        bottomLayout.addView(priceText);
        bottomLayout.addView(rentar);

        ConstraintLayout.LayoutParams priceParam = new ConstraintLayout.LayoutParams(
                0,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        priceParam.startToStart= ConstraintLayout.LayoutParams.PARENT_ID;
        priceParam.endToStart = rentar.getId();
        priceParam.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        priceParam.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        priceParam.leftMargin = 10;
        rentar.setLayoutParams(priceParam);

        ConstraintLayout.LayoutParams rentarParam = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        rentarParam.startToEnd = priceText.getId();
        rentarParam.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        rentarParam.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        rentarParam.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        rentarParam.leftMargin = 10;
        rentar.setLayoutParams(rentarParam);

        // Añadir elementos al layout de informacion
        infoLayout.addView(titleLayout);
        infoLayout.addView(addressText);
        infoLayout.addView(bottomLayout);

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
                Intent intent = new Intent(MyPropertiesActivity.this, AddPropertyActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("idProperty", id);
                startActivity(intent);
            }
        });

        //Evento change rentar
        rentar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                service.rentProperty(id, new DAOProperty.PropertyCallback() {
                    @Override
                    public void onSuccess(int id) {
                        Toast.makeText(MyPropertiesActivity.this, isChecked ? "Propiedad rentada" : "Propiedad disponible", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(MyPropertiesActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Finalmente, añadir el CardView al contenedor principal del fragment
        cardLayout.addView(cardView);
    }

    private void createMessageText(){
        //Poner propiedad de centrar componentes en el contenede¿or
        cardLayout.setGravity(Gravity.CENTER);

        //Crear textview
        TextView txt = new TextView(MyPropertiesActivity.this);
        txt.setText("No hay propiedades registradas");
        txt.setTextSize(18);
        txt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        txt.setTextColor(getResources().getColor(R.color.sky_blue_2));

        //agregamos texto a layout
        cardLayout.addView(txt);
    }

}