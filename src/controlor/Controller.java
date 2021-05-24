package controlor;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import socket.InputOutPut;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private InputOutPut io ;
    private Socket socket ;
    @FXML
    private Label username ;
    @FXML
    private TextField msgInput ;
    @FXML
    private VBox chatSection ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            socket = new Socket("localhost",1234);
            io = new InputOutPut(socket);
            new InputWait().start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void newContact(){

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
                io.getPrintWriter().println(str.toString());
                Platform.runLater(() ->{
                    Label label = new Label(str);
                    label.getStyleClass().add("msgOuter");
                    chatSection.getChildren().add(label) ;
                });
            }
        }
    }
    class InputWait extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    String msg = io.getBufferedReader().readLine();
                    System.out.println(msg);
                    Platform.runLater(() ->{
                        Label label = new Label(msg);
                        label.getStyleClass().add("msgEnter");
                        chatSection.getChildren().add(label) ;
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
