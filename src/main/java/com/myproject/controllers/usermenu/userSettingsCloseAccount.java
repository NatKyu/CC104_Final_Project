package com.myproject.controllers.usermenu;

import com.myproject.utils.ShowOnScreen;
import com.myproject.utils.data.*;
import com.myproject.app.App;
import com.myproject.controllers.mainmenu.*;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class userSettingsCloseAccount {

    private Stage stage;
    private Scene scene;
    private Parent root;
	private UserInfo currUser = mainMenuController.currentUser;

	@FXML
	private TextArea reasonText;
	@FXML
	private TextField pinField;
	@FXML
	private Button submitButton;

	@FXML
	public void initialize(){
        reasonText.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z0-9@., -]*") ? change : null
        ));

		pinField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null
        ));
	}

	@FXML
	public void submitButtonClick(ActionEvent event){

        String message = reasonText.getText().trim();

        if(message.isEmpty()){
            ShowOnScreen.showError("Input reason for closing account.");
            return;
        }

        if(pinField.getText().isEmpty()){
            ShowOnScreen.showError("PIN required.");
            return;
        }

		if(!pinField.getText().equals(currUser.getAcctPin())){
            ShowOnScreen.showError("Incorrect Pin.");
			return;
		}

        if(currUser.getCloseAccountSend() >= 1){
            ShowOnScreen.showError("You already had a pending close account.\nPlease wait for the admin's review.");
            return;
        }

        AdminCloseAccountMessages toSend = new AdminCloseAccountMessages(message, currUser.getAcctNumber());
        App.adminCloseAccountMessages.addLast(toSend);
        currUser.addCloseAccountSend();
        showSuccess(event);
	}

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Close account request successful\nWaiting for the admin's review.");
        alert.setTitle("Success");

        Image icon = new Image("images/success.png");
        stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) submitButton.getScene().getWindow();
            try{
                switchToUserMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void switchToUserMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userSettings.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }	
}
