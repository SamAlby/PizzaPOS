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
import javafx.stage.Popup;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

// Controller Class for createOrder screen
public class createOrderController extends Main implements Initializable {
    @FXML
    private Text welcomeText;
    @FXML
    private TableView<pizza> OrderTable;
    @FXML
    private TableColumn<pizza, String> size;
    @FXML
    private TableColumn<pizza, String> toppings;
    @FXML
    private TableColumn<pizza, String> sodas;
    @FXML
    private Button soda;

    // Logout button onAction method
    public void logOut() {
        GuiManager.getInstance().changeWindow("login.fxml");
    }

    // Admin options button onAction method
    public void adminOptions() {
        GuiManager.getInstance().changeWindow("adminOptions.fxml");
    }

    // Add soda button clicked
    public void addSoda() {
        // if adding a soda to an order
        if (OrderTable.getSelectionModel().getSelectedIndex() >= 0) {
            //to add, adding sodas to regular orders 
        } else { // if adding a soda in general
            pizza pizza = new pizza(null, null, "1");
            try {
                FileWriter fw = new FileWriter("src/pizzas.txt", true); // open the pizza database
                // add the new pizza
                fw.write("None;None;" + pizza.getSodas() + ";"+ "\n");
                fw.close(); // close the database
            } catch (IOException e) {
            }
            initialize(null, null);
        }
    }
    
    // remove button clicked
    public void remove(){
        int selectedIndex = OrderTable.getSelectionModel().getSelectedIndex(); // get the current index
        if (selectedIndex >= 0) { // if the user selected a row
            pizza currPizza = OrderTable.getItems().get(selectedIndex); // get the selected user
            delPizza(currPizza); // delete the user
            initialize(null, null); // refresh table
        } else {
            Popup popup = popUp("Select an order to delete"); // tell the user to select a row
            popup.show(primStage);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeText.setText("Welcome " + loginController.userName);
        // gets the items and their prices
        ObservableList<pizza> observableList = FXCollections.observableArrayList(fetchPizzas());
        size.setCellValueFactory(new PropertyValueFactory<>("Size")); // ties the item columns to the item names
        toppings.setCellValueFactory(new PropertyValueFactory<>("Toppings")); // same but for price column
        sodas.setCellValueFactory(new PropertyValueFactory<>("Sodas"));
        OrderTable.setItems(observableList); // pushes the data into the table
    }

    // gets the list of the current pizzas from the data base
    private static List<pizza> fetchPizzas() {
        List<String> pizzas = new ArrayList<>(); // create a list to hold the prices
        try {
            Scanner s = new Scanner(new File("src/pizzas.txt")); // open the prices database
            while (s.hasNextLine()) {
                pizzas.add(s.nextLine()); // add each item to the list
            }
            s.close(); // close the data base
        } catch (FileNotFoundException e) {
        }
        String[] curr; // hold the current item
        List<pizza> Pizzas = new ArrayList<pizza>(); // create a list of items
        for (int i = 0; i < pizzas.size(); i++) { // for each item
            curr = pizzas.get(i).split(";"); // split up their attributes
            // create an item obj out of them and add it to the item list
            Pizzas.add(new pizza(curr[0], curr[1], curr[2]));
        }
        return Pizzas; // return the list of item objs
    }

    // deletes one pizza from the data base
    private void delPizza(pizza pizza){
        List<pizza> pizzaList = fetchPizzas(); // get the list of pizzas
        // for each pizza
        // makes sure we only delete one pizza, so that exact duplicates don't all get deleted
        boolean pizzaDeleted = false; 
        for (int i = 0; i < pizzaList.size(); i++) {
            if ((pizza.getSize().equals(pizzaList.get(i).getSize())) && (pizza.getToppings().equals(pizzaList.get(i).getToppings()) && (pizza.getSodas().equals(pizzaList.get(i).getSodas()))) && !pizzaDeleted) { // if their attributes match
                pizzaList.remove(i); // remove the pizza from the list
                pizzaDeleted = true;
            }
        }
        // delete the database
        File file = new File("src/pizzas.txt");
        file.delete();
        // for each pizza in the list
        for (int i = 0; i < pizzaList.size(); i++) {
            // add it to the database
            try {
                FileWriter fw = new FileWriter("src/pizzas.txt", true); // open the pizza database
                fw.write(pizzaList.get(i).getSize() + ';' + pizzaList.get(i).getToppings() + ';' + pizzaList.get(i).getSodas() + ";" + "\n"); // add the new pizza
                fw.close(); // close the database
            } catch (IOException e) {
            }
        }
    }
}