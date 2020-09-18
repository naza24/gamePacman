package com.pacman;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pacman.fragments.MenuPrincipalFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            int aux =  bundle.getInt("puntaje");
            Toast.makeText(this, "esta mierda es "+aux, Toast.LENGTH_SHORT).show();
            NavController nav= Navigation.findNavController(findViewById(R.id.fragment1));

            nav.navigate(R.id.menuPrincipalFragment,bundle);

        }

    }


}