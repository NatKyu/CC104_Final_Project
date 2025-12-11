package com.myproject.utils;

import com.myproject.utils.data.*;
import com.myproject.app.App;

public class Checking {

	public static boolean checkUserInfo(String mode, String toCheck){

		for(LinkedList.Node temp = App.bank.head; temp != null; temp = temp.next){
			UserInfo data = (UserInfo) temp.data;
			String compare = (mode.equals("number")) ? data.getAcctNumber() : data.getAcctPin();

			if(toCheck.equals(compare)){
				return true;
			}
		}

		return false;
	}

	public static UserInfo checkUser(String toCheck){
		
		for(LinkedList.Node temp = App.bank.head; temp != null; temp = temp.next){
            UserInfo data = (UserInfo) temp.data;
            String compare = data.getAcctNumber();

            if(toCheck.equals(compare)){
                return data;
             }
        }

        return null;
    }

	public static LinkedList checkUserTransactions(String toCheck){
		
		for(LinkedList.Node temp = App.transactions.head; temp != null; temp = temp.next){

			if(temp.data instanceof LinkedList){
				LinkedList curr = (LinkedList) temp.data;

				if(curr.head != null && curr.head.data instanceof String){
					String compare = (String) curr.head.data;

					if(toCheck.equals(compare)){
						return curr;
					}
				}
			}
		}

		return null;
	}

	public static AdminCloseAccountMessages getUserCloseAccount(String toCheck){

		for(LinkedList.Node temp = App.adminCloseAccountMessages.head; temp != null; temp = temp.next){

			if(temp.data instanceof AdminCloseAccountMessages){
				AdminCloseAccountMessages curr = (AdminCloseAccountMessages) temp.data;

				if(curr.getSenderAccountNumber().equals(toCheck)){
					return curr;
				}
			}
		}

		return null;
	}
}
