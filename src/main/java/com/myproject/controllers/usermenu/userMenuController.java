package com.myproject.controllers.usermenu;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Node;

public class userMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button logoutClick;

    public void switchToUserDeposit(ActionEvent event) throws IOException{  
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userDepositMenuDin.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToUserWithdraw(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userWithdrawalMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToUserBalance(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userBalanceMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToUserTransactions(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userTransactionsMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserSettings(ActionEvent event) throws IOException{  
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userSettings.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToMainMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/mainmenu/mainMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logoutAccount(ActionEvent event){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to Logout?");

        Image icon = new Image("images/logout.png");
        Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
        stage1.getIcons().add(icon);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) logoutClick.getScene().getWindow();
			try{
				switchToMainMenu(event);
			}catch(IOException e){
				e.printStackTrace();
			}
            
        }
    }
}
