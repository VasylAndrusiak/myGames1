
package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Scene sce1, sce2;
    @Override

    public void start(Stage primaryStage) throws Exception  {
        window = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        sce1 = new Scene(root);
        primaryStage.setScene(sce1);
        primaryStage.setTitle("Main");
        primaryStage.show();
        //------------------------------------

    }
    public static void main(String[] args) {
        launch(args);

    }
}
