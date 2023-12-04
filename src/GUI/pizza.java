package GUI;

import javafx.beans.property.SimpleStringProperty;

public class pizza {
    private SimpleStringProperty size;
    private SimpleStringProperty toppings;
    private SimpleStringProperty sodas;

    public pizza(String size, String toppings, String sodas){
        this.size = new SimpleStringProperty(size);
        this.toppings = new SimpleStringProperty(toppings);
        this.sodas = new SimpleStringProperty(sodas);
    }
    public String getSize(){
        return size.get();
    }
    public void setSize(String size){
        this.size = new SimpleStringProperty(size);
    }
    public String getToppings(){
        return toppings.get();
    }
    public void setToppings(String toppings){
        this.toppings = new SimpleStringProperty(toppings);
    }
    public String getSodas(){
        return sodas.get();
    }
    public void setSodas(String sodas){
        this.sodas = new SimpleStringProperty(sodas);
    }
    public String toString(){
        return size.get() + ";" + toppings.get() + ";" + sodas.get() + ";" + "\n";
    }
}
