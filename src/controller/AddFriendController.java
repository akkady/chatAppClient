package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFriendController {
	
	@FXML
	private TextField input;
	
	public void sendUser(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../presentation/sample.fxml"));
		Parent root = loader.load();
		Scene rootScene = new Scene(root);
		
		
		//access the controller and call a method
		Controller controller = loader.getController();
		Stage stage = new Stage();
		stage.setScene(rootScene);
		stage.show();
	}

}
