package GUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;


// Main Class - starts the application
public class Main extends Application {
public static Stage primStage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("root.fxml"));
        try{
            StackPane rootPane;
            rootPane = loader.load();
            GuiManager guiModel = GuiManager.getInstance();
            guiModel.setRootPane(rootPane);
            Scene scene = new Scene(rootPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("PizzaPOS");
            primaryStage.setMaximized(true);
            primaryStage.show();
            primStage = primaryStage;
            guiModel.changeWindow("login.fxml");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }

    // Standard function to check if a String is an Integer, much faster than Integer.valueOf()
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
    
    public static Popup popUp(String text){
        Label label = new Label(text); 
        Popup popup = new Popup();   
        label.setStyle(" -fx-background-color: white; -fx-alignment: center;"); 
        label.setTextFill(Color.rgb(210, 39, 30));
        popup.getContent().add(label); 
        label.setMinWidth(150); 
        label.setMinHeight(75);
        label.setAlignment(Pos.TOP_CENTER);
        popup.setAutoHide(true);
        return popup;
    }
}