package com.example.shenhaichen.bakingapp.bean;

/**
 * Created by shenhaichen on 09/11/2017.
 */

public class Ingredients {

    private float quantity;
    private String measure;
    private String ingredient;


    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
