package com.myproject.utils.data;

public class UserInfo {
	private String acctName;
    private String acctNumber;
    private String acctPin;
    private double balance;
    private String acctContact;
    private String acctEmail;
    private String acctAddress;
    private String acctNumberWithDash;
    private int closeAccountSend;
    private String accountStatus;

    public UserInfo(String acctName, String acctNumber, String acctPin, double balance, String acctContact, String acctEmail, String acctAddress, String acctNumberWithDash, String accountStatus){
        this.acctName = acctName;
        this.acctNumber = acctNumber;
        this.acctPin = acctPin;
        this.balance = balance;
        this.acctContact = acctContact;
        this.acctEmail = acctEmail;
        this.acctAddress = acctAddress;
        this.acctNumberWithDash = acctNumberWithDash;
        this.accountStatus = accountStatus;
    }

    //getter

    public String getAcctName(){
        return this.acctName;
    }

    public String getAcctNumber(){
        return this.acctNumber;
    }

    public String getAcctPin(){
        return this.acctPin;
    }

    public double getBalance(){
        return this.balance;
    }

    public String getAcctContact(){
        return this.acctContact;
    }

    public String getAcctEmail(){
        return this.acctEmail;
    }

    public String getAcctAddress(){
        return this.acctAddress;
    }

    public String getAcctNumberWithDash(){
        return this.acctNumberWithDash;
    }

    public int getCloseAccountSend(){
        return this.closeAccountSend;
    }

    public String getAccountStatus(){
        return this.accountStatus;
    }

    //setter

    public void setAcctName(String name){
        this.acctName = name;
    }

    public void setAcctNumber(String acctNumber){
        this.acctNumber = acctNumber;
    }

    public void setAcctPin(String acctPin){
        this.acctPin = acctPin;
    }

    public void setBalance(double newBalance){
        this.balance = newBalance;
    }

    public void setAcctContact(String acctContact){
        this.acctContact = acctContact;
    }

    public void setAcctEmail(String acctEmail){
        this.acctEmail = acctEmail;
    }

    public void setAcctAddress(String acctAddress){
        this.acctAddress = acctAddress;
    }

    public void setAcctNumberWithDash(String acctNumberWithDash){
        this.acctNumberWithDash = acctNumberWithDash;
    }

    public void setCloseAccountSend(int closeAccountSend){
        this.closeAccountSend = closeAccountSend;
    }

    public void setAccountStatus(String accountStatus){
        this.accountStatus = accountStatus;
    }

    public void addCloseAccountSend(){
        this.closeAccountSend++;
    }
}
