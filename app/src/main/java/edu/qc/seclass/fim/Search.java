package edu.qc.seclass.fim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Search extends AppCompatActivity {
    private Intent toResult, toHome;
    private String store;
    private String category;
    private EditText type;
    private Bundle bundle;
    private Button search, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.setTitle("Search");

        Spinner categorySpinner = (Spinner)findViewById(R.id.category);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categoryArray));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        Spinner storeSpinner = (Spinner)findViewById(R.id.store);
        ArrayAdapter<String> storeAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.storeArray));
        storeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(storeAdapter);

        toResult = new Intent(this, SearchResult.class);
        toHome = new Intent(this, Home.class);

        search = findViewById(R.id.search);
        search.setOnClickListener(v -> {
            store = storeSpinner.getSelectedItem().toString();
            category = categorySpinner.getSelectedItem().toString();
            type = findViewById(R.id.type);
            bundle = new Bundle();
            bundle.putString("store", store.substring(store.length() - 4));
            bundle.putString("category", category);
            bundle.putString("type", type.getText().toString());
            toResult.putExtras(bundle);
            startActivity(toResult);
        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(v -> {
            startActivity(toHome);
        });

    }
}