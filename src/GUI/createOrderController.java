package GUI;

import java.io.File;
import java.io.FileNotFoundException;
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

    // Logout button onAction method
    public void logOut() {
        GuiManager.getInstance().changeWindow("login.fxml");
    }

    // Admin options button onAction method
    public void adminOptions() {
            GuiManager.getInstance().changeWindow("adminOptions.fxml");
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

    private static List<pizza> fetchPizzas(){
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
}