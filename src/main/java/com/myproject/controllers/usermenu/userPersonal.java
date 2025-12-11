package com.myproject.controllers.usermenu;

import com.myproject.controllers.mainmenu.*;
import com.myproject.utils.*;
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

public class userPersonal {

    private Stage stage;
    private Scene scene;
    private Parent root;
	private UserInfo currUser = mainMenuController.currentUser;

	@FXML
	private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
	@FXML
	private TextField pinField;
	@FXML
	private Button submitChangesButton;

	@FXML
	public void initialize(){
        nameField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z ]*") ? change : null
        ));

        addressField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z0-9., -]*") ? change : null
        ));

        emailField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z@._-]*") ? change : null
        ));

        contactField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,9}") ? change : null
        ));

		pinField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,4}") ? change : null
        ));

		nameField.setPromptText(currUser.getAcctName());
		addressField.setPromptText(currUser.getAcctAddress());
		emailField.setPromptText(currUser.getAcctEmail());
		String setContact = Setters.setContactNumber(currUser.getAcctContact());
		contactField.setPromptText(setContact);
	}

	@FXML
	public void submitButtonClick(ActionEvent event){
        if(nameField.getText().isEmpty() &&
            addressField.getText().isEmpty() &&
            emailField.getText().isEmpty() &&
            contactField.getText().isEmpty()){
                ShowOnScreen.showError("No text entered. Input atleast one field.");
                return;
        }

        if(pinField.getText().length() != 4){
            ShowOnScreen.showError("Pin must be 4 Digits.");
            return;
        }

		if(userSettings.settingsPin >= 2){
            try{
                ShowOnScreen.showError("Max pin submit reached.");
                switchToUserMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
            return;
        }

		if(!currUser.getAcctPin().equals(pinField.getText())){
			ShowOnScreen.showError("Incorrect Pin");
			userSettings.settingsPin++;
			return;
		}

		if(!nameField.getText().isEmpty() && !nameField.getText().equals("")) currUser.setAcctName(nameField.getText());
		if(!addressField.getText().isEmpty() && !addressField.getText().equals("")) currUser.setAcctAddress(addressField.getText());
		if(!emailField.getText().isEmpty() && !emailField.getText().equals("")) currUser.setAcctEmail(emailField.getText());
		if(!contactField.getText().isEmpty() && !contactField.getText().equals("")) currUser.setAcctContact("+639" + contactField.getText());

		showSuccess(event);
	}

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Changes applied successfully");
        alert.setTitle("Success");

        Image icon = new Image("images/success.png");
        stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) submitChangesButton.getScene().getWindow();
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
