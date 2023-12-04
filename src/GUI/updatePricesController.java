package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Popup;

// Controller Class for updatePrices screen
public class updatePricesController extends Main implements Initializable {
    @FXML private TableView<item> priceTable;
    @FXML private TableColumn<item, String> Items;
    @FXML private TableColumn<item, String> Prices;
    @FXML private TextField EditPrice;
    @FXML private Text editName;
    @FXML private Button Save;
    @FXML private Button Edit;

    // when back is pressed
    public void backUpdatePrices() {
        GuiManager.getInstance().changeWindow("adminOptions.fxml");
    }

    // when logout is pressed
    public void logoutUpdatePrices() {
        GuiManager.getInstance().changeWindow("login.fxml");
        File file = new File("src/pizzas.txt"); // delete the database
        file.delete();
    }
    
    // when edit is pressed
    public void edit(){
        int selectedIndex = priceTable.getSelectionModel().getSelectedIndex(); // get the current index
        if (selectedIndex >= 0) { // if the user selected a row
            item currItem = priceTable.getItems().get(selectedIndex); // get the selected item
            editName.setText(currItem.getItem()); // fill in the text field with price appended
            EditPrice.setText(currItem.getPrice()); // fill in the price of the item
        } else { // if they didnt select a row
            Popup popup = popUp("Select an item to edit"); // tell the user to select a row
            popup.show(primStage);
        }
    }

    // when save is pressed
    public void save(){
        int selectedIndex = priceTable.getSelectionModel().getSelectedIndex(); // see what row they selected
        if (selectedIndex >= 0) { // if they selected a row
                if (!EditPrice.getText().equals("")) { // if the pin isn't blank
                    // create a user based off the edit
                    item item = new item(editName.getText(), EditPrice.getText()); 
                    if (!item.getItem().equals("tax rate") && !isInteger(item.getPrice())) { // if its not a number and not tax rate
                        Popup popup = popUp("Please enter a number"); // let the user know
                        popup.show(primStage);
                    } else if(item.getItem().equals("tax rate") && !isFloat(item.getPrice())){ // if its not a float
                        Popup popup = popUp("For tax rate, please enter a decimal"); // let the user know
                        popup.show(primStage);
                    }
                     else { // if the inputs are correct
                        delItem(item); // delete the stored version of the user
                        try {
                            FileWriter fw = new FileWriter("src/prices.txt", true); // open the user database
                            // add the new version of the user
                            fw.write(item.getItem() + ',' + item.getPrice() + ',' + "\n"); 
                            fw.close(); // close the database
                        } catch (IOException e) {
                        }
                        // clear the boxes for the next edit
                        editName.setText("Item");;
                        EditPrice.clear();
                        // update the table
                        initialize(null, null);
                    }
                } else {
                    Popup popup = popUp("Please enter a price"); // if the pin was blank
                    popup.show(primStage);
                }
            } else {
                Popup popup = popUp("Please select an item"); // if the name was blank
                popup.show(primStage);
            }
        }
    

    @Override // fills the table
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<item> observableList = FXCollections.observableArrayList(fetchPrices()); // gets the items and their prices
        Items.setCellValueFactory(new PropertyValueFactory<>("Item")); // ties the item columns to the item names
        Prices.setCellValueFactory(new PropertyValueFactory<>("Price")); // same but for price column
        priceTable.setItems(observableList); // pushes the data into the table
    }

    private static List<item> fetchPrices() {
        List<String> prices = new ArrayList<>(); // create a list to hold the prices
        try {
            Scanner s = new Scanner(new File("src/prices.txt")); // open the prices database
            while (s.hasNextLine()) {
                prices.add(s.nextLine()); // add each item to the list
            }
            s.close(); // close the data base
        } catch (FileNotFoundException e) {
        }
        String[] curr; // hold the current item
        List<item> items = new ArrayList<item>(); // create a list of items
        for (int i = 0; i < prices.size(); i++) { // for each item
            curr = prices.get(i).split(","); // split up their attributes
            // create an item obj out of them and add it to the item list
            items.add(new item(curr[0], curr[1]));
        }
        return items; // return the list of item objs
    }
    
    private void delItem(item item) {
            delItems(item); // delete the user
            initialize(null, null); // refresh table
        
    }

     // deletes the item from the data base
     private static void delItems(item item) {
        List<item> itemList = fetchPrices(); // get the list of items
        // for each item
        boolean itemDeleted = false; 
        for (int i = 0; i < itemList.size(); i++) {
            if ((item.getItem().equals(itemList.get(i).getItem())) && !itemDeleted) { // if their names match
                itemList.remove(i); // remove the item from the list
                itemDeleted = true;
            }
        }
        // delete the database
        File file = new File("src/prices.txt");
        file.delete();
        // for each item in the list
        for (int i = 0; i < itemList.size(); i++) {
            // add it to the database
            try {
                FileWriter fw = new FileWriter("src/prices.txt", true); // open the item database
                fw.write(itemList.get(i).getItem() + ',' + itemList.get(i).getPrice() + ',' + "\n"); // add the new item
                fw.close(); // close the database
            } catch (IOException e) {
            }
        }
    }
}