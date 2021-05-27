package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socket.InputOutPut;

public class WelcomePageController implements Initializable {
    private InputOutPut io ;
    
    @FXML
    private TextField input;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			io = new InputOutPut(Main.socket);
		} catch (IOException e) {
		}
		
	}
	
	@FXML
	public void connect(ActionEvent event) {
		
			try {
				io.getPrintWriter().println(input.getText());
				
				if(io.getBufferedReader().readLine().equals("token:")) {
					Alert alert = new Alert(AlertType.INFORMATION);
				    alert.setTitle("User's Information");
				    alert.setHeaderText(null);
				    alert.setContentText("This username is token try another one");
				    alert.showAndWait();    	
				}
				else {
					FileWriter myWriter = new FileWriter("src/db.txt");
					myWriter.write(input.getText());
					myWriter.close();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("../presentation/sample.fxml"));
					Parent root = loader.load();
					Scene rootScene = new Scene(root);
					rootScene.getStylesheets().add(getClass().getResource("../resources/css/style.css").toString());
					
					//access the controller and call a method
					Controller controller = loader.getController();
					controller.getConnectedUser(input.getText());
					Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					stage.setScene(rootScene);
					stage.show();
				}
			} catch (IOException e) {}
		
	}
	
	public void exit(MouseEvent event) {
    	try {
			Main.socket.close();
			System.exit(0);
		} catch (IOException e) {}
    	
    }

	
}
