package com.myproject.controllers.mainmenu;

import com.myproject.utils.Checking;
import com.myproject.utils.ShowOnScreen;
import com.myproject.utils.data.UserInfo;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Node;

public class mainMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static UserInfo currentUser;

    @FXML
    private TextField accountNumber;
    @FXML
    private TextField pin;
    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {

        accountNumber.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,12}") ? change : null
        ));

        pin.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null
        ));
    }

    @FXML
    public void loginButtonClick(ActionEvent event){

        if(accountNumber.getText().equals("121212")){
            adminAccess(event);
            return;
        }

        if(accountNumber.getText().isEmpty() || pin.getText().isEmpty()){
            ShowOnScreen.showError("All fields must be filled");
            return;
        }

        if(accountNumber.getText().length() != 12){
            ShowOnScreen.showError("Account number must be 12 Digits");
            return;
        }

        if(!Checking.checkUserInfo("number", accountNumber.getText())){
            ShowOnScreen.showError("Account not found.");
            return;
        }

        currentUser = Checking.checkUser(accountNumber.getText());

        if(currentUser.getAccountStatus().equalsIgnoreCase("Closed")){
            ShowOnScreen.showError("Sorry, your account has been closed.");
            return;
        }

        if(pin.getText().length() != 4){
            ShowOnScreen.showError("Pin must be 4 Digits.");
            return;
        }

        if(!currentUser.getAcctPin().equals(pin.getText())){
            ShowOnScreen.showError("Incorrect Pin.");
            return;
        }

        showSuccess(event);
    }

    public void adminAccess(ActionEvent event){
        try{
            switchToAdminLogin(event);
        }catch(IOException e){
            e.printStackTrace();
        }
    }    

    public void switchToAdminLogin(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminLogin.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCreateAccount(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/mainmenu/createAccountMenu.fxml"));
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

    public void switchToForgotPassword(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/mainmenu/forgotPassword.fxml"));
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
            stage = (Stage) loginButton.getScene().getWindow();
            try{
                switchToUserMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
