package com.myproject.utils;

import com.myproject.app.App;
import com.myproject.utils.data.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileEditor {

	public static void loadData(String filePath){

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String read;
            String name = "", acctNum = "", pin = "", contact = "", email = "", address = "", status = "";
            double balance = 0;
            int closeNum = 0;

            while((read = reader.readLine()) != null){

                try{
                    for(int i = 0; i < 10; i++){
                        int end = read.indexOf(";");
                        String toWrite = read.substring(0, end);
                        read = read.substring(end + 1);

                        switch(i){
                            case 0 -> name = toWrite;
                            case 1 -> acctNum = toWrite;
                            case 2 -> pin = toWrite;
                            case 3 -> balance = Double.parseDouble(toWrite);
                            case 4 -> contact = toWrite;
                            case 5 -> email = toWrite;
                            case 6 -> address = toWrite;
                            case 8 -> closeNum = Integer.parseInt(toWrite);
                            case 9 -> status = toWrite;
                        }
                    }
                    String withDash = AccountNumberGenerator.buildWithDash(acctNum);
                    UserInfo data = new UserInfo(name, acctNum, pin, balance, contact, email, address, withDash, status);
                    data.setCloseAccountSend(closeNum);

                    App.bank.addLast(data);

                }catch(IndexOutOfBoundsException e){
                    continue;
                }
            }

            System.out.printf("--- File bank Load Successful (%s) ---\n\n", filePath);
        }catch(FileNotFoundException e){
            System.out.printf("--- File bank Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Load File bank (%s) ---\n\n", filePath);
        }
    }

    public static void loadTransactions(String filePath){
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String read;

            while((read = reader.readLine()) != null){
                try{
                    read = read.trim();
                    if(read.isEmpty()) continue;

                    int last = read.indexOf('!');
                    if(last != -1) read = read.substring(0, last);

                    LinkedList userList = new LinkedList();

					int i = 0;
                    while(true){
                        String token;
                        int sep = read.indexOf(';');
                        if(sep != -1){
                            token = read.substring(0, sep);
                            read = read.substring(sep + 1);
                        } else {
                            token = read;
                            read = "";
                        }

                        if(token == null) token = "";
                        token = token.trim();

                        if(i == 0){
                            if(!token.isEmpty()) userList.addFirst(token);
                        } else {
                            String slash = token;
                            String date = null;
                            String time = null;
                            String withdraw = null;
                            String deposit = null;
                            String balance = null;

                            for(int j = 0; j < 5; j++){
                                int s = slash.indexOf('/');
                                String val;
                                if(s != -1){
                                    val = slash.substring(0, s);
                                    slash = slash.substring(s + 1);
                                } else {
                                    val = slash;
                                    slash = "";
                                }

                                switch(j){
                                    case 0 -> date = val;
                                    case 1 -> time = val;
                                    case 2 -> withdraw = val;
                                    case 3 -> deposit = val;
                                    case 4 -> balance = val;
                                }
                            }
                            if(date != null && !date.isEmpty()){
                                userList.addLast(new UserTransactions(date, time, withdraw, deposit, balance));
                            }
                        }

                        i++;
                        if(read.isEmpty()) break;
                    }

                    if(userList.head != null){
                        App.transactions.addLast(userList);
                    }
                }catch(Exception e){
                    continue;
                }
            }

            System.out.printf("--- File transactions Load Successful (%s) ---\n\n", filePath);
        }catch(FileNotFoundException e){
            System.out.printf("--- File transactions Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Load File transactions (%s) ---\n\n", filePath);
        }
    }

    public static void loadAdminMessages(String filePath){

        try(BufferedReader reader = new BufferedReader((new FileReader(filePath)))){
            String read;
            String acctNum = "", message = "";

            while((read = reader.readLine()) != null){

                try{
                    for(int i = 0; i < 2; i++){
                        int end = read.indexOf(";");
                        String toWrite = read.substring(0, end);
                        read = read.substring(end + 1);

                        switch(i){
                            case 0 -> message = toWrite;
                            case 1 -> acctNum = toWrite;
                        }
                    }

                    AdminCloseAccountMessages data = new AdminCloseAccountMessages(message, acctNum);
                    App.adminCloseAccountMessages.push(data);

                }catch(IndexOutOfBoundsException e){
                    continue;
                }

            }

            System.out.printf("--- File adminCloseAccountMessages Load Successful (%s) ---\n\n", filePath);

        }catch(FileNotFoundException e){
            System.out.printf("--- File adminCloseAccountMessages Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Load File adminCloseAccountMessages (%s) ---\n\n", filePath);
        }
    }

    public static void loadResetRequest(String filePath){

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String read;
            String acctNum = "", name = "", email = "", withDash = "";

            while((read = reader.readLine()) != null){

                try{
                    for(int i = 0; i < 4; i++){
                        int end = read.indexOf(";");
                        String toWrite = read.substring(0, end);
                        read = read.substring(end + 1);

                        switch(i){
                            case 0 -> acctNum = toWrite;
                            case 1 -> name = toWrite;
                            case 2 -> email = toWrite;
                            case 3 -> withDash = toWrite;
                        }
                    }

                    RequestResets data = new RequestResets(acctNum, name, email, withDash);

                    App.resetPinRequest.push(data);

                }catch(IndexOutOfBoundsException e){
                    continue;
                }
            }

            System.out.printf("--- File resetPinRequest Load Successful (%s) ---\n\n", filePath);
        }catch(FileNotFoundException e){
            System.out.printf("--- File resetPinRequest Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Load File resetPinRequest (%s) ---\n\n", filePath);
        }

    }

    //Name ; Account Number ; Pin ; Initial Balance ; Contact ; Email; Address ;
	public static void saveData(String filePath){

        try(FileWriter writer = new FileWriter(filePath)){
            String toWrite;

            for(LinkedList.Node temp = App.bank.head; temp != null; temp = temp.next){
                UserInfo data = (UserInfo) temp.data;
                toWrite = assignData(data);

                writer.write(toWrite + "\n");
            }

            System.out.printf("--- File bank Save Successful (%s) ---\n\n", filePath);

        }catch(FileNotFoundException e){
            System.out.printf("--- File bank Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Save File bank (%s) ---\n\n", filePath);
        }
    }

    private static String assignData(UserInfo data){
        String name = data.getAcctName();
        String acctNum = data.getAcctNumber();
        String pin = data.getAcctPin();
        double balance = data.getBalance();
        String contact = data.getAcctContact();
        String email = data.getAcctEmail();
        String address = data.getAcctAddress();
        String numWithDash = data.getAcctNumberWithDash();
        int closeNum = data.getCloseAccountSend();
        String status = data.getAccountStatus();

        return name + ";" + acctNum + ";" + pin + ";" + balance + ";" + contact + ";" + email + ";" + address + ";" + numWithDash + ";" + closeNum + ";" + status + ";";
    }

    //567856785678;      /      /           /        /         !
    //AccountNum  ; date / time / withdraw / deposit / balance !
    public static void saveTransactions(String filePath){

        try(FileWriter writer = new FileWriter(filePath)){
            String toWrite;

            for(LinkedList.Node temp = App.transactions.head; temp != null; temp = temp.next){
                LinkedList data = (LinkedList) temp.data;
                toWrite = assignTransac(data);

                writer.write(toWrite + "\n");
            }

            System.out.printf("--- File transactions Save Successful (%s) ---\n\n", filePath);

        }catch(FileNotFoundException e){
            System.out.printf("--- File transactions Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Save File transactions (%s) ---\n\n", filePath);
        }
    }

    private static String assignTransac(LinkedList userTransactions){
        String toWrite = "";
        String acctNum = "";
        
        for(LinkedList.Node temp = userTransactions.head; temp != null; temp = temp.next){
            if(temp.data instanceof String){
                acctNum = (String) temp.data;
                toWrite += acctNum;
            } else if(temp.data instanceof UserTransactions){
                UserTransactions curr = (UserTransactions) temp.data;
                
                if(curr.getBalance() != null){
                    String date = curr.getDate();
                    String time = curr.getTime();
                    String withdraw = curr.getWithdraw();
                    String deposit = curr.getDeposit();
                    String balance = curr.getBalance();
                    
                    toWrite += ";" + date + "/" + time + "/" + withdraw + "/" + deposit + "/" + balance;
                }
            }
        }
        
        return toWrite + "!";
    }

    public static void saveAdminMessages(String filePath){

        try(FileWriter writer = new FileWriter(filePath)){
            String toWrite;

            for(Stack.Node temp = App.adminCloseAccountMessages.top; temp != null; temp = temp.next){
                AdminCloseAccountMessages data = (AdminCloseAccountMessages) temp.data;

                toWrite = assignAdmin(data);

                writer.write(toWrite + "\n");
            }

            System.out.printf("--- File adminCloseAccountMessages Save Successful (%s) ---\n\n", filePath);

        }catch(FileNotFoundException e){
            System.out.printf("--- File adminCloseAccountMessages Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Save File adminCloseAccountMessages (%s) ---\n\n", filePath);
        }
    }

    private static String assignAdmin(AdminCloseAccountMessages data){
        String acctNum = data.getSenderAccountNumber();
        String message = data.getMessage();

        return message + ";" + acctNum + ";";
    }

    public static void saveResetRequest(String filePath){

        try(FileWriter writer = new FileWriter(filePath)){
            String toWrite;

            for(Stack.Node temp = App.resetPinRequest.top; temp != null; temp = temp.next){
                RequestResets data = (RequestResets) temp.data;
                toWrite = assignResetRequest(data);

                writer.write(toWrite + "\n");
            }

            System.out.printf("--- File resetPinRequest Save Successful (%s) ---\n\n", filePath);

        }catch(FileNotFoundException e){
            System.out.printf("--- File resetPinRequest Path Not Found (%s) ---\n\n", filePath);
        }catch(IOException e){
            System.out.printf("--- Couldn't Save File resetPinRequest (%s) ---\n\n", filePath);
        }
    }

    private static String assignResetRequest(RequestResets data){
        String acctNum = data.getAccountNumber();
        String name = data.getName();
        String email = data.getEmail();
        String withDash = data.getAccountNumberWithDash();

        return acctNum + ";" + name + ";" + email + ";" + withDash + ";";
    }
}