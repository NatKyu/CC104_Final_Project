package com.myproject.controllers.adminmenu;

import com.myproject.app.App;
import com.myproject.utils.*;
import com.myproject.utils.data.*;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class adminShowCloseAccount {

	private Stage stage;
    private Scene scene;
    private Parent root;

	public static UserInfo selectedAccount;

	@FXML
	private TableView<UserInfo> table;
	@FXML
	private TableColumn<UserInfo, String> nameColumn;
	@FXML
	private TableColumn<UserInfo, String> accountNumberColumn;
	
	@FXML
	public void initialize(){
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("acctName"));
		accountNumberColumn.setCellValueFactory(new PropertyValueFactory<>("acctNumberWithDash"));

		Setters.centerColumnAdmin(nameColumn);
		Setters.centerColumnAdmin(accountNumberColumn);

		loadAccounts();
		setupDoubleClick();
	}

	private void loadAccounts(){
		table.setPlaceholder(new Label("No user accounts to show."));
		table.getItems().clear();

		LinkedList.Node temp = App.adminCloseAccountMessages.head;

		while(temp != null){
			if(temp.data instanceof AdminCloseAccountMessages){
				AdminCloseAccountMessages curr = (AdminCloseAccountMessages) temp.data;

				UserInfo user = Checking.checkUser(curr.getSenderAccountNumber());

				if(user != null && !"Closed".equals(user.getAccountStatus())){
					table.getItems().add(user);
				}
			}

			temp = temp.next;
		}
	}

	private void setupDoubleClick(){

		table.setOnMouseClicked(event -> {

			if(event.getClickCount() == 2){

				UserInfo selected = table.getSelectionModel().getSelectedItem();

				if(selected != null){
					event.consume();
					openDetailScene(selected);
				}
			}
		});
	}

	private void openDetailScene(UserInfo selected){

		try{
			selectedAccount = selected;

			switchToAdminCloseAccount();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void switchToAdminCloseAccount() throws IOException{  
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminCloseAccount.fxml"));
        stage = (Stage) table.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

	public void switchToAdminMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/scene_builder/adminmenu/adminMainMenu.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }	
}