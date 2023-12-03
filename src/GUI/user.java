package GUI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class user{
    private SimpleStringProperty name = null;
    private SimpleStringProperty id = null;
    private SimpleBooleanProperty admin = null;

    public user(String name, String id, boolean admin){
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.admin = new SimpleBooleanProperty(admin);
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
    public boolean getAdmin(){
        return admin.get();
    }
    public void setAdmin(boolean admin){
        this.admin = new SimpleBooleanProperty(admin);
    }
    public String toString(){
        return "Name: "+ name + ", ID: " + id + ", Admin: " + admin;
    }
}