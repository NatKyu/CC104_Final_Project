package com.myproject.controllers.adminmenu;

import java.io.IOException;
import java.util.Optional;

import com.myproject.app.App;
import com.myproject.utils.*;
import com.myproject.utils.data.UserInfo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class adminLogin {

    private Scene scene;
    private Parent root;
    private Stage stage;

    public static UserInfo currentUser;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {

        username.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z@ ]*") ? change : null
        ));

        password.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z0-9@.]*") ? change : null
        ));
    }

    @FXML
    public void submitClick(ActionEvent event){

        if(username.getText().isEmpty() || password.getText().isEmpty()){
            ShowOnScreen.showError("All fields must be filled");
            return;
        }

        if(!username.getText().equals(App.superAdmin.getAdminName())){
            ShowOnScreen.showError("Incorrect username.");
            return;
        }

		System.out.print("--- Login as Admin ---\n\n");
        showSuccess(event);
    }

    public void switchToMainMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/mainmenu/mainMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAdminMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminMainMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Login successfully");
        alert.setTitle("Success");

        Image icon = new Image("images/success.png");
        stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) submitButton.getScene().getWindow();
            try{
                switchToAdminMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
