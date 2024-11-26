package com.univerisity.rentify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.univerisity.Common.profile.Profile;
import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOImages;
import com.univerisity.DataAccess.DAOs.DAOProperty;
import com.univerisity.DataAccess.DAOs.DAOUser;
import com.univerisity.Domain.models.Favorito;
import com.univerisity.Domain.models.Image;
import com.univerisity.Domain.models.Property;
import com.univerisity.Domain.models.User;
import com.univerisity.Domain.services.PropertyService;
import com.univerisity.Domain.services.UserService;

import java.util.ArrayList;

public class DetailPropertyActivity extends AppCompatActivity {

    ImageView image;
    TextView title;
    TextView address;
    TextView price;
    TextView description;
    ImageButton fav;
    Property propertyDetail = null;

    private String phoneNumber = "";
    PropertyService service = new PropertyService();
    UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_property);
        int id = getIntent().getIntExtra("id", 0);

        image = findViewById(R.id.img_detail_property);
        title = findViewById(R.id.lb_title_detail_property);
        address = findViewById(R.id.lb_location_detail_property);
        price  = findViewById(R.id.lb_price_detail_property);
        description = findViewById(R.id.lb_description_detail_property);
        fav = findViewById(R.id.btn_favorite_detail_property);

        if(id != 0){
            getInfo(id);
        }
    }

    private void getInfo(int id){
        service.getProperty(id, new DAOProperty.GetProptertyCallback() {
            @Override
            public void onSuccess(Property property) {
                title.setText(property.getTitulo());
                address.setText(property.getDireccion());
                price.setText("S/. " + property.getPrecio());
                description.setText(property.getDescripcion());
                propertyDetail = property;

                showImage(property);

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

                property.obtenerPropietario(new DAOUser.UserCallback() {
                    @Override
                    public void onLoginSuccess(User user) {
                        phoneNumber = user.getCelular();
                    }

                    @Override
                    public void onLoginFailure(String errorMessage) {
                        Toast.makeText(DetailPropertyActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(DetailPropertyActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    //Events
    public void btnBackDetailProduct_Click(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnFavoriteDetailProduct_Click(View view){
        ImageButton iv = (ImageButton) view;
        if((int)iv.getTag() == R.drawable.ic_favorite_outline){
            iv.setImageResource(R.drawable.ic_favorite);
            iv.setTag(R.drawable.ic_favorite);

            service.addFavority(Profile.getIdRauser(), (iv.getId() / (int)10), new DAOProperty.PropertyCallback(){
                @Override
                public void onSuccess(int id) {
                    Toast.makeText(view.getContext(), "Propiedad añadida a favoritos", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText (view.getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            iv.setImageResource(R.drawable.ic_favorite_outline);
            iv.setTag(R.drawable.ic_favorite_outline);

            service.deleteFavorite(Profile.getIdRauser(), (iv.getId() / (int) 10), new DAOProperty.PropertyCallback() {
                @Override
                public void onSuccess(int id) {
                    Toast.makeText(view.getContext(), "Propiedad eimindad de favoritos", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void btnLocationDetailProduct_Click(View view){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("address", propertyDetail.getDireccion());
        intent.putExtra("longitude", propertyDetail.getLongitud());
        intent.putExtra("latitude", propertyDetail.getLatitud());
        startActivity(intent);
    }

    public void btnWhatsappDetailProduct_Click(View view){
        String phoneNumber = this.phoneNumber;
        String message = "Hola, puedo vistar a ver el inmueble?";

        if(!phoneNumber.isEmpty()){
            String url = "https://wa.me/" + phoneNumber + "?text=" + Uri.encode(message);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "WhatsApp no está instalado en este dispositivo.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "La propiedad no cuenta con un numero de telefono", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImage(Property p){
        p.obtenerImagenes(new DAOImages.ImageListCallback() {
            @Override
            public void onSuccess(ArrayList<Image> images) {
                if(images.size() > 0){
                    if (images.get(0).getImagenUrl() != null && !images.get(0).getImagenUrl().isEmpty()) {
                        Glide.with(DetailPropertyActivity.this)
                                .load(images.get(0).getImagenUrl())
                                .error(R.drawable.ic_no_image)
                                .into(image);
                    }
                    else{
                        Glide.with(DetailPropertyActivity.this)
                                .load(R.drawable.ic_no_image)
                                .into(image);
                    }
                }
                else{
                    Glide.with(DetailPropertyActivity.this)
                            .load(R.drawable.ic_no_image)
                            .into(image);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Glide.with(DetailPropertyActivity.this)
                        .load(R.drawable.ic_no_image)
                        .into(image);
            }
        });
    }
}