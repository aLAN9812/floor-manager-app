package edu.qc.seclass.fim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditDelete extends AppCompatActivity {

    private Bundle bundle;
    private String store, category, type, pName, color, wide, longUnit, thickness, brand, price, stock, species;
    private String newPName, newColor, newBrand;
    private Double newWide, newLong, newThickness, newPrice, newStock;
    private TextView storeTv, categoryTv, speciesTv, typeTv;
    private EditText pNameEt, colorEt, wideEt, longUnitEt, thicknessEt, brandEt, priceEt, stockEt;
    private Button save, delete, cancel;
    private Intent toAdminSearch;
    private DAOFloor dao;
    private String key;
    private TableRow speciesRow;
    private HashMap<String, Object> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        bundle = getIntent().getExtras();
        key = bundle.getString("key");
        category = bundle.getString("category");
        store = bundle.getString("store");
        type = bundle.getString("type");
        pName = bundle.getString("pName");
        color = bundle.getString("color");
        wide = bundle.getString("wide");
        longUnit = bundle.getString("longUnit");
        thickness = bundle.getString("thickness");
        brand = bundle.getString("brand");
        price = bundle.getString("price");
        stock = bundle.getString("stock");
        if(category.equals("Wood"))
            species = bundle.getString("species");

        storeTv = findViewById(R.id.store);
        categoryTv = findViewById(R.id.category);
        typeTv = findViewById(R.id.type);
        pNameEt = findViewById(R.id.pName);
        speciesTv = findViewById(R.id.species);
        colorEt = findViewById(R.id.color);
        wideEt = findViewById(R.id.wide);
        longUnitEt = findViewById(R.id.longUnit);
        thicknessEt = findViewById(R.id.thickness);
        brandEt = findViewById(R.id.brand);
        priceEt = findViewById(R.id.price);
        stockEt = findViewById(R.id.stock);
        speciesRow = findViewById(R.id.speciesRow);

        storeTv.setText(store);
        categoryTv.setText(category);
        typeTv.setText(type);
        pNameEt.setText(pName);
        colorEt.setText(color);
        wideEt.setText(wide);
        longUnitEt.setText(longUnit);
        thicknessEt.setText(thickness);
        brandEt.setText(brand);
        priceEt.setText(price);
        stockEt.setText(stock);
        if(category.equals("Wood"))
            speciesRow.setVisibility(View.VISIBLE);
            speciesTv.setText(species);

        dao = new DAOFloor();

        toAdminSearch = new Intent(this, AdminSearch.class);

        save = findViewById(R.id.save);
        save.setOnClickListener(v -> {
            newPName = pNameEt.getText().toString();
            newColor = colorEt.getText().toString();
            newWide = Double.parseDouble(wideEt.getText().toString());
            newLong = Double.parseDouble(longUnitEt.getText().toString());
            newThickness = Double.parseDouble(thicknessEt.getText().toString());
            newBrand = brandEt.getText().toString();
            newPrice = Double.parseDouble(priceEt.getText().toString());
            newStock = Double.parseDouble(stockEt.getText().toString());
            hashMap = new HashMap<>();
            hashMap.put("pName", newPName);
            hashMap.put("color", newColor);
            hashMap.put("wide", newWide);
            hashMap.put("longUnit", newLong);
            hashMap.put("thickness", newThickness);
            hashMap.put("brand", newBrand);
            hashMap.put("price", newPrice);
            hashMap.put("stock", newStock);

            dao.getDatabaseReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean exist = false;
                    for(DataSnapshot data: snapshot.getChildren()) {
                        for(DataSnapshot data2: data.getChildren()) {
                            if(data2.child("pName").getValue().toString().equals(pNameEt.getText().toString()) &&
                                    data2.child("store").getValue().toString().equals(store)) {
                                exist = true;
                                Toast.makeText(EditDelete.this, "Same product name already exists in that store", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if(exist)
                            break;
                    }
                    if(!exist) {
                        if(category.equals("Tile")) {
                            toAdminSearch.addFlags(toAdminSearch.FLAG_ACTIVITY_CLEAR_TOP);
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            toAdminSearch.addFlags(toAdminSearch.FLAG_ACTIVITY_CLEAR_TOP);
            if(category.equals("Tile")) {
                dao.removeTile(key).addOnSuccessListener(suc -> {
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                    startActivity(toAdminSearch);
                }).addOnFailureListener(er -> {
                    Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            if(category.equals("Stone")) {
                dao.removeStone(key).addOnSuccessListener(suc -> {
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                    startActivity(toAdminSearch);
                }).addOnFailureListener(er -> {
                    Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            if(category.equals("Wood")) {
                dao.removeWood(key).addOnSuccessListener(suc -> {
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                    startActivity(toAdminSearch);
                }).addOnFailureListener(er -> {
                    Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
            if(category.equals("Laminate")) {
                dao.removeLaminate(key).addOnSuccessListener(suc -> {
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                    startActivity(toAdminSearch);
                }).addOnFailureListener(er -> {
                    Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }
            if(category.equals("Vinyl")) {
                dao.removeVinyl(key).addOnSuccessListener(suc -> {
                    Toast.makeText(EditDelete.this, "Product is deleted", Toast.LENGTH_SHORT).show();
                    startActivity(toAdminSearch);
                }).addOnFailureListener(er -> {
                    Toast.makeText(EditDelete.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> {
            toAdminSearch.addFlags(toAdminSearch.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(toAdminSearch);
        });
    }
}