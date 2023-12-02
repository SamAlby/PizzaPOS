package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.awt.event.KeyEvent;

public class loginController extends Main {

    @FXML
    private PasswordField passfield;
    

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public void initialize() throws Exception {
        passfield.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
            Scanner sc = new Scanner(new File("src/users.txt"));
            sc.useDelimiter(",");
            if(!newValue.equals(""))
            {
                while (sc.hasNext())
                {
                    String x=sc.next();
                    if(isInteger(x) && x.equals(newValue)){
                        GuiManager.getInstance().changeWindow("createOrder.fxml");
                    }
                }
            }
            }catch(FileNotFoundException e)
            {
            }
            if(passfield.getLength()==4)
                javafx.application.Platform.runLater(() -> {passfield.clear();});
        });
    }

}