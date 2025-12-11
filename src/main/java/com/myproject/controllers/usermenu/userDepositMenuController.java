package com.myproject.controllers.usermenu;

import com.myproject.utils.data.*;
import com.myproject.controllers.mainmenu.*;
import com.myproject.utils.*;

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

public class userDepositMenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private UserInfo currUser = mainMenuController.currentUser;

    @FXML
    private TextField toDeposit;
    @FXML
    private Button submitClick;

    @FXML
    public void initialize() {
        try{
            toDeposit.setTextFormatter(new TextFormatter<>(change ->
                    change.getControlNewText().matches("\\d*(\\.\\d{0,2})?") ? change : null
            ));
        }catch(NullPointerException e){
            System.out.print("---\n\n");
        }
    }

    @FXML
    public void submitClick(ActionEvent event){

        if(toDeposit.getText().isEmpty()){
            ShowOnScreen.showError("Input deposit amount first.");
            return;
        }

        double amountToDeposit = Double.parseDouble(toDeposit.getText());

        if(amountToDeposit < 100){
            ShowOnScreen.showError("Minimum deposit amount: ₱100.00");
            return;
        }

        double newBalance = currUser.getBalance() + amountToDeposit;

        String[] transactions = {
            DateAndTime.dateStamp(),
            DateAndTime.timeStamp(),
            null,
            String.valueOf(amountToDeposit),
            String.valueOf(newBalance),
        };

        LinkedList temp = Checking.checkUserTransactions(currUser.getAcctNumber());
        UserTransactions currTransactions = new UserTransactions(transactions[0], transactions[1], transactions[2], transactions[3], transactions[4]);

        temp.addLast(currTransactions);
        mainMenuController.currentUser.setBalance(newBalance);

        showSuccess(event);
    }

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String balance = String.format("%.2f", mainMenuController.currentUser.getBalance());
        
        alert.setHeaderText("Current Balance: ₱" + balance);
        alert.setTitle("Deposit Successful");

        Image icon = new Image("images/success.png");
        stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) submitClick.getScene().getWindow();
            try{
                switchToUserMenu(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void switchToUserMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/usermenu/userMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }	
}