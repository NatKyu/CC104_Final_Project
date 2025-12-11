package com.myproject.utils;

import java.util.Random;

public class AccountNumberGenerator {
	static Random random = new Random();

	public static String generate(){

		char[] digits = new char[12];

		digits[0] = (char) ('0' + random.nextInt(1, 10));

		for(int i = 1; i < 12; i++){
			digits[i] = (char) ('0' + random.nextInt(0,10));
		}

		return new String(digits);
	}

	public static String getAccountNumber(){

		String generatedNumber = "";

		while(true){
			generatedNumber = generate();

			if(!Checking.checkUserInfo("number", generatedNumber)){
				break;
			}
		}

		return generatedNumber;
	}

    public static String buildWithDash(String text) {
        char[] result = new char[14];
        int j = 0;  

        for (int i = 0; i < text.length(); i++) {
            result[j] = text.charAt(i);
            j++;	

            if ((i + 1) % 4 == 0 && i + 1 < text.length()) {
                result[j] = '-';
                j++;
            }
        }

        String output = "";
        for(int i = 0; i < j; i++){
            output += result[i];
        }

        return output;
    }
}
