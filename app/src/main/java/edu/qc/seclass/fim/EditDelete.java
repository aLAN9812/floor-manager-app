package edu.qc.seclass.fim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

public class EditDelete extends AppCompatActivity {

    private Bundle bundle;
    private String store, category, type, pName, color, wide, longUnit, thickness, brand, price, stock, species;
    private String newStore, newStoreId, newCategory, newType, newSpecies, newPName, newColor, newBrand;
    private Double newWide, newLong, newThickness, newPrice, newStock;
    private EditText pNameEt, colorEt, wideEt, longUnitEt, thicknessEt, brandEt, priceEt, stockEt;
    private Button save, delete, cancel;
    private Intent toAdminSearch;
    private DAOFloor dao;
    private String key;
    private TableRow speciesRow;
    private HashMap<String, Object> hashMap;
    private Spinner categorySpinner, storeSpinner, typeSpinner, speciesSpinner;
    private ArrayAdapter<String> storeAdapter, categoryAdapter, tiletypeAdapter, stonetypeAdapter, woodtypeAdapter, laminatetypeAdapter, vinyltypeAdapter, speciesAdapter;
    private String[] categoryArray, storeArray, typeArray, speciesArray;
    private int storePos, categoryPos, typePos, speciesPos;
    DatabaseReference from, to;

