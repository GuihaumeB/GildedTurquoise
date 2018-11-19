package edu.insightr.gildedrose;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/interface.fxml"));
            Scene scene = new Scene(root, 300, 275);
            scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());
            primaryStage.setTitle("Gilded Rose UI");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
