package edu.qc.seclass.fim;

public class Laminate extends Floor {
    private boolean waterResistant;

    public Laminate(String store,
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
                boolean waterResistant) {
        super(store, pName, color, wide, longUnit, thickness, brand, type, price, category, stock);
        this.waterResistant = waterResistant;
    }

    public boolean isWaterResistant() {
        return waterResistant;
    }

    public void setWaterResistant(boolean waterResistant) {
        this.waterResistant = waterResistant;
    }
}
