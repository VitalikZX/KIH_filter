package kih;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("KIH_filter");
        primaryStage.setScene(new Scene(root, 1600, 800));
//        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream( "JavaDuke_m.png")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
