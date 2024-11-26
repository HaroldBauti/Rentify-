package com.univerisity.rentify;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.utils.ObjectUtils;
import com.univerisity.Common.profile.Profile;
import com.univerisity.Common.utils.Activities;
import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOImages;
import com.univerisity.DataAccess.DAOs.DAOProperty;
import com.univerisity.DataAccess.api.CloudinaryClient;
import com.univerisity.Domain.models.Image;
import com.univerisity.Domain.models.Property;
import com.univerisity.Domain.services.ImageService;
import com.univerisity.Domain.services.PropertyService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AddPropertyActivity extends AppCompatActivity {

    EditText txtTitle;
    EditText txtDescription;
    EditText txtAddress;
    EditText txtCity;
    EditText txtLong;
    EditText txtLat;
    EditText txtPrice;
    Button btnAction;

    //Variables de localización
    private String address;
    private double longitude;
    private double latitude;
    private int idProperty;

    private Image image = null;

    private ActivityResultLauncher<Intent> launcher;


    PropertyService service = new PropertyService();
    ImageService imgService = new ImageService();

    //Interface para guardar imagenes
    public interface UploadCallback {
        void onUploadSuccess(String imageUrl);
        void onUploadError(String error);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        //Obtener campos
        txtTitle = findViewById(R.id.txt_title_add_property);
        txtDescription = findViewById(R.id.txt_description_add_property);
        txtAddress = findViewById(R.id.txt_address_add_property);
        txtCity = findViewById(R.id.txt_city_add_property);
        txtLong = findViewById(R.id.txt_lon_add_property);
        txtLat = findViewById(R.id.txt_lat_add_property);
        txtPrice = findViewById(R.id.txt_price_add_property);
        btnAction = findViewById(R.id.btn_publish_add_property);

        if(getIntent().getBooleanExtra("isEdit", false)){
            idProperty = getIntent().getIntExtra("idProperty", 0);
            ((TextView)findViewById(R.id.lb_p_title_add_property)).setText("Actualizar propiedad");
            btnAction.setText("Actualizar");
            completeInput();
        }

        //Registrar launcher
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK){
                Intent data = result.getData();

                if(data != null){
                    address = data.getStringExtra("address");
                    latitude = data.getDoubleExtra("latitude", 0);
                    longitude = data.getDoubleExtra("longitude", 0);

                    txtAddress.setText(address);
                    txtLat.setText(String.valueOf(latitude));
                    txtLong.setText(String.valueOf(longitude));
                }
            }
        });
    }

    public void btnBackAddProperty_Click(View view){
        Intent intent = new Intent(this, Activities.getMainActivity().getClass());
        startActivity(intent);
        finish();
    }

    public void btnImagesAddProperty_Click(View view){
        Intent galeria = new Intent(Intent.ACTION_GET_CONTENT);
        galeria.setType("image/*");
        galeria.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);

        startActivityForResult(Intent.createChooser(galeria, "Selecciona imágenes"), 100);
    }

    public void btnOpenMap_Click(View v){
        Intent newIntenet = new Intent(this, MapActivity.class);
        launcher.launch(newIntenet);
    }

    public  void  btnPublishAddProperty(View view){
        if(btnAction.getText().toString().equals("Publicar")){
            Message.showDialog(this,
                    "Información",
                    "¿Desea hacer esta publicación?",
                    new Runnable() {
                        @Override
                        public void run() {
                            Property p = getProperty();
                            if(error(p)){
                                service.saveProperty(Profile.getIdRauser(), p, new DAOProperty.PropertyCallback() {
                                    @Override
                                    public void onSuccess(int idPorperty) {
                                        Toast.makeText(AddPropertyActivity.this, "Registro exitoso", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(AddPropertyActivity.this, Activities.getMainActivity().getClass()));
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String errorMessage) {
                                        Toast.makeText(AddPropertyActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            else{
                                Toast.makeText(AddPropertyActivity.this, "Algunos campos son invalidos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else{
            Message.showDialog(this,
                    "Información",
                    "¿Desea actualizar esta propiedad?",
                    new Runnable() {
                        @Override
                        public void run() {
                            Property p = getProperty();
                            if(error(p)){
                                service.editProperty(idProperty, p, new DAOProperty.PropertyCallback() {
                                    @Override
                                    public void onSuccess(int idPorperty) {

                                        Toast.makeText(AddPropertyActivity.this, "Actualización exitosa", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(AddPropertyActivity.this, Activities.getMainActivity().getClass()));
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String errorMessage) {
                                        Toast.makeText(AddPropertyActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
        }
    }

    private Property getProperty(){
        String title = txtTitle.getText().toString();
        String deascription = txtDescription.getText().toString();
        String address = txtAddress.getText().toString();
        String city = txtCity.getText().toString();
        double lat = txtLat.getText().toString().isEmpty() ? 0 : Double.parseDouble(txtLat.getText().toString());
        double lon = txtLong.getText().toString().isEmpty() ? 0 : Double.parseDouble(txtLong.getText().toString());
        double price = txtPrice.getText().toString().isEmpty() ? 0 : Double.parseDouble(txtPrice.getText().toString());

        return new Property(0, title, deascription, address, city, lon, lat, price);
    }

    private void completeInput(){
        service.getProperty(idProperty, new DAOProperty.GetProptertyCallback() {
            @Override
            public void onSuccess(Property property) {
                txtTitle.setText(property.getTitulo());
                txtDescription.setText(property.getDescripcion());
                txtAddress.setText(property.getDireccion());
                txtCity.setText(property.getCity());
                txtLong.setText(String.valueOf(property.getLongitud()));
                txtLat.setText(String.valueOf(property.getLatitud()));
                txtPrice.setText(String.valueOf(property.getPrecio()));
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                // Lista para almacenar las URIs de las imágenes seleccionadas
                List<Uri> imageUris = new ArrayList<>();
                // Verificar si se seleccionaron múltiples imágenes
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);
                        Bitmap b = uriToBitmap(imageUri, this);
                        File img = bitmapToFile(b, this);

                        if (img != null){
                            uploadImage(img, new UploadCallback() {
                                @Override
                                public void onUploadSuccess(String imageUrl) {
                                    image = new Image();
                                    image.setTitulo(imageUri.toString());
                                    image.setImagenUrl(imageUrl);
                                    Toast.makeText(AddPropertyActivity.this, "Imagen publicada: " + image.getImagenUrl(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onUploadError(String error) {
                                    image = null;
                                    Toast.makeText(AddPropertyActivity.this, error, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    imageUris.add(imageUri);
                }

                displayImages(imageUris);
            }
        }
    }

    private void displayImages(List<Uri> imageUris) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView); // Asegúrate de definir este en tu layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ImageAdapter adapter = new ImageAdapter(this, imageUris);
        recyclerView.setAdapter(adapter);
    }


    private boolean error(Property property){
        boolean ret = true;

        if(property.getTitulo().length() < 20){
            txtTitle.setHint("El titulo debe contener al menos 20 caracteres");
            txtTitle.setHintTextColor(Color.RED);
            txtTitle.setText("");
            ret = false;
        }

        if(property.getDescripcion().length() < 50){
            txtDescription.setHint("La descripción debe contener al menos 50 caracteres");
            txtDescription.setHintTextColor(Color.RED);
            txtDescription.setText("");
            ret = false;
        }

        if(property.getDireccion().length() <= 0){
            txtAddress.setHint("Dirección invalida");
            txtAddress.setHintTextColor(Color.RED);
            txtAddress.setText("");
            ret = false;
        }

        if(property.getPrecio() <= 0){
            txtPrice.setHint("El precio debe ser mayor a 0");
            txtPrice.setHintTextColor(Color.RED);
            txtPrice.setText("");
            ret = false;
        }

        if(property.getTitulo().length() < 20){
            txtTitle.setHint("El titulo debe contener al menos 20 caracteres");
            txtTitle.setHintTextColor(Color.RED);
            txtTitle.setText("");
            ret = false;
        }

        return ret;
    }

    public void uploadImage(File imageFile, UploadCallback callback) {
        new Thread(() -> {
            try {
                Map uploadResult = CloudinaryClient.getInstance()
                        .uploader()
                        .upload(imageFile, ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");

                runOnUiThread(() -> {
                    callback.onUploadSuccess(imageUrl);
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    callback.onUploadError(e.getMessage());
                });
            }
        }).start();
    }


    public File bitmapToFile(Bitmap bitmap, Context context) {
        if (bitmap == null) {
            return null; // Si el bitmap es nulo, retornamos null.
        }

        try {
            File file = new File(context.getCacheDir(), "temp_image.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // En caso de error, retornamos null
        }
    }

    public Bitmap uriToBitmap(Uri uri, Context context) {
        try {
            // Verificar que la URI no sea nula antes de intentar obtener el bitmap
            if (uri == null) {
                return null;
            }

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}