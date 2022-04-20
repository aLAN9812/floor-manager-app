package edu.qc.seclass.fim;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOFloor {
    private DatabaseReference databaseReference;

    public DAOFloor() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Floor.class.getSimpleName());
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public Task<Void> addTile(Tile newTile) {
        return databaseReference.push().setValue(newTile);
    }

    public Task<Void> addStone(Stone newStone) {
        return databaseReference.push().setValue(newStone);
    }

    public Task<Void> addWood(Wood newWood) {
        return databaseReference.push().setValue(newWood);
    }

    public Task<Void> addLaminate(Laminate newLaminate) {
        return databaseReference.push().setValue(newLaminate);
    }

    public Task<Void> addVinyl(Vinyl newVinyl) {
        return databaseReference.push().setValue(newVinyl);
    }
}
