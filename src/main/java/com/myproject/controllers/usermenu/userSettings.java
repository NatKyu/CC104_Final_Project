package com.myproject.controllers.usermenu;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class userSettings {

	private Stage stage;
    private Scene scene;
    private Parent root;

    public static int settingsPin;

	public void switchToUserPersonal(ActionEvent event) throws IOException{  
        settingsPin = 0;

        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userSettingsPersonal.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToUserChangePin(ActionEvent event) throws IOException{  
        settingsPin = 0;

        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userSettingsPin.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToUserCloseAccount(ActionEvent event) throws IOException{
        settingsPin = 0;

        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userSettingsCloseAccount.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToUserMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }	
}
