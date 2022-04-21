package edu.qc.seclass.fim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class AdminSearch extends AppCompatActivity {
    private Intent toEditDelete, toBack;
    private EditText pNameEt;
    private String store, category, type, pName, color, wide, longUnit, thickness, brand, price, stock, species;
    private Bundle bundle;
    private Button search, back;
    private Spinner storeSpinner, categorySpinner;
    private ArrayAdapter<String> storeAdapter, categoryAdapter;
    private DAOFloor dao;
    private DatabaseReference ref;
    private boolean found;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search);

        this.setTitle("Search");

        storeSpinner = findViewById(R.id.store);
        storeAdapter = new ArrayAdapter<>(AdminSearch.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.storeArray));
        storeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(storeAdapter);

        categorySpinner = findViewById(R.id.category);
        categoryAdapter = new ArrayAdapter<>(AdminSearch.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categoryArray));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        pNameEt = findViewById(R.id.pName);

        toEditDelete = new Intent(this, EditDelete.class);
        toEditDelete.addFlags(toEditDelete.FLAG_ACTIVITY_CLEAR_TOP);
        toBack = new Intent(this, Admin.class);
        toBack.addFlags(toBack.FLAG_ACTIVITY_CLEAR_TOP);

        search = findViewById(R.id.search);
        search.setOnClickListener(v -> {
            dao = new DAOFloor();
            store = storeSpinner.getSelectedItem().toString();
            category = categorySpinner.getSelectedItem().toString();
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
                                data.child("pName").getValue().equals(pNameEt.getText().toString())) {
                            key = data.getKey();
                            type = data.child("type").getValue().toString();
                            pName = data.child("pName").getValue().toString();
                            color = data.child("color").getValue().toString();
                            wide = data.child("wide").getValue().toString();
                            longUnit = data.child("longUnit").getValue().toString();
                            thickness = data.child("thickness").getValue().toString();
                            brand = data.child("brand").getValue().toString();
                            price = data.child("price").getValue().toString();
                            stock = data.child("stock").getValue().toString();
                            if(category.equals("Wood"))
                                species = data.child("species").getValue().toString();
                            found = true;
                            break;
                        }
                    }
                    if(found) {
                        bundle = new Bundle();
                        bundle.putString("key", key);
                        bundle.putString("store", store);
                        bundle.putString("category", category);
                        bundle.putString("type", type);
                        bundle.putString("pName", pName);
                        bundle.putString("color", color);
                        bundle.putString("wide", wide);
                        bundle.putString("longUnit", longUnit);
                        bundle.putString("thickness", thickness);
                        bundle.putString("brand", brand);
                        bundle.putString("price", price);
                        bundle.putString("stock", stock);
                        if(category.equals("Wood"))
                            bundle.putString("species", species);
                        toEditDelete.putExtras(bundle);
                        startActivity(toEditDelete);
                    }
                    else
                        Toast.makeText(AdminSearch.this, "Product not found", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(v -> {
            startActivity(toBack);
        });
    }
}