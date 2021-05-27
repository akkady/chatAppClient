package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import socket.InputOutPut;
import socket.User;

public class Controller implements Initializable {
    private InputOutPut io ;
    private boolean userExiste;
    @FXML
    private Label username ;
    @FXML
    private TextField msgInput ;
    @FXML
    private VBox chatSection ;
    @FXML
    private VBox usersSection;
    @FXML
    private TextField friendInput;
    @FXML
    private Label userTarget;
    @FXML
    private VBox chatSectionScroll;
 
    private boolean shutDown = false;
    private User user = Main.user;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            io = new InputOutPut(Main.socket);
            if(user.getUserName() != null) {
            	username.setText(username.getText()+user.getUserName());
            	io.getPrintWriter().println("old:"+user.getUserName());
            }
            loadFriends(user.getFriendsList());
            new InputWait().start();
        } catch (IOException e) {
        }
    	
    	
    	

    }
    
    public void exit(MouseEvent event) {
    	try {
    		shutDown = true;
    		io.getPrintWriter().println("*s*");
			
			System.exit(0);
		} catch (Exception e) {
		}
    	
    }
    
    public void loadFriends(ArrayList<String> friends) {
    	for (String friend : friends) {
    		Label lbl = new Label(friend);
    		lbl.getStyleClass().add("friendLabel");
    		lbl.setOnMouseClicked((mouseEvent) -> {
    	       getTarget(mouseEvent);
    	    });
			usersSection.getChildren().add(lbl);

		}
    }
    
    
    
    
    
    
    
    public void addContact(ActionEvent event){
    	String userName = friendInput.getText();
    	if(userName.isEmpty()){
    		Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("User's Information");
            alert.setHeaderText(null);
            alert.setContentText("Insert your friend's username in the text field !");
            alert.showAndWait();    	
        } else{
    		if( !user.getFriendsList().contains(userName)) {
    			io.getPrintWriter().println(userName);
    			try {
					Thread.currentThread().sleep(3000);
				} catch (InterruptedException e) {}
    			if(userExiste) {
    				try {
	    				FileWriter myWriter = new FileWriter("src/friendList.txt",true);
						myWriter.write(userName+"\n");
					    myWriter.close();
					    user.setFriendsList(userName);
	        			Label lbl = new Label(userName);
	        			lbl.getStyleClass().add("friendLabel");
	        			lbl.setOnMouseClicked((mouseEvent) -> {
	        		       getTarget(mouseEvent);
	        		    });
	    				usersSection.getChildren().add(lbl);
					} catch (IOException e) {}
    			} else {
    				Alert alert = new Alert(AlertType.INFORMATION);
    	            alert.setTitle("User's Information");
    	            alert.setHeaderText(null);
    	            alert.setContentText("There's no such username 😕");
    	            alert.showAndWait();
    			}
    			
    		} else if (user.getFriendsList() ==null ){
    			Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("User's Information");
                alert.setHeaderText(null);
                alert.setContentText("u already have this friend on ur list");
                alert.showAndWait();    	
    		}
    	}
    }
    
    public void getConnectedUser(String name) {
    	username.setText(username.getText()+name);
    }
    
    public void getTarget(MouseEvent mouseEvent) {
    	Label lbl = (Label)mouseEvent.getSource();
    	userTarget.setText(lbl.getText());
    	
    }
    @FXML
    public void sendMsg(){
        new Output().start();
    }

    class Output extends Thread{
        @Override
        public void run() {
            String str = msgInput.getText() ;
            if ( !str.isEmpty() ) {
                io.getPrintWriter().println(userTarget.getText()+"=>"+str);
                Platform.runLater(() ->{
                    Label label = new Label(str);
                    label.getStyleClass().add("msgOuter");
                    chatSectionScroll.getChildren().add(label);
                });
            }
        }
    }
    class InputWait extends Thread{
        @Override
        public void run() {
            while (!shutDown){
                try {
                    String msg = io.getBufferedReader().readLine();
                    if(msg.equals("$yes")) {
                		userExiste = true;
                	}
                    else if(msg.equals("$no")){
                		userExiste = false;
                	}
                    else{
                         Platform.runLater(() ->{
                              Label label = new Label(msg);
                              label.getStyleClass().add("msgEnter");
                              chatSectionScroll.getChildren().add(label) ;
                         });
                    }
                } catch (Exception e) {}
            }
        }
    }
}
