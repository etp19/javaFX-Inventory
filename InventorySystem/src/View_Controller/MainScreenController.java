/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Eduardo
 */
public class MainScreenController implements Initializable {
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    void initializePartView(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PartScreen.fxml"));
        Parent part_page_parent = loader.load();  
       
        Scene part_page_scene = new Scene(part_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        PartScreenController controller = loader.getController();
        
        app_stage.hide(); //optional
        app_stage.setScene(part_page_scene);
        app_stage.show();
    }
    
    @FXML
    void initializeProductView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProductScreen.fxml"));
        Parent product_page_parent = loader.load();  
       
        Scene product_page_scene = new Scene(product_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        PartScreenController controller = loader.getController();
        
        app_stage.hide(); //optional
        app_stage.setScene(product_page_scene);
        app_stage.show();
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
    
}
