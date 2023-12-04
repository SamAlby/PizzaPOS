package GUI;

import java.io.File;

// Controller Class for Login screen
public class checkoutController extends Main {

    // back button
    public void back(){
        GuiManager.getInstance().changeWindow("createOrder.fxml");
    }
    //Logout button onAction method
    public void Logout(){
        GuiManager.getInstance().changeWindow("login.fxml");
        File file = new File("src/pizzas.txt"); // delete the database
        file.delete();
    }

    // if new order
    public void newOrder(){
        File file = new File("src/pizzas.txt");
        file.delete();
        GuiManager.getInstance().changeWindow("createOrder.fxml");
    }
    
    public void initialize() throws Exception {
        
    }
}