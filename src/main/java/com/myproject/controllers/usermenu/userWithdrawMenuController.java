package com.myproject.controllers.usermenu;

import com.myproject.utils.data.UserInfo;
import com.myproject.utils.data.UserTransactions;
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

public class userWithdrawMenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private UserInfo currUser = mainMenuController.currentUser;

    @FXML
    private TextField toWithdraw;
    @FXML
    private Button submitClick;

    @FXML
    public void initialize() {
        toWithdraw.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d*(\\.\\d{0,2})?") ? change : null
        ));
    }

    @FXML
    public void submitButtonClick(ActionEvent event){

        if(toWithdraw.getText().isEmpty()){
            ShowOnScreen.showError("Input withdrawal amount first.");
            return;
        }

        double amountToWithdraw = Double.parseDouble(toWithdraw.getText());

        if((amountToWithdraw % 100) != 0 || amountToWithdraw <= 0){
            ShowOnScreen.showError("The amount must be a multiple of available denominations: ₱100, ₱500, ₱1000");
            return;
        }

        if((currUser.getBalance() - amountToWithdraw) <= 100){
            ShowOnScreen.showError("Withdrawal amount exceeds your current balance.");
            return;
        }

        double newBalance = currUser.getBalance() - amountToWithdraw;

        String[] transactions = {
            DateAndTime.dateStamp(),
            DateAndTime.timeStamp(),
            String.valueOf(amountToWithdraw),
            null,
            String.valueOf(newBalance),
        };

        LinkedList temp = Checking.checkUserTransactions(currUser.getAcctNumber());
        UserTransactions currTransactions = new UserTransactions(transactions[0], transactions[1], transactions[2], transactions[3], transactions[4]);

        temp.addLast(currTransactions);
        currUser.setBalance(newBalance);

        showSuccess(event);
    }

    public void showSuccess(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String balance = String.format("%.2f", currUser.getBalance());
        
        alert.setHeaderText("Current Balance: ₱" + balance);
        alert.setTitle("Withdrawal Successful");

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
