package controlor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../presentation/sample.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Lets talk");
        Scene scene = new Scene(root, 600, 450);
        scene.getStylesheets().add(getClass().getResource("../resources/css/style.css").toString());
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
