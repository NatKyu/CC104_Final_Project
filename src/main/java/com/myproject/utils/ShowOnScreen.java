package com.myproject.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ShowOnScreen {

	public static void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Image icon = new Image("images/error.png");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        stage.getIcons().add(icon);  

        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
