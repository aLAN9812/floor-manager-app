package edu.qc.seclass.fim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Search extends AppCompatActivity {
    private Intent toResult, toHome;
    private String store;
    private String category, type;
    private Bundle bundle;
    private Button search, back;
    private Spinner categorySpinner, storeSpinner, typeSpinner;
    private ArrayAdapter<String> categoryAdapter, storeAdapter, tiletypeAdapter, stonetypeAdapter, woodtypeAdapter, laminatetypeAdapter, vinyltypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.setTitle("Search");

        categorySpinner = findViewById(R.id.category);
        categoryAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categoryArray));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        storeSpinner = findViewById(R.id.store);
        storeAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.storeArray));
        storeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(storeAdapter);

        typeSpinner = findViewById(R.id.type);
        tiletypeAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tileType));
        tiletypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stonetypeAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.stoneType));
        stonetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        woodtypeAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.woodType));
        woodtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laminatetypeAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.laminateType));
        laminatetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vinyltypeAdapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vinylType));
        vinyltypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(tiletypeAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().equals("Tile")) {
                    typeSpinner.setAdapter(tiletypeAdapter);
                }

                if(adapterView.getSelectedItem().toString().equals("Stone")) {
                    typeSpinner.setAdapter(stonetypeAdapter);
                }

                if(adapterView.getSelectedItem().toString().equals("Wood")) {
                    typeSpinner.setAdapter(woodtypeAdapter);
                }

                if(adapterView.getSelectedItem().toString().equals("Laminate")) {
                    typeSpinner.setAdapter(laminatetypeAdapter);
                }

                if(adapterView.getSelectedItem().toString().equals("Vinyl")) {
                    typeSpinner.setAdapter(vinyltypeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        toResult = new Intent(this, SearchResult.class);
        toHome = new Intent(this, Home.class);

        search = findViewById(R.id.search);
        search.setOnClickListener(v -> {
            store = storeSpinner.getSelectedItem().toString();
            category = categorySpinner.getSelectedItem().toString();
            type = typeSpinner.getSelectedItem().toString();
            bundle = new Bundle();
            bundle.putString("store", store.substring(store.length() - 4));
            bundle.putString("category", category);
            bundle.putString("type", type);
            toResult.putExtras(bundle);
            startActivity(toResult);
        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(v -> {
            startActivity(toHome);
        });

    }
}