package com.myproject.controllers.mainmenu;

import com.myproject.app.App;
import com.myproject.utils.*;
import com.myproject.utils.data.RequestResets;
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

public class forgotPassword {

    private Stage stage;
    private Scene scene;
    private Parent root;

	private UserInfo currUser;
    private String accountNumber;

    @FXML
    private TextField nameField;
    @FXML
    private TextField accountNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField firstDepositField;
    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {

        accountNumberField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,12}") ? change : null
        ));

        nameField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z ]*") ? change : null
        )); 

        emailField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z0-9@._-]*") ? change : null
        ));
    }

	@FXML
    public void submitButtonClick(ActionEvent event){
        if(accountNumberField.getText().isEmpty() ||
			nameField.getText().isEmpty() ||
            emailField.getText().isEmpty()){
                ShowOnScreen.showError("All fields must be filled");
                return;
        }

		if(accountNumberField.getText().length() != 12){
			ShowOnScreen.showError("Invalid account number.");
			return;
		}

		currUser = Checking.checkUser(accountNumberField.getText());

		if(currUser == null){
			ShowOnScreen.showError("Account number not found.");
			return;
		}

        if(!nameField.getText().equalsIgnoreCase(currUser.getAcctName())){
            ShowOnScreen.showError("The name doesn't match on the account number.");
            return;
        }

		if(!emailField.getText().equals(currUser.getAcctEmail())){
            ShowOnScreen.showError("Email doesn't match on the account number.");
            return;
		}

        accountNumber = currUser.getAcctNumberWithDash();

		RequestResets add = new RequestResets(currUser.getAcctNumber(), currUser.getAcctName(), currUser.getAcctEmail(), currUser.getAcctNumberWithDash());
		App.resetPinRequest.addLast(add);

        System.out.printf("--- PIN reset requested (%s) ---\n\n", currUser.getAcctNumberWithDash());

        showSuccess(event);
    }

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("PIN reset requested.\nAccount number: " + accountNumber + "\nWaiting for admins approval.");
        alert.setTitle("Success");

        Image icon = new Image("images/success.png");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) submitButton.getScene().getWindow();
            try{
                switchToMainMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void switchToMainMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/mainmenu/mainMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
