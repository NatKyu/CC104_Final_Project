package com.myproject.utils.data;

public class RequestResets {

	private String accountNumber;
	private String name;
	private String email;
	private String accountNumberWithDash;

	public RequestResets(String accountNumber, String name, String email, String accountNumberWithDash){
		this.accountNumber = accountNumber;
		this.name = name;
		this.email = email;
		this.accountNumberWithDash = accountNumberWithDash;
	}

	public String getAccountNumber(){
		return this.accountNumber;
	}

	public String getName(){
		return this.name;
	}

	public String getEmail(){
		return this.email;
	}

	public String getAccountNumberWithDash(){
		return this.accountNumberWithDash;
	}

	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public void setAccountNumberWithDash(String accountNumberWithDash){
		this.accountNumberWithDash = accountNumberWithDash;
	}
}
