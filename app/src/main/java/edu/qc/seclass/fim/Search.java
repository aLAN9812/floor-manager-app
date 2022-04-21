package edu.qc.seclass.fim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private Intent toResult, toHome;
    private String store;
    private String category, type, pName, color, wide, longUnit, thickness, brand, price, stock, species;;
    private Bundle bundle;
    private Button search, back;
    private Spinner categorySpinner, storeSpinner, typeSpinner;
    private ArrayAdapter<String> categoryAdapter, storeAdapter, tiletypeAdapter, stonetypeAdapter, woodtypeAdapter, laminatetypeAdapter, vinyltypeAdapter;
    private DAOFloor dao;
    private DatabaseReference ref;
    private ArrayList<String> list;

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
        toResult.addFlags(toResult.FLAG_ACTIVITY_CLEAR_TOP);
        toHome = new Intent(this, Home.class);
        toHome.addFlags(toHome.FLAG_ACTIVITY_CLEAR_TOP);

        search = findViewById(R.id.search);
        search.setOnClickListener(v -> {
            list = new ArrayList<>();
            store = storeSpinner.getSelectedItem().toString();
            category = categorySpinner.getSelectedItem().toString();
            type = typeSpinner.getSelectedItem().toString();

            dao = new DAOFloor();
            if(category.equals("Tile"))
                ref = dao.getTile();
            if(category.equals("Stone"))
                ref = dao.getStone();
            if(category.equals("Wood"))
                ref = dao.getWood();
            if(category.equals("Laminate"))
                ref = dao.getLaminate();
            if(category.equals("Vinyl"))
                ref = dao.getVinyl();

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot data: snapshot.getChildren()) {
                        if(data.child("store").getValue().equals(store.substring(store.length() - 4)) &&
                                data.child("type").getValue().equals(type)) {
                            pName = data.child("pName").getValue().toString();
                            color = data.child("color").getValue().toString();
                            wide = data.child("wide").getValue().toString();
                            longUnit = data.child("longUnit").getValue().toString();
                            thickness = data.child("thickness").getValue().toString();
                            brand = data.child("brand").getValue().toString();
                            price = data.child("price").getValue().toString();
                            stock = data.child("stock").getValue().toString();
                            String item = String.format(
                                    "Product name: %s\nType: %s\n",
                                    pName, type);
                            if(category.equals("Wood")) {
                                species = data.child("species").getValue().toString();
                                item += String.format("Species: %s\n", species);
                            }
                            item += String.format("Color: %s\nSize: %s\" x %s\" x %smm\nBrand: %s\nPrice: $%s\nStock: %s sqft\n",
                                    color, wide, longUnit, thickness, brand, price, stock);
                            list.add(item);
                        }
                    }
                    if(list.size() == 0) {
                        Toast.makeText(Search.this, "No search result", Toast.LENGTH_SHORT).show();
                    } else {
                        bundle = new Bundle();
                        bundle.putStringArrayList("list", list);
                        toResult.putExtras(bundle);
                        startActivity(toResult);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(v -> {
            startActivity(toHome);
        });

    }
}