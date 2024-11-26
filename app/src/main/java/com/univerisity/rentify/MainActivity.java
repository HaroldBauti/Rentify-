package com.univerisity.rentify;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frame_layout;
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame_layout = findViewById(R.id.frame_layout);
        bottom_nav = findViewById(R.id.bottom_nav);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        }

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.bottom_nav_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                }
                else if(id == R.id.bottom_nav_favorite){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FavoriteFragment()).commit();
                }
                else if(id == R.id.bottom_nav_history){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HistoryFragment()).commit();
                }
                else if(id == R.id.bottom_nav_more){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new MoreFragment()).commit();
                }

                return true;
            }
        });
    }

    //Events
    public void btnAddProperty_Click(View view){
        Intent intent = new Intent(this, AddPropertyActivity.class);
        startActivity(intent);
    }

    //MoreActivity events
    public void btnAccount_Click(View view){
        startActivity(new Intent(this, AccountActivity.class));
    }

    public void btnTYC_Click(View view){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("Type", "TermsAdnCondition");
        startActivity(intent);
    }

    public void btnPolicy_Click(View view){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("Type", "Policy");
        startActivity(intent);
    }

    public void btnAbout_Click(View view){
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("Type", "About");
        startActivity(intent);
    }

    public void btnLogout_Click(View view){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}