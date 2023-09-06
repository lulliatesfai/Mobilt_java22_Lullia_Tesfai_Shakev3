package com.example.uppgift;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.uppgift.MyFragment;
import com.example.uppgift.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new MyFragment())
                    .commit();
        }
    }
}