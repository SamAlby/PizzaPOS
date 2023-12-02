package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;

public class GuiManager {

  private StackPane rootPane;
  private static GuiManager instance;

  public static GuiManager getInstance() {
    if (instance == null) {
      instance = new GuiManager();
    }
    return instance;
  }

  private GuiManager() {}

  public void changeWindow(String path) {
    changeWindow(rootPane, path, this);
    rootPane.setPrefWidth(-1);
    rootPane.setPrefHeight(-1);
  }

  public static void changeWindow(Pane pane, String newWindowPath, Object callingController) {
    Window window = pane.getScene().getWindow();
    double x = window.getX() + getHorizontalMidpoint(window);
    double y = window.getY() + getVerticalMidpoint(window);

    ObservableList<Node> childrenList = pane.getChildren();
    removeAllIncludedChildren(childrenList);

    FXMLLoader loader = new FXMLLoader(callingController.getClass().getResource(newWindowPath));

    try {
      pane.getChildren().add(loader.load());
      Stage primaryStage = (Stage) window;
      primaryStage.setMinHeight(0);
      primaryStage.setMinWidth(0);
      window.sizeToScene();
      window.setX(x - getHorizontalMidpoint(window));
      window.setY(y - getVerticalMidpoint(window));
      primaryStage.setMinHeight(window.getHeight());
      primaryStage.setMinWidth(window.getWidth());
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  private static double getHorizontalMidpoint(Window window) {
    int horizontalBisectionCoefficient = 2;
    return window.getWidth() / horizontalBisectionCoefficient;
  }

  private static double getVerticalMidpoint(Window window) {
    int verticalBisectionCoefficient = 2;
    return window.getHeight() / verticalBisectionCoefficient;
  }

  private static void removeAllIncludedChildren(ObservableList<Node> childrenList) {
    for (int childIndex = 0; childIndex < childrenList.size(); childIndex++) {
      childrenList.remove(childIndex);
    }
  }

  public void setRootPane(StackPane rootPane) {
    this.rootPane = rootPane;
  }
}