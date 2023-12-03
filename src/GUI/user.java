package GUI;

import javafx.beans.property.SimpleStringProperty;

public class user{
    private SimpleStringProperty name = null;
    private SimpleStringProperty id = null;

    public user(String name, String id){
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
    }
    public String getName(){
        return name.get();
    }
    public void setName(String name){
        this.name = new SimpleStringProperty(name);
    }
    public String getId(){
        return id.get();
    }
    public void setId(String id){
        this.id = new SimpleStringProperty(id);
    }
    public String toString(){
        return "Name: "+ name + ", ID: " + id;
    }
}