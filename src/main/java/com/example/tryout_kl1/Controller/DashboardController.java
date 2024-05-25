package com.example.tryout_kl1.Controller;

import com.example.tryout_kl1.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class DashboardController {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnAvailableTO;

    @FXML
    private GridPane pnHome;

    @FXML
    private GridPane pnAvailableTO;

    @FXML
    private StackPane mainStackPane;

    @FXML
    public void initialize(URL location, ResourceBundle resource){

    }

    @FXML
    private void handleClick(ActionEvent event)
    {
        if(event.getSource()==btnAvailableTO)
        {
            pnAvailableTO.toFront();
        }
        else if (event.getSource()==btnHome)
        {
            pnHome.toFront();
        }
    }

    @FXML
    private void handleLogin()
    {
        MainApp.showScene("login.fxml");
    }

    @FXML
    private void handleRegister()
    {
        MainApp.showScene("signup.fxml");
    }

    /*@FXML
    public void goToTryoutSelection() {MainApp.showTryoutSelection();}*/
}