package GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

// Controller Class for Login screen
public class checkoutController extends Main {
    private float tax = 0;
    private int total = 0;
    private float grandTotal = 0;
    @FXML Text Total;
    @FXML Text Tax;
    @FXML Text GrandTotal;

    // back button
    public void back(){
        GuiManager.getInstance().changeWindow("createOrder.fxml"); // go back to order 
    }
    //Logout button onAction method
    public void Logout(){
        GuiManager.getInstance().changeWindow("login.fxml"); // go back to log in
        File file = new File("src/pizzas.txt"); // delete the database
        file.delete();
    }

    // if new order
    public void newOrder(){
        File file = new File("src/pizzas.txt"); // delete the database
        file.delete();
        GuiManager.getInstance().changeWindow("createOrder.fxml"); // go back to create order
    }
    
    public void initialize() throws Exception {
        determineTotal(); // gets the total prices and displays them

    }

    //determines the total prices
    private void determineTotal(){
        List<pizza> pizzas = fetchPizzas(); // get the pizzas
        int small = fetchPrice("small"); // get the price of a small pizza
        int medium = fetchPrice("medium"); // etc 
        int large = fetchPrice("large"); 
        int topping = fetchPrice("toppings");
        int soda = fetchPrice("soda");
        float taxRate = fetchTaxRate("tax rate"); // get the tax rate
        for (int i = 0; i < pizzas.size(); i++){ // for every pizza
            String[] toppings;
            if (pizzas.get(i).getSize().equals("small")){ // if its a small pizza
                total += small; // add the price to the total
            } else if (pizzas.get(i).getSize().equals("medium")){ // etc
                total += medium;
            }else if (pizzas.get(i).getSize().equals("large")){ // etc
                total += large;
            }
            if (pizzas.get(i).getToppings().length() > 0){ // if they have toppings
                toppings = pizzas.get(i).getToppings().split(","); // get the toppings
                total += toppings.length * topping; // multiple the amount of toppings by the price per topping
            }
            if (pizzas.get(i).getSodas().length() > 0){ // add the price of each soda
                for (int j = 0; j < Integer.parseInt(pizzas.get(i).getSodas()); j++){
                    total += soda;
                }
            }
        }
        tax = total * taxRate; // get the amount of tax the customer must pay
        grandTotal = total + tax; // get the grand total
        Total.setText(Integer.toString(total)); // display the total
        Tax.setText(Float.toString(tax)); // display the tax to be paid
        GrandTotal.setText(Float.toString(grandTotal)); // display the grand total
    }

    // gets the price of an item from the database
    private static int fetchPrice(String item){
        int price = 0;
        List<item> items = fetchPrices();
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).getItem().equals(item)){
                price = Integer.parseInt(items.get(i).getPrice());
            }
        }
        return price;
    }

    // gets the tax rate from the database
    private static float fetchTaxRate(String item){
        float tax = 0;
        List<item> items = fetchPrices();
        for (int i = 0; i < items.size(); i++){
            if (items.get(i).getItem().equals(item)){
                tax = Float.parseFloat(items.get(i).getPrice());
            }
        }
        return tax;
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

    // gets the list of the current prices from the data base
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
}