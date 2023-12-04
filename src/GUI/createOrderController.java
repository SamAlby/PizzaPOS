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

    // Checkout button
    public void checkOut(){
        List<pizza> pizzas = fetchPizzas(); 
        if(pizzas.size() > 0){
            GuiManager.getInstance().changeWindow("checkout.fxml");
        }else{
            Popup popup = popUp("Add a pizza to the order"); // tell the user to select a pizza
            popup.show(primStage);
        }
    }

    // Logout button onAction method
    public void logOut() {
        GuiManager.getInstance().changeWindow("login.fxml");
        File file = new File("src/pizzas.txt"); // delete the database
        file.delete();
    }

    // Admin options button onAction method
    public void adminOptions() {
        GuiManager.getInstance().changeWindow("adminOptions.fxml");
    }

    public void addPepperoni(){
        addTopping("pepperoni");
    }
    public void addOnions(){
        addTopping("onions");
    }
    public void addPineapple(){
        addTopping("pineapple");
    }
    public void addMushrooms(){
        addTopping("mushrooms");
    }
    public void addExtraCheese(){
        addTopping("extracheese");
    }
    public void addSausage(){
        addTopping("sausage");
    }
    public void addBacon(){
        addTopping("bacon");
    }
    public void addPeppers(){
        addTopping("peppers");
    }

    public void addTopping(String topping) {
        // if adding a topping to a pizza
        int selectedIndex = OrderTable.getSelectionModel().getSelectedIndex(); // get the selected pizza index
        if (selectedIndex >= 0) { // if they selected a pizza
            pizza currPizza = OrderTable.getItems().get(selectedIndex); // get the selected pizza
            delPizza(currPizza); // delete it from the data base
            if (!currPizza.getToppings().contains(topping)) { // if it doesn't have the topping
                currPizza.setToppings(currPizza.getToppings()+topping+","); // give it the topping
            } else { // remove the topping it already has
                currPizza.setToppings(currPizza.getToppings().replace(topping+",",""));
            }
            // save it to the database
            try {
                FileWriter fw = new FileWriter("src/pizzas.txt", true); // open the pizza database
                fw.write(currPizza.toString()); // add the updated pizza
                fw.close(); // close the database
            } catch (IOException e) {
            }
            initialize(null, null); // refresh the table
        } else { // if no row selected
            Popup popup = popUp("Select a pizza to add toppings to"); // tell the user to select a pizza
            popup.show(primStage);
        }
    }

    // Add soda button clicked
    public void addSoda() {
        // if adding a soda to an order
        int selectedIndex = OrderTable.getSelectionModel().getSelectedIndex(); // get the selected index
        if (selectedIndex >= 0) { // if they selected a pizza
            pizza currPizza = OrderTable.getItems().get(selectedIndex); // get the selected pizza
            delPizza(currPizza); // delete it from the data base
            if (currPizza.getSodas().equals("none")) { // if it doesn't have a soda
                currPizza.setSodas("1"); // give it one
            } else { // add it to the sodas it has
                int sodas = Integer.parseInt(currPizza.getSodas());
                sodas += 1;
                currPizza.setSodas(Integer.toString(sodas)); // add the soda to the pizza
            }
            // save it to the database
            try {
                FileWriter fw = new FileWriter("src/pizzas.txt", true); // open the pizza database
                fw.write(currPizza.toString()); // add the updated pizza
                fw.close(); // close the database
            } catch (IOException e) {
            }
            initialize(null, null); // refresh the table
        } else { // if adding a soda in general
            pizza pizza = new pizza(null, null, "1");
            try {
                FileWriter fw = new FileWriter("src/pizzas.txt", true); // open the pizza database
                // add the new pizza
                fw.write(";;" + pizza.getSodas() + ";" + "\n");
                fw.close(); // close the database
            } catch (IOException e) {
            }
            initialize(null, null);
        }
    }

    // small pizza added
    public void addSmallPizza() {
        addPizza("small");
    }

    // medium pizza added
    public void addMediumPizza() {
        addPizza("medium");
    }

    // large pizza added
    public void addLargePizza() {
        addPizza("large");
    }

    // remove button clicked
    public void remove() {
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

    // clear button clicked
    public void clear(){
        File file = new File("src/pizzas.txt"); // delete the database
        file.delete();
        initialize(null, null); // reload the table
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
    private void delPizza(pizza pizza) {
        List<pizza> pizzaList = fetchPizzas(); // get the list of pizzas
        // for each pizza
        // makes sure we only delete one pizza, so that exact duplicates don't all get
        // deleted
        boolean pizzaDeleted = false;
        for (int i = 0; i < pizzaList.size(); i++) {
            if ((pizza.getSize().equals(pizzaList.get(i).getSize()))
                    && (pizza.getToppings().equals(pizzaList.get(i).getToppings())
                            && (pizza.getSodas().equals(pizzaList.get(i).getSodas())))
                    && !pizzaDeleted) { // if their attributes match
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
                fw.write(pizzaList.get(i).getSize() + ';' + pizzaList.get(i).getToppings() + ';'
                        + pizzaList.get(i).getSodas() + ";" + "\n"); // add the new pizza
                fw.close(); // close the database
            } catch (IOException e) {
            }
        }
    }

    // pizza added
    private void addPizza(String size) {
        pizza newPizza = new pizza(size, "", "0");
        try {
            FileWriter fw = new FileWriter("src/pizzas.txt", true); // open the pizza database
            // add the new pizza
            fw.write(newPizza.toString());
            fw.close(); // close the database
        } catch (IOException e) {
        }
        initialize(null, null);
    }
}
