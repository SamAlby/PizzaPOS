package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Controller Class for Login screen
public class loginController extends Main {

    @FXML private PasswordField passfield; // PasswordField fetched from FXML file for logout
    
    public static Boolean isAdmin = false; // Global Boolean variable that gets updated when a user logs in as admin
    public static String userName; // Global String variable that gets updated when a user logs in
    
    // if user exits software
    public void exit(){
        File file = new File("src/pizzas.txt"); // deletes current pizza order database
        file.delete();
        primStage.close(); // close the program
    }
    public void initialize() throws Exception {
        //Add event listener for text being typed into password field
        passfield.textProperty().addListener((observable, oldValue, newValue) -> {
            try{ //Try catch in case users.txt is not found
            Scanner sc = new Scanner(new File("src/users.txt")); //open user database
            if(!newValue.equals("")){ //Make sure not to check values if the input is blank, prevents exception
                while (sc.hasNextLine()){ //Loop through entirety of user database
                    String[] user = sc.nextLine().split(",");  //split the line by commas
                    userName = user[0];  //get the user's name       
                    if(isInteger(user[1]) && user[1].equals(newValue)){ //If password field entry matches an ID
                        GuiManager.getInstance().changeWindow("createOrder.fxml"); //Change to next screen
                        File file = new File("src/pizzas.txt"); // deletes current pizza order database
                        file.delete();
                    }
                }
            }
            sc.close(); //Close Scanner to remove resource leak
            }catch(FileNotFoundException e){ //Catch file not found in case users.txt goes missing
            }
            if(passfield.getLength()==4){ //Reset length of the password field if > 4 digits
                javafx.application.Platform.runLater(() -> {passfield.clear();}); // runLater() trick to avoid exception in lambda function above
            }
        });
    }

}