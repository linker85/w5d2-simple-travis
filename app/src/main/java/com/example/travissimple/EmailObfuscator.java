package com.example.travissimple;

/**
 * Created by raul on 16/11/2016.
 */
public class EmailObfuscator {
    public static String obfuscate(String s) {
        if (s != null) {
            String[] email = s.split("@");
            if (email[0] != null && email[0].length() > 0) {
                String initial = "" + email[0].charAt(0);
                String end     = "" + email[0].substring(email[0].length() - 1);
                if (email[0].length() <= 3) {
                    return initial + "***@" + email[1];
                } else {
                    return initial + "***" + end + "@" + email[1];
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void main(String args[]) {
        System.out.println(obfuscate("abc@gmail.com"));
    }

}
