package com.myproject.app;

import com.myproject.utils.*;
import com.myproject.utils.data.AdminInfo;

import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class App extends Application {

    public static LinkedList bank = new LinkedList();
    public static LinkedList transactions = new LinkedList();
    public static Stack adminCloseAccountMessages = new Stack();
    public static Stack resetPinRequest = new Stack();

    public static AdminInfo superAdmin = new AdminInfo();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/scene_builder/mainmenu/mainMenu.fxml"));
        Scene scene = new Scene(root);
        Image manny = new Image("/images/manny.jpg");

        stage.getIcons().add(manny);
        stage.setTitle("E-Manny Bank (Bootleg)");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            event.consume();
            exitApp(stage);
        });

        FileEditor.loadData("src/main/resources/user_data/data.txt");
        FileEditor.loadTransactions("src/main/resources/user_data/transactions.txt");
        FileEditor.loadAdminMessages("src/main/resources/admin/adminData.txt");
        FileEditor.loadResetRequest("src/main/resources/user_data/resetRequest.txt");
        stage.show();
    }

    private void exitApp(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to Exit?");

        Image icon = new Image("images/exit.png");
        Stage stage1 = (Stage) alert.getDialogPane().getScene().getWindow();
        stage1.getIcons().add(icon);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            FileEditor.saveData("src/main/resources/user_data/data.txt");
            FileEditor.saveTransactions("src/main/resources/user_data/transactions.txt");
            FileEditor.saveAdminMessages("src/main/resources/admin/adminData.txt");
            FileEditor.saveResetRequest("src/main/resources/user_data/resetRequest.txt");    
            stage1.close();
            stage.close();
        }
    }
}