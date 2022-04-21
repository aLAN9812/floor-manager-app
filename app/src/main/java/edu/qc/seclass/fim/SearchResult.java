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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity {
    private ListView listView;
    private Bundle bundle;
    private String store, category, type, pName, color, wide, longUnit, thickness, brand, price, stock, species;
    private Button back;
    private Intent toSearch;
    private DAOFloor dao;
    private DatabaseReference ref;

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
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item, list);
        listView.setAdapter(adapter);

//        ArrayList<Tile> tileList = new ArrayList<>();
//        TileAdapter tileAdapter = new TileAdapter(this, tileList);
//        listView.setAdapter(tileAdapter);

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
                    if(data.child("store").getValue().equals(store) &&
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
                        if(category.equals("Wood")) {
                            species = data.child("species").getValue().toString();
                            item.concat(String.format("Species: %s", species));
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

//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot data: snapshot.getChildren()) {
//                    Tile tile = data.getValue(Tile.class);
//
//                    tileList.add(tile);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        back.setOnClickListener(v -> {
            toSearch.addFlags(toSearch.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(toSearch);
        });
    }
}