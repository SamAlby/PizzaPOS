package GUI;

// Controller Class for updatePrices screen
public class updatePricesController extends Main {
    // Update Prices button onAction method
    public void backUpdatePrices(){
        GuiManager.getInstance().changeWindow("adminOptions.fxml");
    }
    // Logout button onAction method
    public void logoutUpdatePrices(){
        GuiManager.getInstance().changeWindow("login.fxml");
    }
    public void initialize() throws Exception {
       
    }

}