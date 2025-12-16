package com.myproject.controllers.usermenu;

import com.myproject.controllers.mainmenu.*;
import com.myproject.utils.ShowOnScreen;
import com.myproject.utils.data.UserInfo;

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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class userSettingsChangePin {

    private Stage stage;
    private Scene scene;
    private Parent root;
	private UserInfo currUser = mainMenuController.currentUser;

	@FXML
	private TextField currentPinField;
	@FXML
	private TextField newPinField;
	@FXML
	private TextField reEnterNewPinField;
	@FXML
	private Button submitButton;

	@FXML
	public void initialize(){

		currentPinField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null
        ));

		newPinField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null
        ));

		reEnterNewPinField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null
        ));
	}

	@FXML
	public void submitButtonClick(ActionEvent event){
		if(currentPinField.getText().isEmpty() ||
			newPinField.getText().isEmpty() ||
			reEnterNewPinField.getText().isEmpty()){
				ShowOnScreen.showError("Please enter PIN first.");
				return;
		}

        if(!currentPinField.getText().equals(currUser.getAcctPin())){
            ShowOnScreen.showError("Current PIN incorrect.");
            return;
        }

        if(currentPinField.getText().length() != 4 ||
			newPinField.getText().length() != 4 ||
			reEnterNewPinField.getText().length() != 4){
            ShowOnScreen.showError("PIN must be 4 Digits.");
            return;
        }

		if(currentPinField.getText().equals(newPinField.getText())){
			ShowOnScreen.showError("The old PIN and new PIN shouldn't be the same.");
			return;
		}

		if(!newPinField.getText().equals(reEnterNewPinField.getText())){
			ShowOnScreen.showError("New PIN doesn't match.");
			return;
		}

		currUser.setAcctPin(newPinField.getText());
		showSuccess(event);
	}

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Pin changed successfully");
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
