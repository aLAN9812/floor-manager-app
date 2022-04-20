package edu.qc.seclass.fim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Admin extends AppCompatActivity {
    private Intent toAdd, toHome;
    private Button add, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.setTitle("Admin Page");

        toAdd = new Intent(this, AddFloor.class);
        toHome = new Intent(this, Home.class);

        add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            startActivity(toAdd);
        });
        back = findViewById(R.id.cancel);
        back.setOnClickListener(v -> {
            startActivity(toHome);
        });

    }
}