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
        toHome = new Intent(this, Home.class);
        toAdminSearch = new Intent(this, AdminSearch.class);

        add = findViewById(R.id.save);
        add.setOnClickListener(v -> {
            toAdd.addFlags(toAdd.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(toAdd);
        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(v -> {
            toHome.addFlags(toHome.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(toHome);
        });

        editDelete = findViewById(R.id.editDelete);
        editDelete.setOnClickListener(v -> {
            toAdminSearch.addFlags(toAdminSearch.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(toAdminSearch);
        });

    }
}