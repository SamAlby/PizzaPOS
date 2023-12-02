package GUI;

import javafx.stage.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.control.TableColumn;
import java.util.ArrayList;
import java.util.List;

// Controller Class for modifyUsers screen
public class modifyUsersController extends Main {

    @FXML private TextField EnterUserName;
    @FXML private TextField EnterPin;
    @FXML private CheckBox adminCheck;
    @FXML private TableView UserTable;

    //if click logout, go to login page
    public void LogoutModifyUser(){
        GuiManager.getInstance().changeWindow("login.fxml");
    }
    //if click back, go to admin options page
    public void BackModifyUser(){
        GuiManager.getInstance().changeWindow("adminOptions.fxml");
    }
    //if click submit user
    public void SubmitNewUser(){
        String userName = EnterUserName.getText(); //get the text from the user textbox
        String id = EnterPin.getText(); //get the text from the pin textbox
        Boolean admin = adminCheck.isSelected(); //get the value from the admin check box
        if (!isInteger(id) || id.length() != 4){ //if its not a 4 digit number
            Popup popup = popUp("Please enter a 4 digit pin"); //let the user know
            popup.show(primStage); 
        }else if(userName.equals("")) { //if the username box is blank
            Popup popup = popUp("Please enter a user name"); //let the user know
            popup.show(primStage);
        }else{//if the inputs are correct
            try{
                FileWriter fw = new FileWriter("src/users.txt",true); // open the user database
                fw.write("\n"+userName+','+id+','+admin+','); //add the new user
                fw.close(); //close the database
            }catch(IOException e){ 
            }
            //clear the boxes for the next new user
            EnterUserName.clear();
            EnterPin.clear();
            adminCheck.setSelected(false);
        }
    }

    public void initialize() throws Exception {
        List <String> userNames = new ArrayList<>(); //create a list to hold the users
        try{
            Scanner s = new Scanner(new File("src/users.txt")); //open the user database
            while (s.hasNextLine()){
                userNames.add(s.nextLine());   //add each user to the list     
            }
            s.close(); //close the data base           
        }catch(FileNotFoundException e){
        }
        
        for (String user : userNames){ //add each user to the table
            UserTable.getItems().add(user);
        }
        UserTable.getItems().add("1");
    }
    
}