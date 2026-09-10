//package
package com.carmen.smartpantrymanager;

//imports

//This class will create an object of an ingredient's information
public class PantryItem {
    //attributes
    private int id;
    private String ingredientName;
    private double quantity;
    private String unit;
    private String expiryDate;

    //default constructor
    public PantryItem(){
    }

    //getter methods to access the private attributes
    public int getId() {
        return id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    // setter methods to set the values of the fields
    public void setId(int id) {
        this.id = id;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
