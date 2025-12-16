package com.myproject.controllers.mainmenu;

import com.myproject.app.App;
import com.myproject.utils.*;
import com.myproject.utils.data.*;

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

public class createAccountMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String accountNumber;

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactField;
    @FXML
    private TextField firstDepositField;
    @FXML
    private Button finishButton;

    @FXML
    public void initialize() {
        nameField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z ]*") ? change : null
        ));

        addressField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z0-9., -]*") ? change : null
        ));

        emailField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[A-Za-z0-9@._-]*") ? change : null
        ));

        contactField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d{0,9}") ? change : null
        ));

        firstDepositField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if (newText.isEmpty()) {
                return change;
            }

            if (newText.matches("[1-9]\\d{0,5}(\\.\\d{0,2})?")) {
                return change;
            }

            if (newText.matches("0(\\.\\d{0,2})?")) {
                return change;
            }

            return null;
        }));
    }

    @FXML
    public void finishButton(ActionEvent event){
        if(nameField.getText().isEmpty() ||
            addressField.getText().isEmpty() ||
            emailField.getText().isEmpty() ||
            contactField.getText().isEmpty() ||
            firstDepositField.getText().isEmpty()){
                ShowOnScreen.showError("All fields must be filled");
                return;
        }

        if(contactField.getText().length() != 9){
            ShowOnScreen.showError("Invalid contact number.");
            return;
        }

        double deposit = Double.parseDouble(firstDepositField.getText());
        if(deposit < 500){
            ShowOnScreen.showError("Minimum initial deposit amount: ₱500.00");
            return;
        }

        accountNumber = AccountNumberGenerator.getAccountNumber();

        String[] toSave = {
            nameField.getText(),
            accountNumber,
            "0000",
            "+639" + contactField.getText(),
            emailField.getText(),
            addressField.getText(),
            AccountNumberGenerator.buildWithDash(accountNumber),
            "Open"
        };

        UserInfo userInfo = new UserInfo(toSave[0], toSave[1], toSave[2], deposit, toSave[3], toSave[4], toSave[5], toSave[6], toSave[7]);
        App.bank.addLast(userInfo);
        App.bank.sort();

        LinkedList userTransaction = new LinkedList();
        userTransaction.addFirst(accountNumber);
        App.transactions.addLast(userTransaction);

        System.out.println("--- NEW USER ADDED ---\n");

        showSuccess(event);
    }

    public void showSuccess(ActionEvent event){
        String toShow = AccountNumberGenerator.buildWithDash(accountNumber);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Account successfully created!\nAccount number: " + toShow + "\nDefault PIN: 0000");
        alert.setTitle("Success");

        Image icon = new Image("images/success.png");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) finishButton.getScene().getWindow();
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