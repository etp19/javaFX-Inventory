/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import com.sun.istack.internal.Nullable;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo
 */
public class MainScreenController implements Initializable{
    
    // table config for part
    @FXML private TableView<Part> tableViewPart;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInvLevelColumn;
    @FXML private TableColumn<Part, Double> partPricePerUnitColumn;
    
    // table config for product
    @FXML private TableView<Product> tableViewProduct;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInvLevelColumn;
    @FXML private TableColumn<Product, Double> productPricePerUnitColumn;
   
    private static int index = -1;
    
    public static int selectedIndex() {
        return index;
    }
       
    @FXML
    void initializePartView(ActionEvent event, @Nullable Part part) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PartScreen.fxml"));
        Parent part_page_parent = loader.load();  
       
        Scene part_page_scene = new Scene(part_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        PartScreenController controller = loader.getController();
        if (part != null) {
            controller.initializePart(part);
        }
        else{       
            controller.initializePart(null);
        }
        
        app_stage.hide(); //optional
        app_stage.setScene(part_page_scene);
        app_stage.show();
    }
    
    @FXML
    void initializeProductView(ActionEvent event, @Nullable Product product) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProductScreen.fxml"));
        Parent product_page_parent = loader.load();  
       
        Scene product_page_scene = new Scene(product_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        ProductScreenController controller = loader.getController();
        if (product != null) {
            controller.initializeProduct(product);
        }
        else{       
            controller.initializeProduct(null);
        }
        
        app_stage.hide(); //optional
        app_stage.setScene(product_page_scene);
        app_stage.show();
    }
    
    public void startMain(Stage stage) throws IOException {
        index = -1;
        Parent part_page_parent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene part_page_scene = new Scene(part_page_parent);
        stage.setScene(part_page_scene);
        stage.show(); 
        
        updatePartTableView();
        updateProductTableView();
    }
     
    // Parts CRUD section
    @FXML
    void addPart(ActionEvent event)throws IOException {
        initializePartView(event, null);
    }
    
    @FXML
    void updatePart(ActionEvent event)throws IOException {
        Part selectedPart = tableViewPart.getSelectionModel().getSelectedItem();
        index = Inventory.GetAllParts().indexOf(selectedPart);
        if (selectedPart != null) {
            initializePartView(event, selectedPart);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part selected");
            alert.showAndWait();
        }
    }
    
       
    @FXML
    void deletePart(ActionEvent event) {
        Part selectedPart = tableViewPart.getSelectionModel().getSelectedItem();
        
        if(selectedPart != null) {           
            if(validatePartDelete(selectedPart)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Unable to delete part");
                alert.setHeaderText("Part is linked to a current product and cannot be deleted"); 
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Part Delete Confirmation");
                alert.setHeaderText("Confirm deletion of part");
                alert.setContentText("Are you sure you want to delete " + selectedPart.getName()+ "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                    updatePartTableView();
                }    
            }
            
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part selected");
            alert.setHeaderText("Please select a part from the existing list to delete"); 
            alert.showAndWait();
        }
    }   
    
    // Product CRUD section
    @FXML
    void addProduct(ActionEvent event)throws IOException {
        initializePartView(event, null);
    }
    
    @FXML
    void updateProduct(ActionEvent event)throws IOException {
        Product selectedProduct = tableViewProduct.getSelectionModel().getSelectedItem();
        index = Inventory.GetAllProducts().indexOf(selectedProduct);
        if (selectedProduct != null) {
            initializeProductView(event, selectedProduct);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Product selected");
            alert.showAndWait();
        }
    }
    
    @FXML
    void deleteProduct(ActionEvent event) {
        //make Product has no parts, alert if true
        Product selectedProduct = tableViewProduct.getSelectionModel().getSelectedItem();
        
        if(selectedProduct != null) {
            if(selectedProduct.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle("Product Delete Confirmation");
                alert.setHeaderText("Confirm deletion of product");
                alert.setContentText("Are you sure you want to delete " + selectedProduct.getName()+ "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectedProduct);
                    updatePartTableView();
                }    
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setTitle("Product has parts");
                alert.setHeaderText("Unable to delete a product that has parts associated with it"); 
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Product selected");
            alert.setHeaderText("Please select a product from the existing list to delete"); 
            alert.showAndWait();
        }
    }
    
    @FXML
    void exitProgram(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Please confirm you want to exit.");
        alert.setContentText("Any unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // populate data for part
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        partPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        updatePartTableView();
        
        // populate data for product
        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productInvLevelColumn.setCellValueFactory(cellData -> cellData.getValue().productInvProperty().asObject());
        productPricePerUnitColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        updateProductTableView();
    }
    
    // functions to update tables views
    void updatePartTableView(){
        ObservableList<Part> partItems = Inventory.GetAllParts();
        tableViewPart.setItems(partItems);
    }
    
    void updateProductTableView(){
        ObservableList<Product> productItems = Inventory.GetAllProducts();
        tableViewProduct.setItems(productItems);
    }
    
    // validations
    public static boolean validatePartDelete(Part part) {
        boolean isFound = false;
        
        for (int i = 0; i < Inventory.GetAllProducts().size(); i++) {
            if (Inventory.GetAllProducts().get(i).getAllAssociatedParts().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }
    
}
