/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ed
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIDCount = 0;
    private static int productIDCount = 0;
    
    public static ObservableList<Product> GetAllProducts(){
        return allProducts;
                
    }
    
    public static ObservableList<Part> GetAllParts(){
        return allParts;
    }
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }
    
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    
    public static ObservableList lookupPart(String partName){
        ObservableList<Part> partsInSearch = FXCollections.observableArrayList();
        
        // if search is empty return all parts
        if (partName.length() == 0) {
            return allParts;
        }
        
        for(int index = 0; index < allParts.size(); index++) {
            // search implementation for parts 
            if (allParts.get(index).getName().toLowerCase().contains(partName.toLowerCase()) ) {
                partsInSearch.add(allParts.get(index));
            }
        }
        return partsInSearch;
    }
    
    public static ObservableList lookupProduct(String productName){
        ObservableList<Product> productsInSearch = FXCollections.observableArrayList();
        
        // if search is empty return all parts
        if (productName.length() == 0) {
            return allProducts;
        }
        
        for(int index = 0; index < allProducts.size(); index++) {
            // search implementation for products here
            if (allProducts.get(index).getName().toLowerCase().contains(productName.toLowerCase())) {
                productsInSearch.add(allProducts.get(index));
            }
        }
        return productsInSearch;
    }

    public static int getPartIDCount() {
        partIDCount += 1;
        return partIDCount;
    }
    
    public static int getProductIDCount() {
        productIDCount += 1;
        return productIDCount;
    }
}
