package GUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

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
            guiModel.changeWindow("login.fxml");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}