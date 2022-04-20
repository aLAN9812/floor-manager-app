package edu.qc.seclass.fim;

public class Vinyl extends Floor {
    private boolean waterProof;

    public Vinyl(String store,
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
                    boolean waterProof) {
        super(store, pName, color, wide, longUnit, thickness, brand, type, price, category, stock);
        this.waterProof = waterProof;
    }

    public boolean isWaterProof() {
        return waterProof;
    }

    public void setWaterProof(boolean waterProof) {
        this.waterProof = waterProof;
    }
}
