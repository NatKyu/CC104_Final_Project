package com.myproject.utils.data;

public class AdminCloseAccountMessages {

	private String message;
	private String senderAccountNumber;

	public AdminCloseAccountMessages(String message, String senderAccountNumber){
		this.message = message;
		this.senderAccountNumber = senderAccountNumber;
	}

	public String getMessage(){
		return this.message;
	}

	public String getSenderAccountNumber(){
		return this.senderAccountNumber;
	}
}
