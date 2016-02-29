/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Игорь
 */
public class ViewDepartment {

    private LongProperty id;
    private StringProperty name;
    private LongProperty directorId;
    private StringProperty directorName;
    
    public ViewDepartment(){
        this.id = new SimpleLongProperty(-1);
        this.name = new SimpleStringProperty("");
        this.directorId = new SimpleLongProperty(-1);
        this.directorName = new SimpleStringProperty("");
    };

    public ViewDepartment(long id, String name, long directorId, String directorName) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.directorId = new SimpleLongProperty(directorId);
        this.directorName = new SimpleStringProperty(directorName);
    }

    @Override
    public String toString(){
        return name.get();
    }
    
    /**
     * @return the id
     */
    public long getId() {
        return id.get();
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    /**
     * @return the directorId
     */
    public long getDirectorId() {
        return directorId.get();
    }

    /**
     * @param directorId the directorId to set
     */
    public void setDirectorId(long directorId) {
        this.directorId.set(directorId);
    }

    public LongProperty directorIdProperty() {
        return directorId;
    }

    /**
     * @return the directorName
     */
    public String getDirectorName() {
        return directorName.get();
    }

    /**
     * @param directorName the directorName to set
     */
    public void setDirectorName(String directorName) {
        this.directorName.set(directorName);
    }

    public StringProperty directorNameProperty() {
        return directorName;
    }
}
