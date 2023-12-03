package GUI;

import javafx.stage.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.control.TableColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

// Controller Class for modifyUsers screen
public class modifyUsersController extends Main implements Initializable {

    @FXML private TextField EnterUserName;
    @FXML private TextField EnterPin;
    @FXML private CheckBox adminCheck;
    @FXML private TableView<user> UserTable;
    @FXML private TableColumn<user, String> UserName;
    @FXML private TableColumn<user, String> ID;
    @FXML private TableColumn<user, Boolean> Permissions;

    // if click logout, go to login page
    public void LogoutModifyUser() {
        GuiManager.getInstance().changeWindow("login.fxml");
    }

    // if click back, go to admin options page
    public void BackModifyUser() {
        GuiManager.getInstance().changeWindow("adminOptions.fxml");
    }

    // if click submit user
    public void SubmitNewUser() {
        String userName = EnterUserName.getText(); // get the text from the user textbox
        String id = EnterPin.getText(); // get the text from the pin textbox
        Boolean admin = adminCheck.isSelected(); // get the value from the admin check box
        if (!isInteger(id) || id.length() != 4) { // if its not a 4 digit number
            Popup popup = popUp("Please enter a 4 digit pin"); // let the user know
            popup.show(primStage);
        } else if (userName.equals("")) { // if the username box is blank
            Popup popup = popUp("Please enter a user name"); // let the user know
            popup.show(primStage);
        } else {// if the inputs are correct
            try {
                FileWriter fw = new FileWriter("src/users.txt", true); // open the user database
                fw.write(userName + ',' + id + ',' + admin + ',' + "\n"); // add the new user
                fw.close(); // close the database
            } catch (IOException e) {
            }
            // clear the boxes for the next new user
            EnterUserName.clear();
            EnterPin.clear();
            adminCheck.setSelected(false);
            // update the table
            initialize(null, null);
        }
    }

    // if click remove user
    public void DeleteUser() {
        int selectedIndex = UserTable.getSelectionModel().getSelectedIndex(); // get the current index
        user currUser = UserTable.getItems().get(selectedIndex); // get the selected user
        delUser(currUser); // delete the user
        initialize(null, null); // refresh table
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<user> observableList = FXCollections.observableArrayList(fetchUsers()); // create the list of objects to add to table
        UserName.setCellValueFactory(new PropertyValueFactory<>("Name")); // ties the name column to user names
        ID.setCellValueFactory(new PropertyValueFactory<>("Id")); // same but for id column
        Permissions.setCellValueFactory(new PropertyValueFactory<>("Admin")); // same but for admin column
        UserTable.setItems(observableList); // pushes the data into the table
    }

    // fetches a list of the users from the data base
    private static List<user> fetchUsers() {
        List<String> userNames = new ArrayList<>(); // create a list to hold the users
        try {
            Scanner s = new Scanner(new File("src/users.txt")); // open the user database
            while (s.hasNextLine()) {
                userNames.add(s.nextLine()); // add each user to the list
            }
            s.close(); // close the data base
        } catch (FileNotFoundException e) {
        }
        String[] curr; // hold the current user
        List<user> users = new ArrayList<user>(); // create a list of users
        for (int i = 0; i < userNames.size(); i++) { // for each user
            curr = userNames.get(i).split(","); // split up their attributes
            users.add(new user(curr[0], curr[1], Boolean.valueOf(curr[2]))); // create a user obj out of them and add it to the user list
        }
        return users; // return the list of user objs
    }

    // deletes the user from the data base
    private static void delUser(user user) {
        List<user> userList = fetchUsers(); // get the list of users
        
        // for each user
        for (int i = 0; i < userList.size(); i++) {
            if ((user.getName().equals(userList.get(i).getName())) && (user.getId().equals(userList.get(i).getId())) // if their attributes match the select user
                    && (user.getAdmin() == userList.get(i).getAdmin())) {
                userList.remove(i); // remove the user from the list
            }
        }

        // delete the database
        File file = new File("src/users.txt");
        file.delete();

        // for each user in the list
        for (int i = 0; i < userList.size(); i++) {
            // add it to the database
            try {
                FileWriter fw = new FileWriter("src/users.txt", true); // open the user database
                fw.write(userList.get(i).getName() + ',' + userList.get(i).getId() + ','+ userList.get(i).getAdmin() + ',' + "\n"); // add the new user
                fw.close(); // close the database
            } catch (IOException e) {
            }
        }

    }
}