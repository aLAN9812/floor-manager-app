package edu.qc.seclass.fim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {
    private ListView listView;
    private Bundle bundle;
    private String store;
    private String category;
    private String type;
    private Button back;
    private Intent toSearch;
    private DAOFloor dao;
    private String pName, color, wide, longUnit, thickness, brand, price, stock, material, species, waterResis, waterProof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        this.setTitle("Product List");

        listView = findViewById(R.id.searchResult);
        bundle = getIntent().getExtras();
        store = bundle.getString("store");
        category = bundle.getString("category");
        type = bundle.getString("type");
        back = findViewById(R.id.cancel);
        toSearch = new Intent(this, Search.class);
        dao = new DAOFloor();

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.item, list);
        listView.setAdapter(adapter);

        dao.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()) {
                    if(data.child("store").getValue().equals(store) &&
                            data.child("category").getValue().equals(category) &&
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
                                "Product name: %s\nColor: %s\nSize: %s\" x %s\" x %smm\nBrand: %s\nPrice: $%s\nStock: %s sqft\n",
                                pName, color, wide, longUnit, thickness, brand, price, stock);
                        if(data.child("category").getValue().equals("Tile") || data.child("category").getValue().equals("Stone")) {
                            material = data.child("material").getValue().toString();
                            item += String.format("Material: %s", material);
                        }
                        if(data.child("category").getValue().equals("Wood")) {
                            species = data.child("species").getValue().toString();
                            item.concat(String.format("Species: %s", species));
                        }
                        if(data.child("category").getValue().equals("Laminate")) {
                            waterResis = data.child("waterResis").getValue().toString();
                            item.concat(String.format("Water Resistant: %s", waterResis));
                        }
                        if(data.child("category").getValue().equals("Vinyl")) {
                            waterProof = data.child("waterProof").getValue().toString();
                            item.concat(String.format("Species: %s", waterProof));
                        }
                        list.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(v -> {
            startActivity(toSearch);
        });
    }
}