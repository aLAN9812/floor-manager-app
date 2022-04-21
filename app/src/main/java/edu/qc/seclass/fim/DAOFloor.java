package edu.qc.seclass.fim;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOFloor {
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private DatabaseReference tile;
    private DatabaseReference stone;
    private DatabaseReference wood;
    private DatabaseReference laminate;
    private DatabaseReference vinyl;

    public DAOFloor() {
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Floor.class.getSimpleName());
        tile = db.getReference(Floor.class.getSimpleName()+"/"+Tile.class.getSimpleName());
        stone = db.getReference(Floor.class.getSimpleName()+"/"+Stone.class.getSimpleName());
        wood = db.getReference(Floor.class.getSimpleName()+"/"+Wood.class.getSimpleName());
        laminate = db.getReference(Floor.class.getSimpleName()+"/"+Laminate.class.getSimpleName());
        vinyl = db.getReference(Floor.class.getSimpleName()+"/"+Vinyl.class.getSimpleName());
    }

    public FirebaseDatabase getDb() { return db; }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public DatabaseReference getTile() { return tile; }

    public DatabaseReference getStone() { return stone; }

    public DatabaseReference getWood() { return wood; }

    public DatabaseReference getLaminate() { return laminate; }

    public DatabaseReference getVinyl() { return vinyl; }

    public Task<Void> addTile(Tile newTile) { return tile.push().setValue(newTile); }

    public Task<Void> addStone(Stone newStone) { return stone.push().setValue(newStone); }

    public Task<Void> addWood(Wood newWood) { return wood.push().setValue(newWood); }

    public Task<Void> addLaminate(Laminate newLaminate) { return laminate.push().setValue(newLaminate); }

    public Task<Void> addVinyl(Vinyl newVinyl) { return vinyl.push().setValue(newVinyl); }

    public Task<Void> removeTile(String key) { return tile.child(key).removeValue(); }

    public Task<Void> removeStone(String key) { return stone.child(key).removeValue(); }

    public Task<Void> removeWood(String key) { return wood.child(key).removeValue(); }

    public Task<Void> removeLaminate(String key) { return laminate.child(key).removeValue(); }

    public Task<Void> removeVinyl(String key) { return vinyl.child(key).removeValue(); }

    public Task<Void> updateTile(String key, HashMap<String, Object> hashMap) { return tile.child(key).updateChildren(hashMap); }

    public Task<Void> updateStone(String key, HashMap<String, Object> hashMap) { return stone.child(key).updateChildren(hashMap); }

    public Task<Void> updateWood(String key, HashMap<String, Object> hashMap) { return wood.child(key).updateChildren(hashMap); }

    public Task<Void> updateLaminate(String key, HashMap<String, Object> hashMap) { return laminate.child(key).updateChildren(hashMap); }

    public Task<Void> updateVinyl(String key, HashMap<String, Object> hashMap) { return vinyl.child(key).updateChildren(hashMap); }

}