    private void delete(boolean moving) {
        if(category.equals("Tile")) {
            dao.removeTile(key).addOnSuccessListener(suc -> {
                if(!moving)
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Stone")) {
            dao.removeStone(key).addOnSuccessListener(suc -> {
                if(!moving)
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Wood")) {
            dao.removeWood(key).addOnSuccessListener(suc -> {
                if(!moving)
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Laminate")) {
            dao.removeLaminate(this.key).addOnSuccessListener(suc -> {
                if(!moving)
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Vinyl")) {
            dao.removeVinyl(key).addOnSuccessListener(suc -> {
                if(!moving)
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void update() {
        if(category.equals("Tile")) {
            dao.updateTile(key, hashMap).addOnSuccessListener(suc -> {
                Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Stone")) {
            dao.updateStone(key, hashMap).addOnSuccessListener(suc -> {
                Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Wood")) {
            dao.updateWood(key, hashMap).addOnSuccessListener(suc -> {
                Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Laminate")) {
            dao.updateLaminate(key, hashMap).addOnSuccessListener(suc -> {
                Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
        if(category.equals("Vinyl")) {
            dao.updateVinyl(key, hashMap).addOnSuccessListener(suc -> {
                Toast.makeText(EditDelete.this, "Product is Updated", Toast.LENGTH_SHORT).show();
                startActivity(toAdminSearch);
            }).addOnFailureListener(er -> {
                Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        speciesRow = findViewById(R.id.speciesRow);

        categorySpinner = findViewById(R.id.category);
        categoryAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categoryArray));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categoryArray = getResources().getStringArray(R.array.categoryArray);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().equals("Tile")) {
                    speciesRow.setVisibility(View.GONE);
                    typeSpinner.setAdapter(tiletypeAdapter);
                }

                if(adapterView.getSelectedItem().toString().equals("Stone")) {
                    speciesRow.setVisibility(View.GONE);
                    typeSpinner.setAdapter(stonetypeAdapter);
                }

                if(adapterView.getSelectedItem().toString().equals("Wood")) {
                    speciesRow.setVisibility(View.VISIBLE);
                    typeSpinner.setAdapter(woodtypeAdapter);
                    typeSpinner.setSelection(typePos);
                }

                if(adapterView.getSelectedItem().toString().equals("Laminate")) {
                    speciesRow.setVisibility(View.GONE);
                    typeSpinner.setAdapter(laminatetypeAdapter);
                }

                if(adapterView.getSelectedItem().toString().equals("Vinyl")) {
                    speciesRow.setVisibility(View.GONE);
                    typeSpinner.setAdapter(vinyltypeAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        storeSpinner = findViewById(R.id.store);
        storeAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.storeArray));
        storeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(storeAdapter);
        storeArray = getResources().getStringArray(R.array.storeArray);

        typeSpinner = findViewById(R.id.type);
        tiletypeAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tileType));
        tiletypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stonetypeAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.stoneType));
        stonetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        woodtypeAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.woodType));
        woodtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laminatetypeAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.laminateType));
        laminatetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vinyltypeAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vinylType));
        vinyltypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        speciesSpinner = findViewById(R.id.species);
        speciesAdapter = new ArrayAdapter<>(EditDelete.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.woodSpecies));
        speciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciesSpinner.setAdapter(speciesAdapter);

        bundle = getIntent().getExtras();
        key = bundle.getString("key");
        category = bundle.getString("category");
        categoryPos = Arrays.asList(categoryArray).indexOf(category);
        categorySpinner.setSelection(categoryPos);
        store = bundle.getString("store");
        storePos = Arrays.asList(storeArray).indexOf(store);
        storeSpinner.setSelection(storePos);
        type = bundle.getString("type");
        if(category.equals("Tile")) {
            typeSpinner.setAdapter(tiletypeAdapter);
            typeArray = getResources().getStringArray(R.array.tileType);
            typePos = Arrays.asList(typeArray).indexOf(type);
            typeSpinner.setSelection(typePos);
        }
        if(category.equals("Stone")) {
            typeSpinner.setAdapter(stonetypeAdapter);
            typeArray = getResources().getStringArray(R.array.stoneType);
            typePos = Arrays.asList(typeArray).indexOf(type);
            typeSpinner.setSelection(typePos);
        }
        if(category.equals("Wood")) {
            speciesRow.setVisibility(View.VISIBLE);
            species = bundle.getString("species");
            typeSpinner.setAdapter(woodtypeAdapter);
            typeArray = getResources().getStringArray(R.array.woodType);
            typePos = Arrays.asList(typeArray).indexOf(type);
            typeSpinner.setSelection(typePos);
            speciesArray = getResources().getStringArray(R.array.woodSpecies);
            speciesPos = Arrays.asList(speciesArray).indexOf(species);
            speciesSpinner.setSelection(speciesPos);
        }
        if(category.equals("Laminate")) {
            typeSpinner.setAdapter(laminatetypeAdapter);
            typeArray = getResources().getStringArray(R.array.laminateType);
            typePos = Arrays.asList(typeArray).indexOf(type);
            typeSpinner.setSelection(typePos);
        }
        if(category.equals("Vinyl")) {
            typeSpinner.setAdapter(vinyltypeAdapter);
            typeArray = getResources().getStringArray(R.array.vinylType);
            typePos = Arrays.asList(typeArray).indexOf(type);
            typeSpinner.setSelection(typePos);
        }
        pName = bundle.getString("pName");
        color = bundle.getString("color");
        wide = bundle.getString("wide");
        longUnit = bundle.getString("longUnit");
        thickness = bundle.getString("thickness");
        brand = bundle.getString("brand");
        price = bundle.getString("price");
        stock = bundle.getString("stock");

        pNameEt = findViewById(R.id.pName);
        colorEt = findViewById(R.id.color);
        wideEt = findViewById(R.id.wide);
        longUnitEt = findViewById(R.id.longUnit);
        thicknessEt = findViewById(R.id.thickness);
        brandEt = findViewById(R.id.brand);
        priceEt = findViewById(R.id.price);
        stockEt = findViewById(R.id.stock);

        pNameEt.setText(pName);
        colorEt.setText(color);
        wideEt.setText(wide);
        longUnitEt.setText(longUnit);
        thicknessEt.setText(thickness);
        brandEt.setText(brand);
        priceEt.setText(price);
        stockEt.setText(stock);

        dao = new DAOFloor();

        toAdminSearch = new Intent(this, AdminSearch.class);
        toAdminSearch.addFlags(toAdminSearch.FLAG_ACTIVITY_CLEAR_TOP);

        save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            if(pNameEt.getText().toString().isEmpty() ||
                    colorEt.getText().toString().isEmpty() ||
                    wideEt.getText().toString().isEmpty() ||
                    longUnitEt.getText().toString().isEmpty() ||
                    thicknessEt.getText().toString().isEmpty() ||
                    brandEt.getText().toString().isEmpty() ||
                    priceEt.getText().toString().isEmpty() ||
                    stockEt.getText().toString().isEmpty())
                Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
            else if(Double.parseDouble(wideEt.getText().toString()) == 0)
                Toast.makeText(this, "Cannot have 0 width", Toast.LENGTH_SHORT).show();
            else if(Double.parseDouble(longUnitEt.getText().toString()) == 0)
                Toast.makeText(this, "Cannot have 0 Length", Toast.LENGTH_SHORT).show();
            else if(Double.parseDouble(thicknessEt.getText().toString()) == 0)
                Toast.makeText(this, "Cannot have 0 Thickness", Toast.LENGTH_SHORT).show();
            else if(Double.parseDouble(priceEt.getText().toString()) == 0)
                Toast.makeText(this, "Cannot have 0 price", Toast.LENGTH_SHORT).show();
            else {
                newStore = storeSpinner.getSelectedItem().toString();
                newStoreId = newStore.substring(newStore.length() - 4);
                newCategory = categorySpinner.getSelectedItem().toString();
                newType = typeSpinner.getSelectedItem().toString();
                newPName = pNameEt.getText().toString();
                newColor = colorEt.getText().toString();
                newWide = Double.parseDouble(wideEt.getText().toString());
                newLong = Double.parseDouble(longUnitEt.getText().toString());
                newThickness = Double.parseDouble(thicknessEt.getText().toString());
                newBrand = brandEt.getText().toString();
                newPrice = Double.parseDouble(priceEt.getText().toString());
                newStock = Double.parseDouble(stockEt.getText().toString());
                hashMap = new HashMap<>();
                if(newCategory.equals("Wood")) {
                    newSpecies = speciesSpinner.getSelectedItem().toString();
                    hashMap.put("species", newSpecies);
                }
                hashMap.put("store", newStoreId);
                hashMap.put("type", newType);
                hashMap.put("pName", newPName);
                hashMap.put("color", newColor);
                hashMap.put("wide", newWide);
                hashMap.put("longUnit", newLong);
                hashMap.put("thickness", newThickness);
                hashMap.put("brand", newBrand);
                hashMap.put("price", newPrice);
                hashMap.put("stock", newStock);
                if(pName.equals(newPName) && category.equals(newCategory)) {
                    this.update();
                }

                if(!pName.equals(newPName) && category.equals(newCategory)) {
                    dao.getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot data: snapshot.getChildren()) {
                                for(DataSnapshot data2: data.getChildren()) {
                                    if(data2.child("pName").getValue().toString().equals(newPName) &&
                                            data2.child("store").getValue().toString().equals(store.substring(store.length() - 4))) {
                                        Toast.makeText(EditDelete.this, "Same product name already exists in that store", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                            EditDelete.this.update();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                if(pName.equals(newPName) && !category.equals(newCategory)) {
                    if(category.equals("Tile"))
                        from = dao.getTile();
                    if(category.equals("Stone"))
                        from = dao.getStone();
                    if(category.equals("Wood"))
                        from = dao.getWood();
                    if(category.equals("Laminate"))
                        from = dao.getLaminate();
                    if(category.equals("Vinyl"))
                        from = dao.getVinyl();
                    if(newCategory.equals("Tile"))
                        to = dao.getTile();
                    if(newCategory.equals("Stone"))
                        to = dao.getStone();
                    if(newCategory.equals("Wood"))
                        to = dao.getWood();
                    if(newCategory.equals("Laminate"))
                        to = dao.getLaminate();
                    if(newCategory.equals("Vinyl"))
                        to = dao.getVinyl();
                    if(!newCategory.equals("Wood"))
                        dao.moveData(key, from, to, hashMap);
                    else
                        dao.moveToWood(key, from, to, hashMap);
                    EditDelete.this.delete(true);
                }

                if(!pName.equals(newPName) && !category.equals(newCategory)) {
                    dao.getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot data: snapshot.getChildren()) {
                                for(DataSnapshot data2: data.getChildren()) {
                                    if(data2.child("pName").getValue().toString().equals(newPName) &&
                                            data2.child("store").getValue().toString().equals(store.substring(store.length() - 4))) {
                                        Toast.makeText(EditDelete.this, "Same product name already exists in that store", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                            if(category.equals("Tile"))
                                from = dao.getTile();
                            if(category.equals("Stone"))
                                from = dao.getStone();
                            if(category.equals("Wood"))
                                from = dao.getWood();
                            if(category.equals("Laminate"))
                                from = dao.getLaminate();
                            if(category.equals("Vinyl"))
                                from = dao.getVinyl();
                            if(newCategory.equals("Tile"))
                                to = dao.getTile();
                            if(newCategory.equals("Stone"))
                                to = dao.getStone();
                            if(newCategory.equals("Wood"))
                                to = dao.getWood();
                            if(newCategory.equals("Laminate"))
                                to = dao.getLaminate();
                            if(newCategory.equals("Vinyl"))
                                to = dao.getVinyl();
                            if(!newCategory.equals("Wood"))
                                dao.moveData(key, from, to, hashMap);
                            else
                                dao.moveToWood(key, from, to, hashMap);
                            EditDelete.this.delete(true);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            this.delete(false);
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            startActivity(toAdminSearch);
        });
    }
}