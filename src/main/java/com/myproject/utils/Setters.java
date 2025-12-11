package com.myproject.utils;

import javafx.scene.control.TableColumn;

public class Setters {

	public static String setContactNumber(String contact){

		char[] output = new char[9];

		for(int i = 0, j = 4; i < output.length; i++, j++){
			output[i] = contact.charAt(j);
		}

		return new String(output);
	}

	public static void centerColumnNumber(TableColumn<?, ?> column){
        column.setStyle("-fx-alignment: CENTER;" + "-fx-font-family: Roboto;");
    }
}