package edu.qc.seclass.fim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Intent toSearch, toAdmin;
    private Button customer, employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.setTitle("Home");

        toSearch = new Intent(this, Search.class);
        toAdmin = new Intent(this, Admin.class);

        customer = findViewById(R.id.customer);
        customer.setOnClickListener(v -> {
            startActivity(toSearch);
        });
        employee = findViewById(R.id.employee);
        employee.setOnClickListener(v -> {
            startActivity(toAdmin);
        });
    }
}