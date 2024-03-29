/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import static View_Controller.MainScreenController.selectedIndex;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ed
 */
public class ProductScreenController {

    // form fields
    @FXML private Label productLabel;
    @FXML private TextField productId;
    @FXML private TextField productName;
    @FXML private TextField productCost;
    @FXML private TextField productMax;
    @FXML private TextField productMin;
    @FXML private TextField productInv;
    @FXML private TextField searchProducts;

    // table config fields for new parts
    @FXML private TableView<Part> tableViewNewPart;
    @FXML private TableColumn<Part, Integer> newPartIdColumn;
    @FXML private TableColumn<Part, String> newPartNameColumn;
    @FXML private TableColumn<Part, Integer> newPartInvColumn;
    @FXML private TableColumn<Part, Double> newPartCostColumn;
    
    // table config fields for product parts
    @FXML private TableView<Part> tableViewProductParts;
    @FXML private TableColumn<Part, Integer> productPartIdColumn;
    @FXML private TableColumn<Part, String> productPartNameColumn;
    @FXML private TableColumn<Part, Integer> productPartInvColumn;
    @FXML private TableColumn<Part, Double> productPartCostColumn;
    

    private ObservableList<Part> partRelatedToProductsList = FXCollections.observableArrayList();

    @FXML
    void accessMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainScreen.fxml"));
        Parent product_page_parent = loader.load();

        Scene product_page_scene = new Scene(product_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        MainScreenController controller = loader.getController();
        controller.startMain(app_stage);

        app_stage.setScene(product_page_scene);
        app_stage.show();
    }
    
    @FXML
    void addPart(ActionEvent event) {
        Boolean found = false;
        Part part = tableViewNewPart.getSelectionModel().getSelectedItem();
        
        //make sure a part was selected
        if(part != null) {
            for (int i = 0; i < partRelatedToProductsList.size(); i++) {
                if(partRelatedToProductsList.get(i).equals(part)) {
                    found = true;
                }
            } 

            if(found) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Part Duplication");
                alert.setHeaderText("Part already in Product");
                alert.setContentText("This part is already linked to the product");
                alert.showAndWait();
            } else {
                partRelatedToProductsList.add(part);
                tableViewProductParts.setItems(partRelatedToProductsList);    
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part selected");
            alert.setHeaderText("Please select a part from the existing list to add to the product"); 
            alert.showAndWait();
        }
    }
    
