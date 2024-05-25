package com.example.tryout_kl1.Controller;
import com.example.tryout_kl1.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private DatePicker birthField;

    @FXML
    private PasswordField confPasswordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label confLabelMessage;

    @FXML
    private Label signupLabelMessage;

    @FXML
    private TextField usernameField;



    public void handleTryExamLogo()
    {
        MainApp.showScene("dashboard.fxml");
    }

    @FXML
    public void registerHandle(){
        if (passwordField.getText().equals(confPasswordField.getText()) &&
                firstNameField.getText().isBlank()==false &&
                lastNameField.getText().isBlank()==false &&
                usernameField.getText().isBlank()==false &&
                passwordField.getText().isBlank()==false){
            registerUser();
            confLabelMessage.setText("");
            signupLabelMessage.setText("User Registration Succesfull");
        }else{
            confLabelMessage.setText("Fill this Form Correctly");
        }
    }

    public void registerUser() {
        DbConnector connector = new DbConnector();
        try (Connection connectDb = connector.getConnection()) {
            String firstname = firstNameField.getText();
            String lastname = lastNameField.getText();
            String username = usernameField.getText();
            String birth = String.valueOf(birthField.getValue());
            String password = passwordField.getText();
            String query = "INSERT INTO user(firstName,lastName,username,birthday,password,role) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connectDb.prepareStatement(query)){
                preparedStatement.setString(1, firstname);
                preparedStatement.setString(2, lastname);
                preparedStatement.setString(3, username);
                preparedStatement.setString(4, birth);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, "User");
                preparedStatement.executeUpdate();
                signupLabelMessage.setText("Registration Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error during registration: " + e.getMessage());
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("Error connecting to "+e.getMessage());
        }
    }

    @FXML
    public void regToSignHandle(){
        MainApp.showScene("login.fxml");
    }
}