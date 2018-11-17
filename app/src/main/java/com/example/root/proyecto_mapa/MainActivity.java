package com.example.root.proyecto_mapa;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int TIEMPO_CARGA = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menus = new Intent(MainActivity.this, MenusActivity.class);
                startActivity(menus);
                finish();
            }
        }, TIEMPO_CARGA);
    }
}