    @FXML
    void cancelProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Product Cancel");
        alert.setHeaderText("Please confirm you want to cancel editing products");
        alert.setContentText("Any unsaved changes will be lost");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            accessMain(event);
        }
    }

    @FXML
    void initializeProduct(Product product) {
        if (product != null) {
            this.productLabel.setText("Update Product");
        } else {
            this.productLabel.setText("Add Product");
            productId.setText(Integer.toString(product.getId()));
            productName.setText(product.getName());
            productInv.setText(Integer.toString(product.getStock()));
            productMin.setText(Integer.toString(product.getMin()));
            productMax.setText(Integer.toString(product.getMax()));
            productCost.setText(Double.toString(product.getPrice()));
            partRelatedToProductsList = product.getAllAssociatedParts();
            updateProductPartTableView();

        }
    }
    
    @FXML
    void deletePart(ActionEvent event) {
        Part selectedPart = tableViewProductParts.getSelectionModel().getSelectedItem();
        
        if(selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Removal Confirmation");
            alert.setHeaderText("Confirm removal of part");
            alert.setContentText("Are you sure you want to remove '" + selectedPart.getName()+ "' from the Product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                partRelatedToProductsList.remove(selectedPart);
                updateProductPartTableView();
            }    
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part selected");
            alert.setHeaderText("Please select a part from the existing list to delete from the product"); 
            alert.showAndWait();
        }
        
    }
    
    @FXML
    void saveProduct(ActionEvent event) throws IOException {
        if(isProductValid(
            this.productName.getText(), 
            this.productMin.getText(),
            this.productMax.getText(),
            this.productInv.getText(),
            this.productCost.getText()
        )) {
            Product product = new Product();
            product.setName(this.productName.getText());
            product.setStock(Integer.parseInt(this.productInv.getText()));
            product.setMin(Integer.parseInt(this.productMin.getText()));
            product.setMax(Integer.parseInt(this.productMax.getText()));
            product.setPrice(Double.parseDouble(this.productCost.getText()));
            
            product.addAssociatedPart(partRelatedToProductsList);
            
            if(this.productId.getText().length() == 0) {
                product.setId(Inventory.getProductIDCount());
                Inventory.addProduct(product);
            } else {
                product.setId(Integer.parseInt(this.productId.getText()));
                Inventory.updateProduct(selectedIndex(), product);
            }
            
            accessMain(event);
        }
    }
    
    @FXML
    void searchProducts(ActionEvent event) {
        String term = searchProducts.getText();
        ObservableList foundParts = Inventory.lookupPart(term);
        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Part match found");
            alert.setHeaderText("No Part Names found matching " + term); 
            alert.showAndWait();
        } else {
            tableViewNewPart.setItems(foundParts);
        }
    }

    public void initialize() {
        newPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());
        newPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        newPartInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        newPartCostColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        productPartIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIDproperty().asObject());
        productPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        productPartInvColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        productPartCostColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        updatePartTableView();
        updateProductPartTableView();
    }

    // update tables
    public void updatePartTableView() {
        tableViewNewPart.setItems(Inventory.GetAllParts());

    }

    private void updateProductPartTableView() {
        tableViewProductParts.setItems(partRelatedToProductsList);
    }

    // validations
    public Boolean isProductValid(String name, String min, String max, String inv, String price) {
        String errorMessage = "";
        Integer intMin = null, intMax = null, intInv = null;
        Double dPrice = null;
        Boolean valid;

        if (name == null || name.length() == 0) {
            errorMessage += ("Product Name is blank\n");
        }

        try {
            intMin = Integer.parseInt(min);
        } catch (Exception e) {
            errorMessage += ("Minimum must be numeric\n");
        }

        try {
            intMax = Integer.parseInt(max);
        } catch (Exception e) {
            errorMessage += ("Maximum must be numeric\n");
        }

        if (intMin != null && intMin < 0) {
            errorMessage += ("Minimum cannot be negative\n");
        }

        if (intMin != null && intMax != null && intMin > intMax) {
            errorMessage += ("Minimum must be less than maximum\n");
        }

        try {
            intInv = Integer.parseInt(inv);

            if (intMin != null && intMax != null && intInv < intMin && intInv > intMax) {
                errorMessage += ("Inventory must be between minimum and maximum\n");
            }
        } catch (Exception e) {
            errorMessage += ("Inventory must be numeric\n");
        }

        try {
            dPrice = Double.parseDouble(price);

            if (dPrice <= 0) {
                errorMessage += ("Price must be greater than 0\n");
            }
        } catch (Exception e) {
            errorMessage += ("Price must be numeric\n");
        }

        if (partRelatedToProductsList.isEmpty()) {
            errorMessage += ("Product must have at least one part\n");
        } else {
            //loop through all the products and add up the cost
            //price of product cannot be less than the cost of the parts
            Double partCost = 0.0;
            for (int i = 0; i < partRelatedToProductsList.size(); i++) {
                partCost += partRelatedToProductsList.get(i).getPrice();
            }
            if (dPrice != null && partCost > dPrice) {
                errorMessage += ("Product price (" + dPrice + ") must be greater than the sum of the parts (" + partCost + ")\n");
            }
        }

        if (errorMessage.length() > 0) {
            errorMessage += ("\nFix the listed errors and save again");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Product Validation Error");
            alert.setHeaderText("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }

}
