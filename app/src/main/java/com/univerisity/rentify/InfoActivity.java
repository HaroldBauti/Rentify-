package com.univerisity.rentify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.univerisity.Common.utils.Activities;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {

    TextView lbTilte;
    TextView lbInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        lbTilte = findViewById(R.id.lb_p_title_info);
        lbInfo = findViewById(R.id.lb_info_text);

        String type = getIntent().getStringExtra("Type");
        if(type.equals("About")){
            about();
        }
        else if(type.equals("Policy")){
            policy();;
        }
        else{
            termsAndConditions();
        }
    }

    public void btnBackInfo_Click(View view){
        startActivity(new Intent(this, Activities.getMainActivity().getClass()));
        finish();
    }

    private void about(){
        lbTilte.setText("Acerca de");
        lbInfo.setText("Rentify es una aplicación diseñada para optimizar la búsqueda, publicación y gestión de propiedades en alquiler. Nuestra plataforma conecta propietarios e inquilinos de manera eficiente, centralizando anuncios de inmuebles y ofreciendo herramientas avanzadas como filtros personalizados, geolocalización precisa y comunicación directa mediante WhatsApp. Nuestro objetivo es transformar el mercado inmobiliario con tecnología intuitiva y accesible, facilitando la interacción y mejorando la experiencia de alquiler de propiedades.\n");
    }

    private void policy(){
        lbTilte.setText("Politica de privacidad");
        lbInfo.setText("En Rentify, tu privacidad es nuestra prioridad. Nos comprometemos a proteger tus datos personales y garantizar su uso exclusivo para mejorar tu experiencia en nuestra aplicación. Recopilamos información como tu nombre, correo electrónico, número de teléfono y ubicación únicamente con fines de autenticación, publicación y búsqueda de inmuebles. Tus datos no serán compartidos con terceros sin tu consentimiento expreso. Contamos con medidas de seguridad avanzadas para salvaguardar tu información. Al utilizar Rentify, aceptas esta política de privacidad y nos autorizas a utilizar tus datos según lo descrito.\n");
    }

    private void termsAndConditions(){
        lbTilte.setText("Terminos y condiciones");
        lbInfo.setText("Al utilizar Rentify, aceptas los siguientes términos y condiciones:\n" +
                "\n" +
                "Uso de la plataforma: Rentify es una herramienta para facilitar la publicación y búsqueda de propiedades en alquiler. Nos reservamos el derecho de eliminar contenido que viole nuestras políticas.\n" +
                "Responsabilidad del usuario: Los usuarios son responsables de la veracidad de la información publicada. Rentify no garantiza la autenticidad de los anuncios.\n" +
                "Protección de datos: Nos comprometemos a proteger tu información personal según lo descrito en nuestra Política de Privacidad.\n" +
                "Actualizaciones: Rentify puede actualizar sus funcionalidades, términos y políticas sin previo aviso. Te recomendamos revisar estas condiciones periódicamente.\n" +
                "Limitación de responsabilidad: Rentify no se responsabiliza por acuerdos, disputas o conflictos entre usuarios.");
    }
}