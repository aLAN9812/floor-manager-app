package edu.qc.seclass.fim;

public class Stone extends Floor {
    private String material;

    public Stone(String store,
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
                String material) {
        super(store, pName, color, wide, longUnit, thickness, brand, type, price, category, stock);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
