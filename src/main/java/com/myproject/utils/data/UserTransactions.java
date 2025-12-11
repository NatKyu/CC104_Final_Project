package com.myproject.utils.data;

public class UserTransactions {
    private String date;
    private String time;
    private String withdraw;
    private String deposit;
    private String balance;


    public UserTransactions(String date, String time, String withdraw, String deposit, String balance){
        this.date = date;
        this.time = time;
        this.withdraw = withdraw;
        this.deposit = deposit;
        this.balance = balance;
    }

    public String getDate(){
        return this.date;
    }

    public String getTime(){
        return this.time;
    }

    public String getWithdraw(){
        return this.withdraw;
    }

    public String getDeposit(){
        return this.deposit;
    }

    public String getBalance(){
        return this.balance;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setWithdraw(String withdraw){
        this.withdraw = withdraw;
    }    

    public void setDeposit(String deposit){
        this.deposit = deposit;
    }

    public void setBalance(String balance){
        this.balance = balance;
    }

}
