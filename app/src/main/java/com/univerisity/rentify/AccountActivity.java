package com.univerisity.rentify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.univerisity.Common.profile.Profile;
import com.univerisity.DataAccess.DAOs.DAOUser;
import com.univerisity.Domain.models.User;
import com.univerisity.Domain.services.UserService;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class AccountActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtLastname;
    private EditText txtPhone;
    private EditText txtEmail;
    private EditText txtPassword;
    private Switch switchMode;
    private ImageButton btnShowHide;
    private UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        txtName = findViewById(R.id.txt_account_name);
        txtLastname = findViewById(R.id.txt_account_lastname);
        txtPhone = findViewById(R.id.txt_account_phone);
        txtEmail = findViewById(R.id.txt_account_email);
        txtPassword = findViewById(R.id.txt_account_password);
        switchMode = findViewById(R.id.change_mode_account);
        btnShowHide = findViewById(R.id.btn_show_hide_password_account);

        service = new UserService();

        addValues();
        configureSwitch();
    }

    private void configureSwitch(){

        switchMode.setChecked(Profile.isTipo());

        switchMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                service.changeMode(Profile.getIdRauser(), new DAOUser.UserChangeCallback() {
                    @Override
                    public void onLoginSuccess() {
                        Profile.setTipo(switchMode.isChecked());
                        Toast.makeText(AccountActivity.this, "Cambio de modo exitoso", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLoginFailure(String errorMessage) {
                        Toast.makeText(AccountActivity.this, "Error en el cambio de modo", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void addValues(){
        txtName.setText(Profile.getNombre());
        txtLastname.setText(Profile.getApellidos());
        txtPhone.setText(Profile.getCelular());
        txtEmail.setText(Profile.getEmail());
        txtPassword.setText(Profile.getClave());
    }

    private void setUser(int id, String password){
        service.getUser(id, new DAOUser.UserCallback() {
            @Override
            public void onLoginSuccess(User user) {
                Profile.setIdRauser(user.getIdRauser());
                Profile.setNombre(user.getNombre());
                Profile.setApellidos(user.getApellidos());
                Profile.setCelular(user.getCelular());
                Profile.setEmail(user.getEmail());
                Profile.setClave(password);
                Profile.setTipo(user.isTipo());
            }

            @Override
            public void onLoginFailure(String errorMessage) {

            }
        });
    }

    public void btnBackAccount_Click(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnViewHide_Click(View view){
        if (txtPassword.getInputType() == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)) {
            // Mostrar la contraseña
            txtPassword.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
            btnShowHide.setImageResource(R.drawable.ic_hide_password);
        } else {
            // Ocultar la contraseña
            txtPassword.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            btnShowHide.setImageResource(R.drawable.ic_view_password);
        }

        txtPassword.setSelection(txtPassword.length());
    }

    public void btnAccountSave_Click(View view){
        String name = txtName.getText().toString();
        String lastname = txtLastname.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        User u = new User(Profile.getIdRauser(), name, lastname,email, password, phone);

        service.updateUser(Profile.getIdRauser(), u, new DAOUser.UserChangeCallback() {
            @Override
            public void onLoginSuccess() {
                Toast.makeText(AccountActivity.this, "Actualizacion exitosa", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoginFailure(String errorMessage) {
                Toast.makeText(AccountActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        addValues();
    }
}