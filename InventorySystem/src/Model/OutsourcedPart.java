/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ed
 */
public class OutsourcedPart extends Part {

    private final StringProperty companyName;

    public OutsourcedPart() {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty("");
        this.price = new SimpleDoubleProperty(0);
        this.stock = new SimpleIntegerProperty(0);
        this.max = new SimpleIntegerProperty(0);
        this.min = new SimpleIntegerProperty(0);
        this.companyName = new SimpleStringProperty("");
    }

    public OutsourcedPart(int id, String name, double price, int stock, int max, int min, String companyName) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.max = new SimpleIntegerProperty(max);
        this.min = new SimpleIntegerProperty(min);
        this.companyName = new SimpleStringProperty(companyName);
    }

    public String getCompanyName() {
        return this.companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

}
