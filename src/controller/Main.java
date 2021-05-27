package controller;

import java.io.File;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import socket.User;

public class Main extends Application {
	public static Socket socket;
	public static User user;
	private String path = "../presentation/sample.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        socket = new Socket("192.168.1.103",1234);
		File userHistory = new File("src/db.txt");
		File friendList = new File("src/friendList.txt");
        Scanner userHistoryReader = new Scanner(userHistory);
        Scanner friendListReader = new Scanner(friendList);
        user = new User();
	      if (userHistoryReader.hasNextLine())   user.setUserName(userHistoryReader.nextLine());
	      else path = "../presentation/WelcomePage.fxml";
	      
	      while(friendListReader.hasNextLine()) {
	    	  user.setFriendsList(friendListReader.nextLine());
	      }
	      friendListReader.close();
	      userHistoryReader.close();

        Parent root = FXMLLoader.load(getClass().getResource(path));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Lets talk");
        Scene scene = new Scene(root, 600, 450);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
