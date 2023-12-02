package GUI;
import javafx.fxml.FXML;
import javafx.stage.Popup;
import javafx.scene.text.Text;
// Controller Class for createOrder screen
public class createOrderController extends Main {
    @FXML private Text welcomeText;
    public static GUI.Receipt receipt = new Receipt();
    
    // Logout button onAction method
    public void logOut(){
        GuiManager.getInstance().changeWindow("login.fxml");
    }
    // Admin options button onAction method
    public void adminOptions(){
        if(loginController.isAdmin)
            GuiManager.getInstance().changeWindow("adminOptions.fxml");
        else{
            Popup popup = popUp("Please login as admin");
            popup.show(primStage);
        }
    }
    public void initialize() throws Exception {
        welcomeText.setText("Welcome " + loginController.userName);
    }

}