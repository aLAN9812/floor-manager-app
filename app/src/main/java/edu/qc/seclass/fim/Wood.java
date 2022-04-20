package edu.qc.seclass.fim;

public class Wood extends Floor {
    private String species;

    public Wood(String store,
                String pName,
                String color,
                double wide,
                double longUnit,
                double thickness,
                String brand,
                String type,
                double price,
                String category,
                double stock,
                String species) {
        super(store, pName, color, wide, longUnit, thickness, brand, type, price, category, stock);
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
