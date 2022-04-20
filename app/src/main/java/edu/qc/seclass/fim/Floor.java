package edu.qc.seclass.fim;

public class Floor {
    private String store;
    private String pName;
    private String color;
    private double wide;
    private double longUnit;
    private double thickness;
    private String brand;
    private String type;
    private double price;
    private String category;
    private double stock;

    public Floor(){}

    public Floor(String store,
                 String pName,
                 String color,
                 double wide,
                 double longUnit,
                 double thickness,
                 String brand,
                 String type,
                 double price,
                 String category,
                 double stock){
        this.store = store;
        this.pName = pName;
        this.color = color;
        this.wide = wide;
        this.longUnit = longUnit;
        this.thickness = thickness;
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.category = category;
        this.stock = stock;
    }

    public String getStore() {
        return store;
    }

    public String getpName() {
        return pName;
    }

    public String getColor() {
        return color;
    }

    public double getWide() {
        return wide;
    }

    public double getLongUnit() {
        return longUnit;
    }

    public double getThickness() {
        return thickness;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public double getStock() {
        return stock;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setWide(double wide) {
        this.wide = wide;
    }

    public void setLongUnit(double longUnit) {
        this.longUnit = longUnit;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}
