package GUI;

import javafx.fxml.FXML;
/*
import javafx.scene.control.PasswordField;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.KeyEvent;
*/

public class createOrderController extends Main {

    @FXML
    
    public void logOut(){
        GuiManager.getInstance().changeWindow("login.fxml");
    }
    public void initialize() throws Exception {
       
    }

}