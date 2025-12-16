package com.myproject.controllers.adminmenu;

import com.myproject.app.App;
import com.myproject.utils.*;
import com.myproject.utils.data.*;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class adminShowResetPin {

	private Stage stage;
    private Scene scene;
    private Parent root;

	private LinkedList curr = App.bank;
	private Stack resetRequest;

	private RequestResets selectedAccount;
	private String accountNumber;

	@FXML
	private TableView<RequestResets> table;
	@FXML
	private TableColumn<RequestResets, String> nameColumn;
	@FXML
	private TableColumn<RequestResets, String> accountNumberColumn;
	
	@FXML
	public void initialize(){

		table.widthProperty().addListener((obs, oldVal, newVal) -> {
        		table.lookupAll(".column-header").forEach(header -> {
            	header.setOnMouseDragged(event -> event.consume());
        	});
    	});

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("accountNumberWithDash"));

		Setters.centerColumnNumber(accountNumberColumn);

		resetRequest = App.resetPinRequest;

		loadAccounts();
		setupDoubleClick();
	}

	private void loadAccounts(){
		Stack.Node temp = resetRequest.top;
		table.setPlaceholder(new Label("No user accounts to show."));
		table.getItems().clear();

		while(temp != null){
			if(temp.data instanceof RequestResets){
				table.getItems().add((RequestResets) temp.data);
			}

			temp = temp.next;
		}
	}

	private void setupDoubleClick(){

		table.setOnMouseClicked(event -> {

			if(event.getClickCount() == 2){

				RequestResets selected = table.getSelectionModel().getSelectedItem();

				selectedAccount = selected;

				if(selected != null){
					event.consume();
					showConfirmation();
				}
			}
		});
	}

	private void showConfirmation(){
		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
		confirm.setTitle("Reset PIN?");
		confirm.setHeaderText(
			"Reset PIN for account: " +
			selectedAccount.getAccountNumberWithDash() +
			"\n\nProceed?"
		);

		Image icon = new Image("images/question.png");
        Stage stage = (Stage) confirm.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

		Optional<ButtonType> result = confirm.showAndWait();

		if(result.isPresent() && result.get() == ButtonType.OK){
			doResetPin();
		}
	}

	private void doResetPin(){
		LinkedList.Node temp = curr.head;

		while(temp != null){
			if(temp.data instanceof UserInfo){
				UserInfo curr1 = (UserInfo) temp.data;

				if(curr1.getAcctNumber().equals(selectedAccount.getAccountNumber())){
					curr1.setAcctPin("0000");
					accountNumber = curr1.getAcctNumberWithDash();

					System.out.printf("--- \"%s\" (%s) PIN reset to default ---\n\n", curr1.getAcctName(), curr1.getAcctNumberWithDash());

					resetRequest.popByAccountNumber(selectedAccount.getAccountNumber());

					showSuccess();
					return;
				}
			}

			temp = temp.next;
		}
	}

    public void showSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Account number: " + accountNumber + "\nPIN reset to default.");
        alert.setTitle("Success");

        Image icon = new Image("images/success.png");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);  

        alert.showAndWait();

		loadAccounts();
    }

	public void switchToAdminMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminMainMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }		
}
