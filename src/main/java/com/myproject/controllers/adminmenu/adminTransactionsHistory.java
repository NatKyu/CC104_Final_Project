package com.myproject.controllers.adminmenu;

import java.io.IOException;

import com.myproject.utils.*;
import com.myproject.utils.data.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class adminTransactionsHistory {

	private Stage stage;
    private Scene scene;
    private Parent root;

    private UserInfo currUser = adminShowTransactionsHistory.selectedAccount;
    private LinkedList user = Checking.checkUserTransactions(currUser.getAcctNumber());
    private LinkedList.Node temp = user.head;

	@FXML
	private Label username;
    @FXML
    private TableView<UserTransactions> transactionTable;
    @FXML
    private TableColumn<UserTransactions, String> dateColumn;
    @FXML
    private TableColumn<UserTransactions, String> timeColumn;
    @FXML
    private TableColumn<UserTransactions, String> withdrawColumn;
    @FXML
    private TableColumn<UserTransactions, String> depositColumn;
    @FXML
    private TableColumn<UserTransactions, String> balanceColumn;
    @FXML
    private ComboBox<String> filterType;

    @FXML
    public void initialize(){

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        withdrawColumn.setCellValueFactory(new PropertyValueFactory<>("withdraw"));
        depositColumn.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        filterType.getItems().addAll("All", "Withdraw", "Deposit");
        filterType.setValue("All");

		username.setText("\"" + currUser.getAcctName() + "\"");

        Setters.centerColumnNumber(dateColumn);
        Setters.centerColumnNumber(timeColumn);
        Setters.centerColumnNumber(withdrawColumn);
        Setters.centerColumnNumber(depositColumn);
        Setters.centerColumnNumber(balanceColumn);

        loadTransactions();
    }

    public void loadTransactions(ActionEvent event){
        temp = user.head;
        loadTransactions();
    }

    private void loadTransactions(){
        String type = filterType.getValue();

        transactionTable.setPlaceholder((new Label("No transaction history.")));
        transactionTable.getItems().clear();

        if(temp == null){
            transactionTable.getItems().add(    
                new UserTransactions(
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            );
            return;
        }

        while(temp != null){
            if(temp.data instanceof UserTransactions){
                UserTransactions curr = (UserTransactions) temp.data;

                String withdraw = "---", deposit = "---";
                double balanse = Double.parseDouble(curr.getBalance());
                double withdrawDin, depositDin;
                String balance = "₱" + String.format("%.2f", balanse);

                boolean isWithdraw = curr.getWithdraw() != null && !curr.getWithdraw().trim().equalsIgnoreCase("null");
                boolean isDeposit = (curr.getDeposit() != null && !curr.getDeposit().trim().equalsIgnoreCase("null"));

                if(type.equals("Withdraw") && !isWithdraw){
                    temp = temp.next;
                    continue;
                }

                if(type.equals("Deposit") && !isDeposit){
                    temp = temp.next;
                    continue;
                }

                if(isWithdraw){
                    withdrawDin = Double.parseDouble(curr.getWithdraw());
                    withdraw = "₱" + String.format("%.2f", withdrawDin);
                }
                if(isDeposit){
                    depositDin = Double.parseDouble(curr.getDeposit());
                    deposit = "₱" + String.format("%.2f", depositDin);
                }

                transactionTable.getItems().add(    
                    new UserTransactions(
                        curr.getDate(),
                        curr.getTime(),
                        withdraw,
                        deposit,
                        balance
                    )
                );
            }

            temp = temp.next;
        }
    }

    public void switchToAdminMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminShowTransactionsHistory.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
