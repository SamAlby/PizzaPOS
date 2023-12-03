package GUI;

import javafx.beans.property.SimpleStringProperty;

public class item {
    private SimpleStringProperty item = null;
    private SimpleStringProperty price = null;

    public item(String item, String price){
        this.item = new SimpleStringProperty(item);
        this.price = new SimpleStringProperty(price);
    }
    public String getItem(){
        return item.get();
    }
    public void setItem(String item){
        this.item = new SimpleStringProperty(item);
    }
    public String getPrice(){
        return price.get();
    }
    public void setPrice(String price){
        this.price = new SimpleStringProperty(price);
    }
}
