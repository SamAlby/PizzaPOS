package GUI;

import java.io.File;

//Controller Class for adminOptions screen
public class adminOptionsController extends Main {

    //Logout button onAction method
    public void adminLogout(){
        GuiManager.getInstance().changeWindow("login.fxml");
        File file = new File("src/pizzas.txt"); // delete the database
        file.delete();
    }
    //Back button onAction method
    public void adminBack(){
        GuiManager.getInstance().changeWindow("createOrder.fxml");
    }
    //Modify users button onAction method
    public void modifyUsers(){
        GuiManager.getInstance().changeWindow("modifyUsers.fxml");
    }
    //Update prices button onAction method
    public void updatePrices(){
        GuiManager.getInstance().changeWindow("updatePrices.fxml");
    }
    public void initialize() throws Exception {
       
    }

}