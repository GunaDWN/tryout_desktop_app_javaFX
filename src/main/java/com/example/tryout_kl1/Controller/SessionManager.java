package com.example.tryout_kl1.Controller;

public class SessionManager {
    private static String loggedInUserFirstName;
    private static String loggedInUserlastName;

    public static void setLoggedInUser(String firstName, String lastName){
        loggedInUserFirstName = firstName;
        loggedInUserlastName = lastName;
    }

    public static String getLoggedInUserFirstName() {
        return loggedInUserFirstName;
    }

    public static String getLoggedInUserLastName() {
        return loggedInUserlastName;
    }
}
