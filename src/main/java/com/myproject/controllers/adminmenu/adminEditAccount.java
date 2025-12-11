package com.myproject.controllers.adminmenu;

import java.io.IOException;
import java.util.Optional;

import com.myproject.utils.Setters;
import com.myproject.utils.ShowOnScreen;
import com.myproject.utils.data.*;

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

public class adminEditAccount {

    private Stage stage;
    private Scene scene;
    private Parent root;
	private UserInfo currUser = adminShowEditAccounts.selectedAccount;

	@FXML
	private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
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
                switchToAdminMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void switchToAdminMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminShowAccounts.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }	
}
