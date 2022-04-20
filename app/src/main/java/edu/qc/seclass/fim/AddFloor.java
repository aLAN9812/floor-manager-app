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
import com.google.firebase.database.ValueEventListener;

public class AddFloor extends AppCompatActivity {
    private Spinner categorySpinner, storeSpinner, boolSpinner1, boolSpinner2;
    private EditText pName, color, wide, longUnit, thickness, brand, type, price, stock, material, species;
    private TableRow materialRow, speciesRow, waterResisRow, waterProofRow;
    private DAOFloor dao;
    private Button add, cancel;
    private Intent toAdmin;
    private String store, storeId, category;
    private boolean waterResis, waterProof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_floor);

        this.setTitle("Add Product");

        categorySpinner = (Spinner) findViewById(R.id.category);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categoryArray));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        storeSpinner = (Spinner) findViewById(R.id.store);
        ArrayAdapter<String> storeAdapter = new ArrayAdapter<String>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.storeArray));
        storeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(storeAdapter);

        boolSpinner1 = (Spinner) findViewById(R.id.waterResis);
        boolSpinner2 = (Spinner) findViewById(R.id.waterProof);
        ArrayAdapter<String> boolAdapter = new ArrayAdapter<String>(AddFloor.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.boolArray));
        boolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boolSpinner1.setAdapter(boolAdapter);
        boolSpinner2.setAdapter(boolAdapter);

        pName = findViewById(R.id.pName);
        color = findViewById(R.id.color);
        wide = findViewById(R.id.wide);
        longUnit = findViewById(R.id.longUnit);
        thickness = findViewById(R.id.thickness);
        brand = findViewById(R.id.brand);
        type = findViewById(R.id.type);
        price = findViewById(R.id.price);
        stock = findViewById(R.id.stock);
        material = findViewById(R.id.material);
        species = findViewById(R.id.species);

        materialRow = findViewById(R.id.materialRow);
        speciesRow = findViewById(R.id.speciesRow);
        waterResisRow = findViewById(R.id.waterResisRow);
        waterProofRow = findViewById(R.id.waterProofRow);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().equals("Tile") || adapterView.getSelectedItem().toString().equals("Stone")) {
                    materialRow.setVisibility(View.VISIBLE);
                    speciesRow.setVisibility(View.GONE);
                    waterResisRow.setVisibility(View.GONE);
                    waterProofRow.setVisibility(View.GONE);
                    material.setText("");
                    species.setText("Not available");
                }

                if(adapterView.getSelectedItem().toString().equals("Wood")) {
                    speciesRow.setVisibility(View.VISIBLE);
                    materialRow.setVisibility(View.GONE);
                    waterResisRow.setVisibility(View.GONE);
                    waterProofRow.setVisibility(View.GONE);
                    species.setText("");
                    material.setText("Not available");
                }

                if(adapterView.getSelectedItem().toString().equals("Laminate")) {
                    waterResisRow.setVisibility(View.VISIBLE);
                    materialRow.setVisibility(View.GONE);
                    speciesRow.setVisibility(View.GONE);
                    waterProofRow.setVisibility(View.GONE);
                    material.setText("Not available");
                    species.setText("Not available");
                }

                if(adapterView.getSelectedItem().toString().equals("Vinyl")) {
                    waterProofRow.setVisibility(View.VISIBLE);
                    materialRow.setVisibility(View.GONE);
                    speciesRow.setVisibility(View.GONE);
                    waterResisRow.setVisibility(View.GONE);
                    material.setText("Not available");
                    species.setText("Not available");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dao = new DAOFloor();

        add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            store = storeSpinner.getSelectedItem().toString();
            storeId = store.substring(store.length() - 4);
            category = categorySpinner.getSelectedItem().toString();
            waterResis = boolSpinner1.getSelectedItem().toString().equals("Yes") ? true : false;
            waterProof = boolSpinner2.getSelectedItem().toString().equals("Yes") ? true : false;
            if(pName.getText().toString().isEmpty() ||
                    color.getText().toString().isEmpty() ||
                    wide.getText().toString().isEmpty() ||
                    longUnit.getText().toString().isEmpty() ||
                    thickness.getText().toString().isEmpty() ||
                    brand.getText().toString().isEmpty() ||
                    type.getText().toString().isEmpty() ||
                    price.getText().toString().isEmpty() ||
                    stock.getText().toString().isEmpty() ||
                    material.getText().toString().isEmpty() ||
                    species.getText().toString().isEmpty())
                Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
            else {
                dao.getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean exist = false;
                        for(DataSnapshot data: snapshot.getChildren()) {
                            if(data.child("pName").getValue().toString().equals(pName.getText().toString()) &&
                                    data.child("store").getValue().toString().equals(storeId)) {
                                exist = true;
                                Toast.makeText(AddFloor.this, "Product already exists", Toast.LENGTH_SHORT).show();
                                break;
                            }
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
                                        type.getText().toString(),
                                        Double.parseDouble(price.getText().toString()),
                                        category,
                                        Double.parseDouble(stock.getText().toString()),
                                        material.getText().toString());
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
                                        type.getText().toString(),
                                        Double.parseDouble(price.getText().toString()),
                                        category,
                                        Double.parseDouble(stock.getText().toString()),
                                        material.getText().toString());
                                dao.addStone(newStone).addOnSuccessListener(suc -> {
                                    Toast.makeText(AddFloor.this, "New Product is added", Toast.LENGTH_SHORT).show();
                                    startActivity(toAdmin);
                                }).addOnFailureListener(er -> {
                                    Toast.makeText(AddFloor.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            }

                            if(category.equals("Wood")) {
                                Wood newWood = new Wood(
                                        storeId,
                                        pName.getText().toString(),
                                        color.getText().toString(),
                                        Double.parseDouble(wide.getText().toString()),
                                        Double.parseDouble(longUnit.getText().toString()),
                                        Double.parseDouble(thickness.getText().toString()),
                                        brand.getText().toString(),
                                        type.getText().toString(),
                                        Double.parseDouble(price.getText().toString()),
                                        category,
                                        Double.parseDouble(stock.getText().toString()),
                                        species.getText().toString());
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
                                        type.getText().toString(),
                                        Double.parseDouble(price.getText().toString()),
                                        category,
                                        Double.parseDouble(stock.getText().toString()),
                                        waterResis);
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
                                        type.getText().toString(),
                                        Double.parseDouble(price.getText().toString()),
                                        category,
                                        Double.parseDouble(stock.getText().toString()),
                                        waterProof);
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
            startActivity(toAdmin);
        });
    }
}