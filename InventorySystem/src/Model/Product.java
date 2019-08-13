/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ed
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    protected IntegerProperty id;
    protected StringProperty name;
    protected DoubleProperty price;
    protected IntegerProperty stock;
    protected IntegerProperty min;
    protected IntegerProperty max;
    
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public void setName(String name){
        this.name.set(name);
    }
    
    public void setPrice(double price){
        this.price.set(price);
    }
    
    public void setStock(int stock){
        this.stock.set(stock);
    }
    
    public void setMin(int min){
        this.min.set(min);
    }
    
    public void setMax(int max){
        this.max.set(max);
    }
    
    public int getId(){
        return this.id.get();
    }
    
    public String getName(){
        return this.name.get();
    }
    
    public double getPrice(){
        return this.price.get();
    }
    
    public int getStock(){
        return this.stock.get();
    }
    
    public int getMin(){
        return this.min.get();
    }
    
    public int getMax(){
        return this.max.get();
    }
    
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }
    
    public void deleteAssociatedPart(Part associatedPart){
        this.associatedParts.remove(associatedPart);
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return this.associatedParts;
    }
}
