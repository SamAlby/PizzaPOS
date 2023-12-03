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
    @FXML private TextField enterSearch;
    @FXML private TextField EditUserName;
    @FXML private TextField pinEdit;
    @FXML private TableView<user> UserTable;
    @FXML private TableColumn<user, String> UserName;
    @FXML private TableColumn<user, String> ID;

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
        List<user> userList = fetchUsers(); // get the list of users in the database
        boolean matchedPin = false; // keeps track of whether the pin matches any in the database
        for (int i = 0; i < userList.size(); i++) { // for every user in the database
            if (id.equals(userList.get(i).getId())) { // if the pin matches
                matchedPin = true; // set match to true
            }
        }
        if (matchedPin) {
            Popup popup = popUp("This pin has been taken"); // let the user know the pin is taken
            popup.show(primStage);
        } else if (!isInteger(id) || id.length() != 4) { // if its not a 4 digit number
            Popup popup = popUp("Please enter a 4 digit pin"); // let the user know
            popup.show(primStage);
        } else if (userName.equals("")) { // if the username box is blank
            Popup popup = popUp("Please enter a user name"); // let the user know
            popup.show(primStage);
        } else {// if the inputs are correct
            try {
                FileWriter fw = new FileWriter("src/users.txt", true); // open the user database
                fw.write(userName + ',' + id + ',' + "\n"); // add the new user
                fw.close(); // close the database
            } catch (IOException e) {
            }
            // clear the boxes for the next new user
            EnterUserName.clear();
            EnterPin.clear();
            // update the table
            String search = enterSearch.getText();
            if (search.equals(""))
                initialize(null, null);
        }
    }

    // if click remove user
    public void DeleteUser() {
        int selectedIndex = UserTable.getSelectionModel().getSelectedIndex(); // get the current index
        if (selectedIndex >= 0) { // if the user selected a row
            user currUser = UserTable.getItems().get(selectedIndex); // get the selected user
            delUser(currUser); // delete the user
            initialize(null, null); // refresh table
        } else {
            Popup popup = popUp("Select a user to delete"); // tell the user to select a row
            popup.show(primStage);
        }
    }

    // if click search
    public void Search() {
        String userName = enterSearch.getText(); // get the entered value
        List<user> userList = fetchUsers(); // get users from the database
        boolean userFound = false; // keeps track of if the user has been found
        List<user> foundUsers = new ArrayList<user>();
        for (int i = 0; i < userList.size(); i++) { // for every user in the database
            // if a user hasn't been found, and the name or id match
            if ((userName.equals(userList.get(i).getName()) || userName.equals(userList.get(i).getId()))) {
                foundUsers.add(userList.get(i)); // add each found user to the list of found users
                userFound = true; // mark that at least one user was found
            } else if (!userFound && i == userList.size() - 1) { // if a user wasn't found
                initialize(null, null); // reset the table
                if (!userName.equals("")) { // if the search bar wasn't blank
                    Popup popup = popUp("No results"); // let the user know there were no results
                    popup.show(primStage);
                }
            }
        }
        if (userFound) { // if a user was found
            // populate table with list of found users
            ObservableList<user> observableList = FXCollections.observableArrayList(foundUsers);
            UserTable.setItems(observableList); // pushes the data into the table
        }
    }

    // if click submit edit
    public void SubmitEdit() {
        int selectedIndex = UserTable.getSelectionModel().getSelectedIndex(); // see what row they selected
        if (selectedIndex >= 0) { // if they selected a row
            if (!EditUserName.getText().equals("")) { // if the username isn't blank
                if (!pinEdit.getText().equals("")) { // if the pin isn't blank
                    // create a user based off the edit
                    user user = new user(EditUserName.getText(), pinEdit.getText()); 
                    List<user> userList = fetchUsers(); // get users from the database
                    boolean matchedPin = false;
                    for (int i = 0; i < userList.size(); i++) { // for every user in the database
                        if (user.getId().equals(userList.get(i).getId()) && !user.getName().equals(userList.get(i).getName())) { // if the pin matches
                            matchedPin = true; // set match to true
                        }
                    }
                    if (matchedPin) {
                        Popup popup = popUp("This pin has been taken"); // let the user know the pin is taken
                        popup.show(primStage);
                    }else if (!isInteger(user.getId()) || user.getId().length() != 4) { // if its not a 4 digit number
                        Popup popup = popUp("Please enter a 4 digit pin"); // let the user know
                        popup.show(primStage);
                    } else { // if the inputs are correct
                        DeleteUser(); // delete the stored version of the user
                        try {
                            FileWriter fw = new FileWriter("src/users.txt", true); // open the user database
                            // add the new version of the user
                            fw.write(user.getName() + ',' + user.getId() + ',' + "\n"); 
                            fw.close(); // close the database
                        } catch (IOException e) {
                        }
                        // clear the boxes for the next edit
                        EditUserName.clear();
                        pinEdit.clear();
                        // update the table
                        Search();
                    }
                } else {
                    Popup popup = popUp("Please enter a pin"); // if the pin was blank
                    popup.show(primStage);
                }
            } else {
                Popup popup = popUp("Please enter a name"); // if the name was blank
                popup.show(primStage);
            }
        } else {
            Popup popup = popUp("Please select a user from the table to edit"); // if they didn't select a user to edit
            popup.show(primStage);
        }
    }

    // if click edit user
    public void EditUser() {
        int selectedIndex = UserTable.getSelectionModel().getSelectedIndex(); // get the current index
        if (selectedIndex >= 0) { // if the user selected a row
            user currUser = UserTable.getItems().get(selectedIndex); // get the selected user
            EditUserName.setText(currUser.getName()); // fill in the text fields
            pinEdit.setText(currUser.getId());
        }
    }

    // if click clear
    public void Clear(){
        initialize(null, null); // reload the table
        enterSearch.setText(""); // empty the search bar
    }
    // set the table with all current users in the database
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // create the list of objects to add to table
        ObservableList<user> observableList = FXCollections.observableArrayList(fetchUsers());
        UserName.setCellValueFactory(new PropertyValueFactory<>("Name")); // ties the name column to user names
        ID.setCellValueFactory(new PropertyValueFactory<>("Id")); // same but for id column
        UserTable.setItems(observableList); // pushes the data into the table
    }

    // fetches a list of user objects from the users from the data base
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
            // create a user obj out of them and add it to the user list
            users.add(new user(curr[0], curr[1]));
        }
        return users; // return the list of user objs
    }

    // deletes the user from the data base
    private void delUser(user user) {
        List<user> userList = fetchUsers(); // get the list of users
        // for each user
        // makes sure we only delete one user, so that exact duplicates don't all get deleted
        boolean userDeleted = false; 
        for (int i = 0; i < userList.size(); i++) {
            if ((user.getName().equals(userList.get(i).getName())) && (user.getId().equals(userList.get(i).getId()))
                    && !userDeleted) { // if their attributes match
                userList.remove(i); // remove the user from the list
                userDeleted = true;
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
                fw.write(userList.get(i).getName() + ',' + userList.get(i).getId() + ',' + "\n"); // add the new user
                fw.close(); // close the database
            } catch (IOException e) {
            }
        }
        //Clear the edit fields
        EditUserName.clear();
        pinEdit.clear();

    }
}