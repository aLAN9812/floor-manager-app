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

public class AddFloor extends AppCompatActivity {
    private Spinner categorySpinner, storeSpinner, typeSpinner, speciesSpinner;
    private EditText pName, color, wide, longUnit, thickness, brand, price, stock;
    private TableRow speciesRow;
    private DAOFloor dao;
    private Button add, cancel;
    private Intent toAdmin;
    private String store, storeId, category, type, species;
    private DatabaseReference root;
    private ArrayAdapter<String> storeAdapter, categoryAdapter, tiletypeAdapter, stonetypeAdapter, woodtypeAdapter, laminatetypeAdapter, vinyltypeAdapter, speciesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_floor);

        this.setTitle("Add Product");

        categorySpinner = findViewById(R.id.category);
        categoryAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categoryArray));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        storeSpinner = findViewById(R.id.store);
        storeAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.storeArray));
        storeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(storeAdapter);

        typeSpinner = findViewById(R.id.typeSpinner);
        tiletypeAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tileType));
        tiletypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stonetypeAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.stoneType));
        stonetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        woodtypeAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.woodType));
        woodtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        laminatetypeAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.laminateType));
        laminatetypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vinyltypeAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.vinylType));
        vinyltypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(tiletypeAdapter);

        speciesSpinner = findViewById(R.id.speciesSpinner);
        speciesAdapter = new ArrayAdapter<>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.woodSpecies));
        speciesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speciesSpinner.setAdapter(speciesAdapter);

        pName = findViewById(R.id.pName);
        color = findViewById(R.id.color);
        wide = findViewById(R.id.wide);
        longUnit = findViewById(R.id.longUnit);
        thickness = findViewById(R.id.thickness);
        brand = findViewById(R.id.brand);
        price = findViewById(R.id.price);
        stock = findViewById(R.id.stock);

        speciesRow = findViewById(R.id.speciesRow);

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

        add = findViewById(R.id.save);
        add.setOnClickListener(v -> {
            toAdmin.addFlags(toAdmin.FLAG_ACTIVITY_CLEAR_TOP);

            store = storeSpinner.getSelectedItem().toString();
            storeId = store.substring(store.length() - 4);
            category = categorySpinner.getSelectedItem().toString();
            type = typeSpinner.getSelectedItem().toString();


            dao = new DAOFloor();
            root = dao.getDatabaseReference();

            if(pName.getText().toString().isEmpty() ||
                    color.getText().toString().isEmpty() ||
                    wide.getText().toString().isEmpty() ||
                    longUnit.getText().toString().isEmpty() ||
                    thickness.getText().toString().isEmpty() ||
                    brand.getText().toString().isEmpty() ||
                    price.getText().toString().isEmpty() ||
                    stock.getText().toString().isEmpty())
                Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
            else {
                root.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean exist = false;
                        for(DataSnapshot data: snapshot.getChildren()) {
                            for(DataSnapshot data2: data.getChildren()) {
                                if(data2.child("pName").getValue().toString().equals(pName.getText().toString()) &&
                                        data2.child("store").getValue().toString().equals(storeId)) {
                                    exist = true;
                                    Toast.makeText(AddFloor.this, "Same product name already exists in that store", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if(exist)
                                break;
                        }
                        if(!exist) {
                            if(category.equals("Tile")) {
                                Tile newTile = new Tile(
                                        storeId,
                                        pName.getText().toString(),
                                        color.getText().toString(),
                                        Double.parseDouble(wide.getText().toString()),
                                        Double.parseDouble(longUnit.getText().toString()),
                                        Double.parseDouble(thickness.getText().toString()),
                                        brand.getText().toString(),
                                        type,
                                        Double.parseDouble(price.getText().toString()),
                                        Double.parseDouble(stock.getText().toString()));
                                dao.addTile(newTile).addOnSuccessListener(suc -> {
                                    Toast.makeText(AddFloor.this, "New Product is added", Toast.LENGTH_SHORT).show();
                                    startActivity(toAdmin);
                                }).addOnFailureListener(er -> {
                                    Toast.makeText(AddFloor.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }

                            if(category.equals("Stone")) {
                                Stone newStone = new Stone(
                                        storeId,
                                        pName.getText().toString(),
                                        color.getText().toString(),
                                        Double.parseDouble(wide.getText().toString()),
                                        Double.parseDouble(longUnit.getText().toString()),
                                        Double.parseDouble(thickness.getText().toString()),
                                        brand.getText().toString(),
                                        type,
                                        Double.parseDouble(price.getText().toString()),
                                        Double.parseDouble(stock.getText().toString()));
                                dao.addStone(newStone).addOnSuccessListener(suc -> {
                                    Toast.makeText(AddFloor.this, "New Product is added", Toast.LENGTH_SHORT).show();
                                    startActivity(toAdmin);
                                }).addOnFailureListener(er -> {
                                    Toast.makeText(AddFloor.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }

                            if(category.equals("Wood")) {
                                species = speciesSpinner.getSelectedItem().toString();
                                Wood newWood = new Wood(
                                        storeId,
                                        pName.getText().toString(),
                                        color.getText().toString(),
                                        Double.parseDouble(wide.getText().toString()),
                                        Double.parseDouble(longUnit.getText().toString()),
                                        Double.parseDouble(thickness.getText().toString()),
                                        brand.getText().toString(),
                                        type,
                                        Double.parseDouble(price.getText().toString()),
                                        Double.parseDouble(stock.getText().toString()),
                                        species);
                                dao.addWood(newWood).addOnSuccessListener(suc -> {
                                    Toast.makeText(AddFloor.this, "New Product is added", Toast.LENGTH_SHORT).show();
                                    startActivity(toAdmin);
                                }).addOnFailureListener(er -> {
                                    Toast.makeText(AddFloor.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }

                            if(category.equals("Laminate")) {
                                Laminate newLaminate = new Laminate(
                                        storeId,
                                        pName.getText().toString(),
                                        color.getText().toString(),
                                        Double.parseDouble(wide.getText().toString()),
                                        Double.parseDouble(longUnit.getText().toString()),
                                        Double.parseDouble(thickness.getText().toString()),
                                        brand.getText().toString(),
                                        type,
                                        Double.parseDouble(price.getText().toString()),
                                        Double.parseDouble(stock.getText().toString()));
                                dao.addLaminate(newLaminate).addOnSuccessListener(suc -> {
                                    Toast.makeText(AddFloor.this, "New Product is added", Toast.LENGTH_SHORT).show();
                                    startActivity(toAdmin);
                                }).addOnFailureListener(er -> {
                                    Toast.makeText(AddFloor.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }

                            if(category.equals("Vinyl")) {
                                Vinyl newVinyl = new Vinyl(
                                        storeId,
                                        pName.getText().toString(),
                                        color.getText().toString(),
                                        Double.parseDouble(wide.getText().toString()),
                                        Double.parseDouble(longUnit.getText().toString()),
                                        Double.parseDouble(thickness.getText().toString()),
                                        brand.getText().toString(),
                                        type,
                                        Double.parseDouble(price.getText().toString()),
                                        Double.parseDouble(stock.getText().toString()));
                                dao.addVinyl(newVinyl).addOnSuccessListener(suc -> {
                                    Toast.makeText(AddFloor.this, "New Product is added", Toast.LENGTH_SHORT).show();
                                    startActivity(toAdmin);
                                }).addOnFailureListener(er -> {
                                    Toast.makeText(AddFloor.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        toAdmin = new Intent(this, Admin.class);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            toAdmin.addFlags(toAdmin.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(toAdmin);
        });
    }
}