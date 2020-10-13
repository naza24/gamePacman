package com.pacman;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            NavController nav= Navigation.findNavController(findViewById(R.id.fragment1));
            nav.navigate(R.id.menuPrincipalFragment,bundle);

        }

    }


}