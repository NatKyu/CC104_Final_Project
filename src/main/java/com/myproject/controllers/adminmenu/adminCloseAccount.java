package com.myproject.controllers.adminmenu;

import com.myproject.app.App;
import com.myproject.utils.*;
import com.myproject.utils.data.AdminCloseAccountMessages;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class adminCloseAccount {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private AdminCloseAccountMessages temp;
	private UserInfo curr = adminShowCloseAccount.selectedAccount;

    @FXML
    private Label nameField;
    @FXML
    private Label accountNumberField;
    @FXML
    private TextField reasonField;
    @FXML
    private Button closeAccountButton;

    @FXML
    public void initialize() {
		temp = Checking.getUserCloseAccount(curr.getAcctNumber());

		nameField.setText(curr.getAcctName());
		accountNumberField.setText(curr.getAcctNumberWithDash());
		
		reasonField.setText("		" + temp.getMessage());
    }

    @FXML
    public void closeAccountButtonClick(ActionEvent event){
		curr.setAccountStatus("Closed");

        App.adminCloseAccountMessages.deleteByAccountNumber(curr.getAcctNumber());

        System.out.printf("--- Account Closed \"%s\" (%s) ---\n\n", nameField.getText(), accountNumberField.getText());

        showSuccess(event);
    }

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Account successfully closed!");
        alert.setTitle("Success");

        Image icon = new Image("images/success.png");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            adminShowCloseAccount.selectedAccount = null;
            
            try{
                stage = (Stage) closeAccountButton.getScene().getWindow();
                switchToAdminMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void switchToAdminMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminShowCloseAccount.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
