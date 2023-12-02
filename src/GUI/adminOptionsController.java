package GUI;

//Controller Class for adminOptions screen
public class adminOptionsController extends Main {

    //Logout button onAction method
    public void adminLogout(){
        GuiManager.getInstance().changeWindow("login.fxml");
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