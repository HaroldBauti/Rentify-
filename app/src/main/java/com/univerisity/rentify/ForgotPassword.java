package com.univerisity.rentify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.univerisity.DataAccess.DAOs.DAOUser;
import com.univerisity.Domain.services.UserService;

public class ForgotPassword extends AppCompatActivity {

    EditText txtEmail;
    EditText txtPassword;
    EditText txtReapeat;

    TextView lbPassword;
    TextView lbRepeat;

    Button btnChange;

    UserService userService;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        txtEmail = findViewById(R.id.txt_forgot_email);
        txtPassword = findViewById(R.id.txt_forgot_password);
        txtReapeat = findViewById(R.id.txt_forgot_repeat_password);

        lbPassword = findViewById(R.id.lb_forgot_password);
        lbRepeat = findViewById(R.id.lb_forgot_repeat_password);

        btnChange = findViewById(R.id.btn_forgot_change);

        lbPassword.setVisibility(View.INVISIBLE);
        txtPassword.setVisibility(View.INVISIBLE);
        lbRepeat.setVisibility(View.INVISIBLE);
        txtReapeat.setVisibility(View.INVISIBLE);
        btnChange.setVisibility(View.INVISIBLE);


        userService = new UserService();
    }

    public void btnBackForgot_Click(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnSearchEmail_click(View view){
        if(!txtEmail.getText().toString().isEmpty()){
            userService.getEmail(txtEmail.getText().toString(), new DAOUser.EmailCallback() {
                @Override
                public void onLoginSuccess(int idUser) {
                    id = idUser;
                    lbPassword.setVisibility(View.VISIBLE);
                    txtPassword.setVisibility(View.VISIBLE);
                    lbRepeat.setVisibility(View.VISIBLE);
                    txtReapeat.setVisibility(View.VISIBLE);
                    btnChange.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoginFailure(String errorMessage) {
                    Toast.makeText(ForgotPassword.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void btnChangePassword_click(View view){
        String password = txtPassword.getText().toString();
        String repeat = txtReapeat.getText().toString();

        if(password.isEmpty() || repeat.isEmpty()){
            Toast.makeText(this, "Complete campos solicitados", Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equals(repeat)){
            Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return;
        }

        userService.changePassword(password, id, new DAOUser.UserChangeCallback() {
            @Override
            public void onLoginSuccess() {
                Toast.makeText(ForgotPassword.this, "Contraseña actualizada", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLoginFailure(String errorMessage) {
                Toast.makeText(ForgotPassword.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}