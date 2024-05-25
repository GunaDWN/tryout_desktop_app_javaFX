package com.example.tryout_kl1.Controller;

import com.example.tryout_kl1.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private Label loginMessageLabel;
    @FXML
    private PasswordField passwordUser;
    @FXML
    private TextField username;

    @FXML
    public void loginButtonHandle()
    {
        if(username.getText().isBlank()==false && passwordUser.getText().isBlank()==false){
            validateLogin();
        }else{
            loginMessageLabel.setText("Please Enter Username and Password");
        }
    }

    private void validateLogin()
    {
        DbConnector connector = new DbConnector();
        Connection connectDB = connector.getConnection();

        String verifyLogin = "SELECT * FROM user WHERE username = ? AND password = ? ";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, username.getText());
            preparedStatement.setString(2, passwordUser.getText());
            ResultSet queryResult = preparedStatement.executeQuery();
            boolean userFound = false;
            while (queryResult.next()){
                String role = queryResult.getString("role");
                String firstName = queryResult.getString("firstName");
                String lastName = queryResult.getString("lastName");

                SessionManager.setLoggedInUser(firstName,lastName);

                if(role.equals("Admin")){
                    MainApp.showScene("admin_dashboard.fxml");
                    userFound = true;
                }else if (role.equals("User")){
                    MainApp.showScene("user_dashboard.fxml");
                    userFound = true;
                }
            }
            if (!userFound){
                loginMessageLabel.setText("Gagal Login, Coba Lagi !!");
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error: " +e.getMessage());
        }
    }
    @FXML
    private void handleTryExamLogo()
    {
        MainApp.showScene("dashboard.fxml");
    }
    @FXML
    public void regBtnHandle() {
        MainApp.showScene("signup.fxml");
    }
}