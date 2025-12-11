package com.myproject.controllers.usermenu;

import com.myproject.controllers.mainmenu.*;
import com.myproject.utils.data.UserInfo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class userBalanceMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private UserInfo currUser = mainMenuController.currentUser;

    @FXML
    private TextField accountNumber;
    @FXML
    private TextField currentBalance;

    @FXML
    public void initialize() {
        String acctNum = currUser.getAcctNumberWithDash();
        String balance = String.format("%.2f", currUser.getBalance());

        accountNumber.setText(acctNum);
        currentBalance.setText(balance);
    }

    public void switchToUserMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
// 789700407894