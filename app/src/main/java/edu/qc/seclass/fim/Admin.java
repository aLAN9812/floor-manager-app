package edu.qc.seclass.fim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Admin extends AppCompatActivity {
    private Intent toAdd, toHome, toAdminSearch;
    private Button add, back, editDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.setTitle("Admin Page");

        toAdd = new Intent(this, AddFloor.class);
        toAdd.addFlags(toAdd.FLAG_ACTIVITY_CLEAR_TOP);
        toHome = new Intent(this, Home.class);
        toHome.addFlags(toHome.FLAG_ACTIVITY_CLEAR_TOP);
        toAdminSearch = new Intent(this, AdminSearch.class);
        toAdminSearch.addFlags(toAdminSearch.FLAG_ACTIVITY_CLEAR_TOP);

        add = findViewById(R.id.save);
        add.setOnClickListener(v -> {
            startActivity(toAdd);
        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(v -> {
            startActivity(toHome);
        });

        editDelete = findViewById(R.id.editDelete);
        editDelete.setOnClickListener(v -> {
            startActivity(toAdminSearch);
        });

    }
}